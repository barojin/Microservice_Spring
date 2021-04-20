package com.example.post.exception;

public class PostNotFoundException extends RuntimeException{

    public PostNotFoundException(Long id){
        super("Post " + id + " not found.");
    }
}
