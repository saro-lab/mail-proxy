package me.saro.smtp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SmtpProxyApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmtpProxyApplication.class, args);
	}

}
