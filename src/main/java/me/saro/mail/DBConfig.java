package me.saro.mail;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Slf4j
@Configuration
@EnableTransactionManagement
public class DBConfig {

    @Value("${data.path}/mail")
    private String dbfile;

    @Bean
    public DataSource dataSource() {

        String jdbcUrl;
        if (!(dbfile.startsWith("./") || dbfile.startsWith("~/"))) {
            jdbcUrl = "jdbc:h2:"+dbfile;
        } else {
            jdbcUrl = "jdbc:h2:file:"+dbfile;
        }

        log.info("h2 db file path : " + dbfile);
        log.info("h2 db jdbcUrl : " + jdbcUrl);

        var conf = new HikariDataSource();
        conf.setDriverClassName("org.h2.Driver");
        conf.setJdbcUrl(jdbcUrl);
        conf.setUsername("user");
        conf.setPassword("pass");
        return conf;
    }
}
