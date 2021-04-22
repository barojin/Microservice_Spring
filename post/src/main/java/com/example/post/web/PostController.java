package com.example.post.web;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import com.example.post.exception.PostNotFoundException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class PostController {

    private DiscoveryClient discoveryClient;
    private PostRepository repository;
    private final PostModelAssembler assembler;

    public PostController(DiscoveryClient discoveryClient, PostRepository repository, PostModelAssembler assembler) {
        this.discoveryClient = discoveryClient;
        this.repository = repository;
        this.assembler = assembler;
    }

    @RequestMapping("/{applicationName}")
    public List<ServiceInstance> serviceInstancesByApplicationName(
            @PathVariable String applicationName)  {
        return this.discoveryClient.getInstances(applicationName);
    }

    @GetMapping("/posts")
    CollectionModel<EntityModel<Post>> all(){
        List<EntityModel<Post>> posts = repository.findAll().stream()
                .map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(posts, linkTo(methodOn(PostController.class).all()).withSelfRel());
    }

    @GetMapping("/posts/{id}")
    EntityModel<Post> one(@PathVariable Long id){
        Post post = repository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
        return assembler.toModel(post);
    }

    @PostMapping("/posts")
    ResponseEntity<?> newPost(@RequestBody Post post){
        EntityModel<Post> entityModel = assembler.toModel(repository.save(post));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @PutMapping("/posts/{id}")
    ResponseEntity<?> replacePost(@RequestBody Post newPost, @PathVariable Long id){
        Post replacedPost = repository.findById(id)
                .map(post -> {
                    post.setAccountNumber(newPost.getAccountNumber());
                    post.setFirstName(newPost.getFirstName());
                    post.setLastName(newPost.getLastName());
                    post.setTitle(newPost.getTitle());
                    post.setBody(newPost.getBody());
                    return repository.save(newPost);
                })
                .orElseGet(() ->{
                    newPost.setId(id);
                    return repository.save(newPost);
                });

        EntityModel<Post> entityModel = assembler.toModel(replacedPost);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @DeleteMapping("/posts/{id}")
    ResponseEntity<?> deletePost(@PathVariable Long id){
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/posts/{acct}")
    CollectionModel<EntityModel<Post>> postByAccount(@PathVariable String acct){
        List<EntityModel<Post>> posts = repository.findByAccountNumber(acct).stream()
                .map(assembler::toModel).collect(Collectors.toList());

        return CollectionModel.of(posts, linkTo(methodOn(PostController.class).all()).withSelfRel());
    }
}
