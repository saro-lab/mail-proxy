package me.saro.mail.smtp;

import lombok.Data;
import me.saro.mail.pub.Person;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class Smtp {
    @NotNull @NotBlank String id;
    @NotEmpty List<Person> to;
    List<Person> cc;
    String subject;
    String content;
}
