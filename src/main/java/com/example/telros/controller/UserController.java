package com.example.telros.controller;

import com.example.telros.dto.UserDTO;
import com.example.telros.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable(name = "id") Long userId) {
        Optional<UserDTO> userDTO = userService.getUserById(userId);
        if (userDTO.isPresent())
            return ResponseEntity.of(userDTO);
        else
            return ResponseEntity.notFound().build();
    }

    /**
     * @param image если null, сохраняем без фото
     */
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestPart UserDTO user,
                                              @RequestPart @Nullable MultipartFile image) {
        UserDTO userDTO = userService.createUser(user, image);

        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
    }

    /**
     * @param image если null, сохраняем без фото
     */
    @PutMapping
    public ResponseEntity<UserDTO> updateUser(@RequestPart UserDTO user,
                                              @RequestPart @Nullable MultipartFile image) {

        UserDTO userDTO;

        try {
            userDTO = userService.updateUser(user, image);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }

        return ResponseEntity.status(HttpStatus.OK).body(userDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(name = "id") Long userId) {
        if (userService.deleteUser(userId)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
