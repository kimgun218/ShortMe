package com.kimgun.shortme;

import com.kimgun.shortme.shorturl.ShortUrlJDBCTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@SpringBootApplication
public class ShortMeMain {

    @Bean
    @ConfigurationProperties(prefix="app.datasource")
    public DataSource dataSource() {
        return new DriverManagerDataSource();
    }

    @Bean
    public ShortUrlJDBCTemplate shortUrlJDBCTemplate() {
        ShortUrlJDBCTemplate shortUrlJDBCTemplate = new ShortUrlJDBCTemplate();
        shortUrlJDBCTemplate.setDataSource(dataSource());
        return shortUrlJDBCTemplate;
    }

    public static void main(String[] args) {
        SpringApplication.run(ShortMeMain.class, args);
    }
}
