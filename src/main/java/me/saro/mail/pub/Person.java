package me.saro.mail.pub;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class Person {

    // view
    String view;
    // mail
    @NotNull @NotBlank String mail;

    public String toString() {
        if (view == null && view.isBlank()) {
            view = mail;
        }
        return view + " <" + mail + ">";
    }
}
