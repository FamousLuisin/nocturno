package com.nocturno.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.nocturno.api.models.user.dto.LoginDTO;
import com.nocturno.api.models.user.dto.RegisterDTO;
import com.nocturno.api.models.user.dto.UserDTO;
import com.nocturno.api.services.AuthService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping(path = "/auth")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> registerController(@RequestBody RegisterDTO dto) {  
        try {
            UserDTO user = authService.registerService(dto);
            
            return ResponseEntity.ok().body(user);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getBody());
        }   
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginController(@RequestBody LoginDTO dto) {
        try {
            UserDTO user = authService.loginService(dto);
            
            return ResponseEntity.ok().body(user);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getBody());
        }   
    }
    
}
