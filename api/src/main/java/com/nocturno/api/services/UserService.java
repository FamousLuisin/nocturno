package com.nocturno.api.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.nocturno.api.models.user.UserModel;
import com.nocturno.api.models.user.dto.UpdateDTO;
import com.nocturno.api.models.user.dto.UserDTO;
import com.nocturno.api.repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper mapper;

    public UserDTO getUserById(String id){
        UserModel user = userRepository.getReferenceById(UUID.fromString(id));

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "error: user not found");
        }

        UserDTO userDTO = mapper.map(user, UserDTO.class);

        return userDTO;
    }

    public UserDTO getUserByUsername(String username){
        UserModel user = userRepository.findByUsername(username);

        if(user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "error: user not found");
        }

        UserDTO userDTO = mapper.map(user, UserDTO.class);

        return userDTO;
    }

    public List<UserDTO> getAllUsers(){
        List<UserModel> users = userRepository.findAll();
        List<UserDTO> usersDTO = new ArrayList<>();

        users.forEach(user -> {
            UserDTO userDTO = mapper.map(user, UserDTO.class);
            usersDTO.add(userDTO);
        });

        return usersDTO;
    }

    public UserDTO updateUser(String id, UpdateDTO dto){
        UserModel user = userRepository.findById(UUID.fromString(id)).orElse(null);

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "error: user not found");
        }

        user.setBio(dto.getBio());
        user.setDisplayName(dto.getDisplayName());
        user.setUsername(dto.getUsername());

        userRepository.save(user);

        return mapper.map(user, UserDTO.class);
    }

    public void deleteUser(String id){
        UserModel user = userRepository.findById(UUID.fromString(id)).orElse(null);

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "error: user not found");
        }

        userRepository.delete(user);
    }
}
