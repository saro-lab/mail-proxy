package me.saro.mail;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class DBConfig {

    @Bean
    public DataSource dataSource() {
        var conf = new HikariDataSource();
        conf.setDriverClassName("org.h2.Driver");
        conf.setJdbcUrl("jdbc:h2:./mail-proxy-data/mail");
        conf.setUsername("user");
        conf.setPassword("pass");
        return conf;
    }
}
