package com.nocturno.api.services;

import java.time.Instant;
import java.util.Objects;
import java.util.regex.Pattern;

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
import com.nocturno.api.models.user.Status;
import com.nocturno.api.models.user.UserModel;
import com.nocturno.api.models.user.dto.LoginDTO;
import com.nocturno.api.models.user.dto.RegisterDTO;
import com.nocturno.api.repository.UserRepository;

@Service
public class AuthService {
    
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private JwtEncoder jwtEncoder;

    private static String REGEX_USERNAME = "^[a-zA-Z0-9_]{4,}$";
    private static String REGEX_PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9])\\S{8,}$";
    private static String REGEX_EMAIL = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private static String REGEX_DISPLAYNAME = "^.{4,}$";

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
        validateEmail(dto.getEmail());
        UserModel user = userRepository.findByEmailAndStatus(dto.getEmail(), Status.ACTIVE);

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found");
        }

        this.passwordMatch(dto.getPassword(), user.getPassword());

        return this.generateJwt(user);
    }

    public String registerService(RegisterDTO dto) {
        validatePasswords(dto.getPassword(), dto.getConfirmPassword());
        validateUsername(dto.getUsername());
        validateEmail(dto.getEmail());
        validateDisplayName(dto.getDisplayName());

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
            throw new ResponseStatusException(HttpStatus.CONFLICT, "user already exists");
        }

        return this.generateJwt(user);
    }

    private void validateUsername(String username){
        if (!Pattern.matches(REGEX_USERNAME, username)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid username format. Minimum 5 characters, only letters, numbers, and underscore are allowed");
        }
    }

    private void validateEmail(String email){
        if (!Pattern.matches(REGEX_EMAIL, email)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid email format. Please provide a valid email address");
        }
    }

    private void validateDisplayName(String displayName){
        if (!Pattern.matches(REGEX_DISPLAYNAME, displayName)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid display name format. Minimum 4 characters are required");
        }
    }

    private void validatePasswords(String password, String confirmPassword) {
        if (!Objects.equals(password, confirmPassword)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "password and confirm password are different");
        }
        if (!Pattern.matches(REGEX_PASSWORD, password)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password must contain at least 8 characters, one uppercase letter, one lowercase letter, one number, and one special character");
        }
    }

    private void passwordMatch(String passwordRequest, String passwordData){
        if (!passwordEncoder.matches(passwordRequest, passwordData)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "incorrect password");
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
