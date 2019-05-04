package me.saro.mail.server;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;

@Data
@Entity
@Table(name = "mail_auth")
public class Auth {
    @Id
    String id;

    @Column(nullable = false)
    String host;

    @Column
    @Range(min = 1, max=65535)
    int port;

    @Column(nullable = false)
    String mail;

    @Column(nullable = false)
    String user;

    @JsonIgnore
    @Column(nullable = false)
    String pass;
}
