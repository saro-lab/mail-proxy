package me.saro.mail.smtp;

import me.saro.mail.pub.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class SmtpContoller {

    @Autowired SmtpService smtpService;

    @PostMapping(path = "/send", produces = "application/json")
    public Result<String> send(@RequestBody Smtp smtp) {
        return smtpService.send(smtp);
    }

    @PostMapping(path = "/sendAll", produces = "application/json")
    public Result<List<Result<String>>> sendAll(@RequestBody List<Smtp> smtps) {
        return smtpService.sendAll(smtps);
    }

    @GetMapping("/template")
    public Result<Smtp> template() {
        return smtpService.template();
    }
}
