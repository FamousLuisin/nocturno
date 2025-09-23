package com.nocturno.api.services;

import java.time.Instant;
import java.util.Objects;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.nocturno.api.models.user.Role;
import com.nocturno.api.models.user.UserModel;
import com.nocturno.api.models.user.dto.LoginDTO;
import com.nocturno.api.models.user.dto.RegisterDTO;
import com.nocturno.api.repository.UserRepository;

@Service
public class AuthService {
    
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private JwtEncoder jwtEncoder;

    public AuthService(
        UserRepository userRepository, 
        BCryptPasswordEncoder passwordEncoder,
        JwtEncoder jwtEncoder
        ){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;
    }

    public String loginService(LoginDTO dto){

        UserModel user = userRepository.findByEmail(dto.getEmail());

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "error: user not found");
        }

        this.passwordMatch(dto.getPassword(), user.getPassword());

        return this.generateJwt(user);
    }

    public String registerService(RegisterDTO dto) {
        validatePasswords(dto.getPassword(), dto.getConfirmPassword());

        dto.setPassword(this.encryptPassword(dto.getPassword()));

        UserModel user = new UserModel(
                dto.getDisplayName(),
                dto.getUsername(),
                dto.getEmail(),
                dto.getPassword(),
                dto.getBio(),
                dto.getBirthday(),
                dto.getPicture()
        );

        try {
            user = userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "error: user already exists");
        }

        return this.generateJwt(user);
    }

    private void validatePasswords(String password, String confirmPassword) {
        if (!Objects.equals(password, confirmPassword)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "error: password and confirm password are different");
        }
    }

    private void passwordMatch(String passwordRequest, String passwordData){
        if (!passwordEncoder.matches(passwordRequest, passwordData)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "error: incorrect password");
        }
    }

    private String encryptPassword(String password){
        return passwordEncoder.encode(password);
    }

    private String generateJwt(UserModel user){

        Instant now = Instant.now();
        Long expiresIn = 900L;

        Role scope = user.getRole();

        JwtClaimsSet clains = JwtClaimsSet.builder()
            .issuer("api")
            .subject(user.getId().toString())
            .issuedAt(now)
            .expiresAt(now.plusSeconds(expiresIn))
            .claim("scope", scope)
            .build();

        JwsHeader jwsHeader = JwsHeader.with(SignatureAlgorithm.RS256)
        .type("JWT")
        .build();

        String token = jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, clains)).getTokenValue();
        
        return token;
    }
}
