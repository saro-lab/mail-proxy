package me.saro.mail.auth;

import me.saro.commons.Converter;
import me.saro.mail.pub.Code;
import me.saro.mail.pub.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class AuthService {
    @Autowired
    AuthRepository authRepository;

    public Result<Auth> view(String id) {
        return authRepository.findById(id)
                .map(e -> new Result(Code.OK,"", e.mask()))
                .orElseGet(() -> new Result(Code.NOT_FOUND, "id not found, please check id or register id", null));
    }

    public Result<Auth> viewAll() {
        return new Result(Code.OK,"", Converter.toStream(authRepository.findAll()).map(Auth::mask));
    }

    public Auth get(String id) {
        return authRepository.findById(id).orElseThrow(() -> new RuntimeException("id not found, please check id or register id"));
    }

    public Result<Auth> template() {
        var auth = new Auth();
        auth.setId("id for using in the mail-proxy");
        auth.setHost("host");
        auth.setPort(0);
        auth.setMail("email address");
        auth.setUser("username");
        auth.setPass("password");

        return new Result(Code.OK, "auth template", auth);
    }

    public Result<String> save(@RequestBody Auth auth) {
        try {
            String id = auth.getId();

            switch (id) {
                case "all" :
                case "template" :
                    return new Result(Code.SAVE_FAIL, id + " is reserved word","");
            }

            authRepository.save(auth);
            return new Result(Code.OK);
        } catch(Exception e) {
            return new Result(Code.EXCEPTION, e.getMessage(), Converter.toString(e));
        }
    }
}
