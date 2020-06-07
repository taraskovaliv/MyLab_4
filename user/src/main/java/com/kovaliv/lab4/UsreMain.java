package com.kovaliv.lab4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class UsreMain {
    public static void main(String[] args) {
        SpringApplication.run(UsreMain.class, args);
    }
}
