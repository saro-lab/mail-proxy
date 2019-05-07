package me.saro.mail.auth;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AuthPoolService {
    private Map<String, JavaMailSender> map = new ConcurrentHashMap<>();

    public JavaMailSender getJavaMailSender(String id) {
        return map.get(id);
    }

    public JavaMailSender setJavaMailSender(String id, JavaMailSender sender) {
        return map.put(id, sender);
    }

    public JavaMailSender delJavaMailSender(String id) {
        return map.remove(id);
    }

    public void delAll(String id) {
        if (id != null) {
            map.remove(id);
        }
    }
}
