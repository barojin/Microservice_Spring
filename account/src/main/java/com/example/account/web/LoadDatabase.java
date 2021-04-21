package com.example.account.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class LoadDatabase {
    private static final Logger log =
            LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(AccountRepository repository){
        return args -> {
            log.info("Init account DB");
            log.info("init: " + repository.save(new Account("1234","Yi", new BigDecimal("1000"))));
            log.info("init: " + repository.save(new Account("2222","", new BigDecimal("1000"))));
            log.info("init: " + repository.save(new Account("3333","", new BigDecimal("1000"))));
            log.info("init: " + repository.save(new Account("8888","", new BigDecimal("1000"))));
            log.info("init: " + repository.save(new Account("9999","", new BigDecimal("1000"))));

        };
    }
}
