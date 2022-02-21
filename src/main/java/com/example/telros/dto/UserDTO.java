package com.example.telros.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {

    private Long userId;

    private String userFirstName; //Имя

    private String userSecondName; //Фамилия

    private String userMiddleName; //Отчество

    private LocalDate userDOB; // Data of Birth, Дата рождения

    private String userEmail; // Электронная почта

    private String userPhoneNumber; // Номер телефона

    private String imageBase64; // Фотограифия в формате Base64
}
