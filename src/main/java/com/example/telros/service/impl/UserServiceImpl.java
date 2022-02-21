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

    @Override
    public UserDTO createUser(UserDTO userDTO, MultipartFile image) {
        byte[] imageByte = getImageBytes(image);

        User user = userMapper.toEntity(userDTO);
        user.setUserImage(imageByte);

        user = userRepo.save(user);

        return userMapper.toDto(user);
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO, MultipartFile image) throws RuntimeException {
        byte[] imageByte = getImageBytes(image);

        User user = userRepo.findById(userDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("Пользователь не найден: " + userDTO.getUserId()));

        if (userDTO.getUserFirstName() != null) // проверяем Имя
            user.setUserFirstName(userDTO.getUserFirstName());
        if (userDTO.getUserSecondName() != null) // проверяем Фамилию
            user.setUserSecondName(userDTO.getUserSecondName());
        if (userDTO.getUserMiddleName() != null) // проверяем Отчество
            user.setUserMiddleName(userDTO.getUserMiddleName());
        if (userDTO.getUserDOB() != null) // проверям дату рождения
            user.setUserDOB(userDTO.getUserDOB());
        if (userDTO.getUserEmail() != null) // проверяем email
            user.setUserEmail(userDTO.getUserEmail());
        if (userDTO.getUserPhoneNumber() != null) // проверяем номер телефона
            user.setUserPhoneNumber(userDTO.getUserPhoneNumber());
        if (imageByte != null) // проверяем фотографию
            user.setUserImage(imageByte);

        user = userRepo.save(user);

        return userMapper.toDto(user);
    }

    @Override
    public boolean deleteUser(Long userId) {
        return userRepo.deleteUserByUserId(userId);
    }

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
