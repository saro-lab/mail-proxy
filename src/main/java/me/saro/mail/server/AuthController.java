package me.saro.mail.server;

import me.saro.mail.pub.Code;
import me.saro.mail.pub.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController("/auth")
public class AuthController {

    @Autowired AuthRepository authRepository;

    @GetMapping("/{id}")
    public Result<Auth> view(@PathVariable("id") String id) {
        return authRepository.findById(id)
                .map(e -> new Result(Code.OK,"", e))
                .orElseGet(() -> new Result(Code.NOT_FOUND, "id not found, please check id or register id", null));
    }

    @PostMapping
    public Result<String> save(@RequestBody Auth auth) {
        try {
            authRepository.save(auth);
            return new Result(Code.OK);
        } catch(Exception e) {
            return new Result(Code.OK);
        }
    }
}
