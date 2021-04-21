package com.example.post.web;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class PostController {

    private DiscoveryClient discoveryClient;
    private PostRepository repository;

    public PostController(DiscoveryClient discoveryClient, PostRepository repository) {
        this.discoveryClient = discoveryClient;
        this.repository = repository;
    }

    @RequestMapping("/{applicationName}")
    public List<ServiceInstance> serviceInstancesByApplicationName(
            @PathVariable String applicationName) {
        return this.discoveryClient.getInstances(applicationName);
    }

    @GetMapping("/posts")
    List<Post> all(){
        return repository.findAll();
    }

    @GetMapping("/posts/{acct}")


    @PostMapping("/posts")
    void createPost(@RequestBody Post post) {
        Post newPost = repository.save(post);
    }


}
