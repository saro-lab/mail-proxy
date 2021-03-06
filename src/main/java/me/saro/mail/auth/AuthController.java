package me.saro.mail.auth;

import me.saro.mail.pub.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired AuthService authService;

    @GetMapping("/{id}")
    public Result<Auth> view(@PathVariable("id") String id) {
        return authService.view(id);
    }

    @GetMapping("/all")
    public Result<List<Auth>> viewAll() {
        return authService.viewAll();
    }

    @GetMapping("/template")
    public Result<Auth> template() {
        return authService.template();
    }

    @PostMapping(path = "", produces = "application/json")
    public Result<String> save(@RequestBody Auth auth) {
        return authService.save(auth);
    }
}
