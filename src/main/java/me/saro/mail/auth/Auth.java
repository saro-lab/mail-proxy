package me.saro.mail.auth;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
    String user;

    @Column(nullable = false)
    String pass;

    public Auth mask() {
        pass = "****";
        return this;
    }
}
