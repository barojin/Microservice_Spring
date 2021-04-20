package com.example.post.web;

import java.util.List;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;


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

    @PostMapping("/posts")
    void createPost(@RequestBody Post post) {
        Post newPost = repository.save(post);
    }
}
