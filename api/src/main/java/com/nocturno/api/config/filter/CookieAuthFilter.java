package com.nocturno.api.config.filter;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CookieAuthFilter extends OncePerRequestFilter {

    private final JwtDecoder jwtDecoder;

    public CookieAuthFilter(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
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

                    try {
                        Jwt token = jwtDecoder.decode(tokenString);
                        String scope = String.format("SCOPE_%s", token.getClaimAsString("scope"));
                        Collection<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(scope));
                        JwtAuthenticationToken jwt = new JwtAuthenticationToken(token, authorities);
                        SecurityContextHolder.getContext().setAuthentication(jwt);
                    } catch (JwtException ex) {
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        return;
                    }
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