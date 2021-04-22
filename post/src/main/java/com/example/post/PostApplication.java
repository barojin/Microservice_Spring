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
            log.info("init: " + repository.save(new Post("1234","Yi", "Xuan", "haha", "good")));
            log.info("init: " + repository.save(new Post("1234","Yi", "Xuan", "where", "are you going!")));
            log.info("init: " + repository.save(new Post("1234","Yi", "Xuan", "what", "are you doing?")));
            log.info("init: " + repository.save(new Post("2222","Hojin", "Nam", "something", "good")));
            log.info("init: " + repository.save(new Post("3333","a", "b", "title", "this is the body content, fill me")));
            log.info("init: " + repository.save(new Post("8888","c", "d", "title", "this is the body content, fill me")));
            log.info("init: " + repository.save(new Post("9999","e", "f", "title", "this is the body content, fill me")));

            log.info("find: "+ repository.findByAccountNumber("1234"));
        };
    }

}
