package com.nocturno.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.nocturno.api.models.user.dto.LoginDTO;
import com.nocturno.api.models.user.dto.RegisterDTO;
import com.nocturno.api.services.AuthService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;

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

    @Data
    static class Token {
        private String token;
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> registerController(@RequestBody RegisterDTO dto, HttpServletResponse response) {  
        try {
            String tokenString = authService.registerService(dto);

            Token token = new Token();
            token.setToken(tokenString);

            Cookie cookie = new Cookie("NOCTURNO_TOKEN", tokenString);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            response.addCookie(cookie);
            
            return ResponseEntity.ok().body(token);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getBody());
        }   
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginController(@RequestBody LoginDTO dto, HttpServletResponse response) {
        try {
            String tokenString = authService.loginService(dto);

            Token token = new Token();
            token.setToken(tokenString);

            Cookie cookie = new Cookie("NOCTURNO_TOKEN", tokenString);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            cookie.setSecure(false);
            response.addCookie(cookie);
            
            return ResponseEntity.ok().body(token);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getBody());
        }   
    }
    
}
