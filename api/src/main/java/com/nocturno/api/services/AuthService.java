package com.nocturno.api.services;

import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.nocturno.api.models.user.UserModel;
import com.nocturno.api.models.user.dto.LoginDTO;
import com.nocturno.api.models.user.dto.RegisterDTO;
import com.nocturno.api.models.user.dto.UserDTO;
import com.nocturno.api.repository.UserRepository;

@Service
public class AuthService {
    
    private UserRepository userRepository;
    private ModelMapper modelMapper;

    public AuthService(UserRepository userRepository, ModelMapper modelMapper){
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public UserDTO loginService(LoginDTO dto){

        UserModel user = userRepository.findByEmail(dto.getEmail());

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "error: user not found");
        }

        this.passwordMatch(dto.getPassword(), user.getPassword());

        return modelMapper.map(user, UserDTO.class);
    }

    public UserDTO registerService(RegisterDTO dto) {
        validatePasswords(dto.getPassword(), dto.getConfirmPassword());

        UserModel user = new UserModel(
                dto.getDisplayName(),
                dto.getUsername(),
                dto.getEmail(),
                dto.getPassword(),
                dto.getBio(),
                dto.getBirthday()
        );

        try {
            user = userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "error: user already exists");
        }

        return modelMapper.map(user, UserDTO.class);
    }

    private void validatePasswords(String password, String confirmPassword) {
        if (!Objects.equals(password, confirmPassword)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "error: password and confirm password are different");
        }
    }

    private void passwordMatch(String passwordRequest, String passwordData){
        if (!Objects.equals(passwordRequest, passwordData)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "error: incorrect password");
        }
    }
}
