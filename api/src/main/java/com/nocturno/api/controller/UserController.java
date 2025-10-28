package com.nocturno.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.nocturno.api.services.UserService;


@RestController
@RequestMapping(path = "/api/user")
public class UserController {
    
    @Autowired
    UserService userService;

    @GetMapping(path = "/me")
    public ResponseEntity<?> getMe(){
        try {
            Jwt auth = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
            var user = userService.getUserById(auth.getSubject());

            return ResponseEntity.status(HttpStatus.OK).body(user);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getBody());
        }
    }

    @GetMapping(path = "/{username}")
    public ResponseEntity<?> findUserByusername(@PathVariable String username){
        try {
            var user = userService.getUserByUsername(username);

            return ResponseEntity.status(HttpStatus.OK).body(user);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getBody());
        }
    }

    @GetMapping
    public ResponseEntity<?> findAllUsers(){
        var users = userService.getAllUsers();

        return ResponseEntity.status(HttpStatus.OK).body(users);
    }
}
