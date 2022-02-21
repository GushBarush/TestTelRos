package com.example.telros.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "user", schema = "public")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "user_first_name", nullable = false)
    private String userFirstName; //Имя

    @Column(name = "user_second_name", nullable = false)
    private String userSecondName; //Фамилия

    @Column(name = "user_middle_name", nullable = false)
    private String userMiddleName; //Отчество

    @Column(name = "user_dob", nullable = false)
    private LocalDate userDOB; // Data of Birth, Дата рождения

    @Column(name = "user_email", nullable = false)
    private String userEmail; // Электронная почта

    @Column(name = "user_phone_number", nullable = false)
    private String userPhoneNumber; // Номер телефона

    @Lob
    @Column(name = "image_byte")
    private byte[] userImage; // Фотограифия в массиве byte
}
