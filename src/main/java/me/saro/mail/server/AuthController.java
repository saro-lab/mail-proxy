package me.saro.mail.server;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonInclude;
import me.saro.commons.Converter;
import me.saro.mail.pub.Code;
import me.saro.mail.pub.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired AuthRepository authRepository;

    @JsonIgnoreProperties("pass")
    @GetMapping("/{id}")
    public Result<Auth> view(@PathVariable("id") String id) {
        return authRepository.findById(id)
                .map(e -> new Result(Code.OK,"", e.mask()))
                .orElseGet(() -> new Result(Code.NOT_FOUND, "id not found, please check id or register id", null));
    }

    @GetMapping("/template")
    public Result<Auth> template() {
        var auth = new Auth();
        auth.setId("id for using in the mail-proxy");
        auth.setHost("host");
        auth.setPort(0);
        auth.setMail("mail address");
        auth.setUser("username");
        auth.setPass("password");

        return new Result(Code.OK, "auth template", auth);
    }

    @PostMapping(path = "", produces = "application/json")
    public Result<String> save(@RequestBody Auth auth) {
        System.out.println("진입");
        System.out.println(auth);
        try {
            authRepository.save(auth);
            return new Result(Code.OK);
        } catch(Exception e) {
            return new Result(Code.EXCEPTION, e.getMessage(), Converter.toString(e));
        }
    }
}
