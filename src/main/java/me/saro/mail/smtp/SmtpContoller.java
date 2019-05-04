package me.saro.mail.smtp;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SmtpContoller {
    @PostMapping("/send")
    public void send() {

    }
}
