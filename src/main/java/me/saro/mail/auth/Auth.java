package me.saro.mail.auth;

import lombok.Data;
import lombok.SneakyThrows;
import me.saro.mail.pub.Person;
import org.hibernate.validator.constraints.Range;

import javax.mail.internet.InternetAddress;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Properties;

@Data
@Entity
@Table(name = "mail_auth")
public class Auth {
    @Id
    String id;

    @Column(nullable = false)
    String host;

    @Column
    @Range(min = 1, max = 65535)
    int port;

    @Column(nullable = false)
    String mail;

    @Column(nullable = false)
    String displayName;

    @Column(nullable = false)
    String user;

    @Column(nullable = false)
    String pass;

    public Auth mask() {
        pass = "****";
        return this;
    }

    public Properties toProperties() {
        Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.ssl.trust", host);
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.port", Integer.toString(port));
        properties.setProperty("mail.smtp.socketFactory.port", Integer.toString(port));
        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.smtp.starttls.required", "true");
        properties.setProperty("mail.debug", "true");
        properties.setProperty("mail.smtp.ssl.enable", "true");
        return properties;
    }
}
