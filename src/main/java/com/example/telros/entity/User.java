package com.example.telros.entity;

import lombok.*;
import org.hibernate.annotations.Type;

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
    @Column(name = "user_id")
    private Long Id;

    /**
     * Имя
     */
    @Column(name = "user_first_name")
    private String firstName;

    /**
     * Фамилия
     */
    @Column(name = "user_second_name")
    private String secondName;

    /**
     * Отчество
     */
    @Column(name = "user_middle_name")
    private String middleName;

    /**
     * Data of Birth, Дата рождения
     */
    @Column(name = "user_dob")
    private LocalDate dataOfBirth;

    /**
     * Электронная почта
     */
    @Column(name = "user_email")
    private String email;

    /**
     * Номер телефона
     */
    @Column(name = "user_phone_number")
    private String phoneNumber;

    /**
     * Фотограифия в массиве byte
     */
    @Column(name = "image_byte")
    @Lob
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] image;
}
