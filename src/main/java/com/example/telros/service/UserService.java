package com.example.telros.service;

import com.example.telros.dto.UserDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserDTO> getAllUsers();

    Optional<UserDTO> getUserById(Long userId);

    void deleteUser(Long userId);

    UserDTO createUser(UserDTO userDTO, MultipartFile image);

    UserDTO updateUser(UserDTO userDTO, MultipartFile image);
}
