package me.saro.mail.auth;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import me.saro.commons.Converter;
import me.saro.mail.pub.Code;
import me.saro.mail.pub.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AuthService {

    @Autowired AuthRepository authRepository;
    @Autowired AuthPoolService authPoolService;

    @Value("${data.path}/auth.json") String reservedAuthListFileName;

    List<String> reservedList = Collections.synchronizedList(new ArrayList<>());

    public Result<Auth> view(String id) {
        return authRepository.findById(id)
                .map(e -> new Result<>(Code.OK,"", e.mask()))
                .orElseGet(() -> new Result<>(Code.NOT_FOUND, "id not found, please check id or register id", null));
    }

    public Result<List<Auth>> viewAll() {
        return new Result<>(Code.OK,"", Converter.toStream(authRepository.findAll()).map(Auth::mask).collect(Collectors.toList()));
    }

    public Auth get(String id) {
        return authRepository.findById(id).orElseThrow(() -> new RuntimeException("id not found, please check id or register id"));
    }

    public Result<Auth> template() {
        var auth = new Auth();
        auth.setId("id for using in the mail-proxy");
        auth.setHost("host");
        auth.setPort(0);
        auth.setDisplay("sender display");
        auth.setMail("sender email address");
        auth.setUser("username");
        auth.setPass("password");

        return new Result<>(Code.OK, "auth template", auth);
    }

    public Result<String> save(Auth auth) {
        try {
            String id = auth.getId();

            switch (id) {
                case "all" :
                case "template" :
                    return new Result<>(Code.SAVE_FAIL, id + " is reserved word","");
            }
            if (reservedList.contains(id)) {
                return new Result<>(Code.SAVE_FAIL, id + " is reserved id","");
            }

            authRepository.save(auth);
            authPoolService.delAll(id);
            return new Result<>(Code.OK);
        } catch(Exception e) {
            return new Result<>(Code.EXCEPTION, e.getMessage(), Converter.toString(e));
        }
    }

    public void loadReservedList() {
        File file = new File(reservedAuthListFileName);
        if (file.exists()) {
            log.info("find reserved auth list file");
            try {
                List<Auth> authList = new ObjectMapper().readValue(Converter.toString(file, "UTF-8"), new TypeReference<List<Auth>>() {});
                for (Auth auth : authList) {
                    save(auth);
                    reservedList.add(auth.getId());
                    log.info("put reserved id : " + auth.getId());
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }
}
