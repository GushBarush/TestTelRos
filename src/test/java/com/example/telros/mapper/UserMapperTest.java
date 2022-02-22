package com.example.telros.mapper;

import com.example.telros.dto.UserDTO;
import com.example.telros.entity.User;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RunWith(SpringRunner.class)
@SpringBootTest
@FieldDefaults(level = AccessLevel.PRIVATE)
class UserMapperTest {

    final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    final User USER_IN_DB = new User(1L, "Admin",
            "Admin", "Admin", LocalDate.parse("22.02.2022", DATE_FORMAT),
            "admin@admin.com", "89999999999", null);

    final UserDTO USER_DTO = new UserDTO(null, "User",
            "User", "User", "22.03.2021",
            "user@user.com", "87777777777", null);

    @Autowired
    UserMapper userMapper;

    @Test
    void toEntity() {
        User user = userMapper.toEntity(USER_DTO);

        Assertions.assertEquals(USER_DTO.getFirstName(), user.getFirstName());
        Assertions.assertEquals(USER_DTO.getSecondName(), user.getSecondName());
        Assertions.assertEquals(USER_DTO.getMiddleName(), user.getMiddleName());
        Assertions.assertEquals(LocalDate.parse(USER_DTO.getDataOfBirth(), DATE_FORMAT), user.getDataOfBirth());
        Assertions.assertEquals(USER_DTO.getEmail(), user.getEmail());
        Assertions.assertEquals(USER_DTO.getPhoneNumber(), user.getPhoneNumber());
    }

    @Test
    void toDto() {
        UserDTO userDTO = userMapper.toDto(USER_IN_DB);

        Assertions.assertEquals(USER_IN_DB.getFirstName(), userDTO.getFirstName());
        Assertions.assertEquals(USER_IN_DB.getSecondName(), userDTO.getSecondName());
        Assertions.assertEquals(USER_IN_DB.getMiddleName(), userDTO.getMiddleName());
        Assertions.assertEquals(USER_IN_DB.getDataOfBirth().format(DATE_FORMAT), userDTO.getDataOfBirth());
        Assertions.assertEquals(USER_IN_DB.getEmail(), userDTO.getEmail());
        Assertions.assertEquals(USER_IN_DB.getPhoneNumber(), userDTO.getPhoneNumber());
    }
}