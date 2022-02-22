package com.example.telros.service.impl;

import com.example.telros.dto.UserDTO;
import com.example.telros.entity.User;
import com.example.telros.repository.UserRepo;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RunWith(SpringRunner.class)
@SpringBootTest
@FieldDefaults(level = AccessLevel.PRIVATE)
class UserServiceImplTest {

        final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        final User USER_IN_DB = new User(1L, "Admin",
                "Admin", "Admin", LocalDate.parse("22.02.2022", DATE_FORMAT),
                "admin@admin.com", "89999999999", null);

        final UserDTO USER_DTO = new UserDTO(1L, "User",
                "User", "User", "22.03.2021",
                "user@user.com", "87777777777", null);

        final MultipartFile FILE = new MultipartFile() {
            @Override
            public String getName() {
                return null;
            }

            @Override
            public String getOriginalFilename() {
                return null;
            }

            @Override
            public String getContentType() {
                return null;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public long getSize() {
                return 0;
            }

            @Override
            public byte[] getBytes() throws IOException {
                return new byte[0];
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return null;
            }

            @Override
            public void transferTo(File dest) throws IOException, IllegalStateException {

            }
        };

    @Autowired
    UserServiceImpl userService;

    @MockBean
    UserRepo userRepo;

    @Test
    void createUser() {
        Mockito.when(userRepo.save(Mockito.any(User.class)))
                .thenAnswer(i -> i.getArguments()[0]);

        UserDTO createdUser = userService.createUser(USER_DTO, FILE);

        createdUser.setId(1L);

        Assertions.assertEquals(USER_DTO.getId(), createdUser.getId());
        Assertions.assertEquals(USER_DTO.getFirstName(), createdUser.getFirstName());
        Assertions.assertEquals(USER_DTO.getSecondName(), createdUser.getSecondName());
        Assertions.assertEquals(USER_DTO.getMiddleName(), createdUser.getMiddleName());
        Assertions.assertEquals(USER_DTO.getDataOfBirth(), createdUser.getDataOfBirth());
        Assertions.assertEquals(USER_DTO.getEmail(), createdUser.getEmail());
        Assertions.assertEquals(USER_DTO.getPhoneNumber(), createdUser.getPhoneNumber());
    }

    @Test
    void updateUser() {
        Mockito.doReturn(USER_IN_DB)
                .when(userRepo)
                .getById(1L);

        Mockito.when(userRepo.save(Mockito.any(User.class)))
                .thenAnswer(i -> i.getArguments()[0]);

        UserDTO updatedUser = userService.updateUser(USER_DTO, FILE);

        Assertions.assertEquals(USER_DTO.getId(), updatedUser.getId());
        Assertions.assertEquals(USER_DTO.getFirstName(), updatedUser.getFirstName());
        Assertions.assertEquals(USER_DTO.getSecondName(), updatedUser.getSecondName());
        Assertions.assertEquals(USER_DTO.getMiddleName(), updatedUser.getMiddleName());
        Assertions.assertEquals(USER_DTO.getDataOfBirth(), updatedUser.getDataOfBirth());
        Assertions.assertEquals(USER_DTO.getEmail(), updatedUser.getEmail());
        Assertions.assertEquals(USER_DTO.getPhoneNumber(), updatedUser.getPhoneNumber());
    }

}