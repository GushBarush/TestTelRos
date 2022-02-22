package com.example.telros.service.impl;

import com.example.telros.dto.UserDTO;
import com.example.telros.entity.User;
import com.example.telros.mapper.UserMapper;
import com.example.telros.repository.UserRepo;
import com.example.telros.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

    UserRepo userRepo;
    UserMapper userMapper;

    /**
     * @return возвращаем список всех пользователей
     */
    public List<UserDTO> getAllUsers() {
        return userMapper.toDto(userRepo.findAll());
    }

    /**
     * @param userId принимаем Id пользователя
     * @return возвращаем пользователя по Id
     */
    @Override
    public Optional<UserDTO> getUserById(Long userId) {
        return userRepo.findById(userId)
                .map(userMapper::toDto);
    }

    /**
     * @param userDTO примипаем пользователя
     * @param image примимаем MultipartFile с фотографией
     * @return при успешном создании пользоватеся возвращаем пользователя
     * в случае ошибки возвращаем RuntimeException с тектом ошибки
     */
    @Override
    public UserDTO createUser(UserDTO userDTO, MultipartFile image) throws RuntimeException {
        byte[] imageByte = getImageBytes(image);

        if (userDTO.getFirstName() == null)
            throw  new RuntimeException("Имя не может быть пустым");
        if (userDTO.getSecondName() == null)
            throw  new RuntimeException("Фамилия не божет быть пустой");
        if (userDTO.getMiddleName() == null)
            throw  new RuntimeException("Отчество не может быть пустым");
        if (userDTO.getDataOfBirth() == null)
            throw  new RuntimeException("Дата рождения не может быть пустой");
        if (userDTO.getPhoneNumber() == null)
            throw  new RuntimeException("Номер телефона не может быть пустым");
        if (userDTO.getEmail() == null)
            throw  new RuntimeException("Email не может быть пустым");

        User user = userMapper.toEntity(userDTO);
        user.setImage(imageByte);
        user = userRepo.save(user);

        return userMapper.toDto(user);
    }

    /**
     * @param userDTO примипаем пользователя
     * @param image примимаем MultipartFile с фотографией
     * проверяем все поля на изменения
     * @return при успешном обновлении пользоватеся возвращаем пользователя
     * в случае ошибки возвращаем RuntimeException с тектом ошибки
     */
    @Override
    public UserDTO updateUser(UserDTO userDTO, MultipartFile image) throws RuntimeException {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        byte[] imageByte = getImageBytes(image);

        User user = userRepo.getById(userDTO.getId());

        //  Проверяем поля на наличие изменений
        if (userDTO.getFirstName() != null)
            user.setFirstName(userDTO.getFirstName());
        if (userDTO.getSecondName() != null)
            user.setSecondName(userDTO.getSecondName());
        if (userDTO.getMiddleName() != null)
            user.setMiddleName(userDTO.getMiddleName());
        if (userDTO.getDataOfBirth() != null)
            user.setDataOfBirth(LocalDate.parse(userDTO.getDataOfBirth(), format));
        if (userDTO.getEmail() != null)
            user.setEmail(userDTO.getEmail());
        if (userDTO.getPhoneNumber() != null)
            user.setPhoneNumber(userDTO.getPhoneNumber());
        if (imageByte != null)
            user.setImage(imageByte);

        user = userRepo.save(user);

        return userMapper.toDto(user);
    }

    @Override
    public void deleteUser(Long userId) {
        User user = userRepo.getById(userId);

        userRepo.delete(user);
    }

    /**
     * получение из MultipartFile image массива byte
     */
    private byte[] getImageBytes(MultipartFile image) {
        if (image == null)
            return null;
        byte[] imageBytes = null;
        try {
            if (!image.isEmpty())
                imageBytes = image.getBytes();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return imageBytes;
    }
}
