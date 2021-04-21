package com.example.post;

import com.example.post.web.Post;
import com.example.post.web.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PostApplication {

    public static void main(String[] args) {
        SpringApplication.run(PostApplication.class, args);
    }

    private static final Logger log = LoggerFactory.getLogger(PostApplication.class);

    @Bean
    CommandLineRunner initDB(PostRepository repository){
        return args -> {
            log.info("Preloading " + repository.save(new Post("1234","Bilbo Baggins", "burglar","okay")));
            log.info("Preloading " + repository.save(new Post("1235","Frodo" ,"Baggins", "thief")));
        };
    }

}
