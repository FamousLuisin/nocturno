package com.nocturno.api.config.filter;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CookieAuthFilter extends OncePerRequestFilter {

    private final AuthenticationManager authenticationManager;

    public CookieAuthFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException { 

        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("NOCTURNO_TOKEN".equals(cookie.getName())) {
                    String tokenString = cookie.getValue();
                    BearerTokenAuthenticationToken authRequest = new BearerTokenAuthenticationToken(tokenString);
                    Authentication authResult = authenticationManager.authenticate(authRequest);
                    SecurityContextHolder.getContext().setAuthentication(authResult);
                    break;
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return path.startsWith("/auth");
    }
}