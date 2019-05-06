package me.saro.mail.pub;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    // display
    String display;
    // mail
    @NotNull @NotBlank String mail;

    public String toString() {
        if (display == null && display.isBlank()) {
            display = mail;
        }
        return display + " <" + mail + ">";
    }

    @SneakyThrows
    public InternetAddress toInternetAddress() {
        return new InternetAddress(toString());
    }
}
