package com.example.telros.controller;

import com.example.telros.dto.UserDTO;
import com.example.telros.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;

    /**
     * @return возвращение списка всех пользователей
     * в случае если пользователей нету возвращается пустрой список
     */
    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    /**
     * @param userId принимается id пользователся
     * @return возвращается пользователь по id
     * в случае если пользователь не найден возвращается notFound
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable(name = "id") Long userId) {
        Optional<UserDTO> userDTO = userService.getUserById(userId);
        if (userDTO.isPresent())
            return ResponseEntity.of(userDTO);
        else
            return ResponseEntity.notFound().build();
    }

    /**
     * @param user принимается Json пользователь конвертируется в UserDTO
     * @param image принимается файл с фотогравией (не обязательно)
     * @return при удачном создании пользователя возвращает пользователя
     * в случае если произошла ошибка при сохранение возващает HttpStatus.NOT_MODIFIED
     */
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> createUser(@RequestPart UserDTO user,
                                              @RequestPart(required = false) MultipartFile image) {

        UserDTO userDTO;

        try {
            userDTO = userService.createUser(user, image);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
    }

    /**
     * @param user принимается Json пользователь конвертируется в UserDTO
     * @param image принимается файл с фотогравией (не обязательно)
     * @return при удачном обновлении пользователя возвращает пользователя
     * в случае если произошла ошибка при сохранение возващает HttpStatus.NOT_MODIFIED
     */
    @PutMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> updateUser(@RequestPart UserDTO user,
                                              @RequestPart(required = false) MultipartFile image) {

        UserDTO userDTO;

        try {
            userDTO = userService.updateUser(user, image);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.OK).body(userDTO);
    }

    /**
     * @param userId принимается id пользователся
     * @return при удачном удаление возвращается HttpStatus.OK
     * в случае ошибки при удалении возвращается HttpStatus.NOT_MODIFIED
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(name = "id") Long userId) {

        try {
            userService.deleteUser(userId);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(e.getMessage());
        }
    }
}
