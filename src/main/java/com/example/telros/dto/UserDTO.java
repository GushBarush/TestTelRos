package com.example.telros.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {

    private Long id;

    /**
     * Имя
     */
    private String firstName;

    /**
     * Фамилия
     */
    private String secondName;

    /**
     * Отчество
     */
    private String middleName;

    /**
     * Data of Birth, Дата рождения
     */
    private String dataOfBirth;

    /**
     * Электронная почта
     */
    private String email;

    /**
     * Номер телефона
     */
    private String phoneNumber;

    /**
     * Фотограифия в формате Base64
     */
    private String imageBase64;
}
