package com.nocturno.api.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.nocturno.api.models.post.dto.PostRequestDTO;
import com.nocturno.api.services.PostService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping(path = "/api/post")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody PostRequestDTO postDTO){

        try {
            Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            var post = postService.newPost(postDTO, jwt.getSubject());

            return ResponseEntity.status(HttpStatus.OK).body(post);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getBody());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllPosts(){
        var posts = postService.getAllPosts();

        return ResponseEntity.status(HttpStatus.OK).body(posts);
    }
    
    @GetMapping(path = "/user/{username}")
    public ResponseEntity<?> getPostByUsername(@PathVariable String username) {
        try {
            var posts = postService.getPostsByUser(username);

            return ResponseEntity.status(HttpStatus.OK).body(posts);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getBody());
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deletePost(@PathVariable String id){
        try {
            Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            postService.deletePost(id, jwt.getSubject());

            return ResponseEntity.ok(null);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getBody());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@PathVariable String id, @RequestBody PostRequestDTO dto) {
        try {
            Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            var post = postService.updatePost(id, dto, jwt.getSubject());

            return ResponseEntity.status(HttpStatus.OK).body(post);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getBody());
        }
    }
    
    @PostMapping("/like/{id}")
    public ResponseEntity<?> postLike(@PathVariable String id) {
        try {
            Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            postService.likePost(id, jwt.getSubject());

            return ResponseEntity.ok(null);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getBody());
        }
    }
    
}
