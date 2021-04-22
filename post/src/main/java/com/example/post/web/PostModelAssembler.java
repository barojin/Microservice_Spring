package com.example.post.web;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class PostModelAssembler implements RepresentationModelAssembler<Post, EntityModel<Post>> {

    @Override
    public EntityModel<Post> toModel(Post entity){
        return EntityModel.of(entity,
                linkTo(methodOn(PostController.class).one(entity.getId())).withSelfRel(),
                linkTo(methodOn(PostController.class).all()).withRel("posts"));
    }
}
