package me.saro.mail.smtp;

import me.saro.commons.Converter;
import me.saro.commons.Utils;
import me.saro.commons.function.ThrowableConsumer;
import me.saro.mail.auth.Auth;
import me.saro.mail.auth.AuthService;
import me.saro.mail.pub.Code;
import me.saro.mail.pub.Person;
import me.saro.mail.pub.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.mail.Message;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class SmtpService {

    final static int THREAD_COUNT = 64;
    ExecutorService sendAllExecutor;
    Map<String, JavaMailSender> map = new ConcurrentHashMap<>();

    @Autowired AuthService authService;

    public Result<String> send(Smtp smtp) {

        try {
            Auth auth = authService.get(smtp.getId());
            JavaMailSender sender = getJavaMailSender(auth);

            sender.send(mimeMessage -> {
                mimeMessage.setFrom(new Person(auth.getDisplayName(), auth.getMail()).toInternetAddress());
                smtp.getTo().stream()
                        .forEach(ThrowableConsumer.runtime(e -> mimeMessage.addRecipient(Message.RecipientType.TO, e.toInternetAddress())));
                Optional.ofNullable(smtp.getCc()).orElse(Collections.emptyList()).stream()
                        .forEach(ThrowableConsumer.runtime(e -> mimeMessage.addRecipient(Message.RecipientType.TO, e.toInternetAddress())));
                mimeMessage.setSubject(smtp.getSubject());
                mimeMessage.setText(smtp.getContent(), "utf-8", "html");
            });
        } catch (Exception e) {
            return new Result<>(Code.EXCEPTION, e.getMessage(), Converter.toString(e));
        }

        return new Result<>(Code.OK, "", null);
    }

    public Result<List<Result<String>>> sendAll(List<Smtp> smtps) {
        return new Result<>(Code.DONE, "execute all", Utils.executeAllThreads(sendAllExecutor, smtps, this::send));
    }

    public Result<Smtp> template() {
        var smtp = new Smtp();
        smtp.setId("registered auth id");
        smtp.setTo(List.of(new Person("display", "email"), new Person("display", "email")));
        smtp.setCc(List.of(new Person("display", "email"), new Person("display", "email")));
        smtp.setContent("html content");
        return new Result<>(Code.OK, "auth template", smtp);
    }

    public JavaMailSender getJavaMailSender(Auth auth) {
        JavaMailSender sender = map.get(auth.getId());

        if (sender == null) {
            JavaMailSenderImpl jmsi = new JavaMailSenderImpl();
            jmsi.setUsername(auth.getUser());
            jmsi.setPassword(auth.getPass());
            jmsi.setJavaMailProperties(auth.toProperties());
            map.put(auth.getId(), (sender = jmsi));
        }

        return sender;
    }

    public void removeJavaMailSender(String id) {
        map.remove(id);
    }

    @PostConstruct
    private void PostConstruct() {
        sendAllExecutor = Executors.newFixedThreadPool(THREAD_COUNT);
    }

    @PreDestroy
    private void PreDestroy() {
        if (!sendAllExecutor.isShutdown()) {
            sendAllExecutor.shutdown();
        }
    }
}
