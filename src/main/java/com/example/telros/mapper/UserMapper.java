package com.example.telros.mapper;


import com.example.telros.dto.UserDTO;
import com.example.telros.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;

@Mapper
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "dataOfBirth", source = "dataOfBirth", qualifiedByName = "toEntityDateResolver")
    User toEntity(UserDTO source);

    @Mapping(target = "imageBase64", source = "image", qualifiedByName = "toDtoImageResolver")
    @Mapping(target = "dataOfBirth", source = "dataOfBirth", qualifiedByName = "toDotDateResolver")
    UserDTO toDto(User source);
    List<UserDTO> toDto(List<User> source);

    /**
     * конвертация массива байтов в теккстовое представление формата Base64 для метода toDto
     */
    @Named("toDtoImageResolver")
    static String toDtoImageResolver(byte[] image) {
        if (image == null)
            return null;
        byte[] encodedBase64 = Base64.getEncoder().encode(image); //Base64.encode(image);
        return new String(encodedBase64, StandardCharsets.UTF_8);
    }

    /**
     * конвертация текстового представления даты в LocalDate для метода toEntity
     */
    @Named("toEntityDateResolver")
    static LocalDate toEntityDateResolver(String date) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        return LocalDate.parse(date, format);
    }

    /**
     * конвертация LocalDate в текстовое представление формата dd.MM.yyyy
     */
    @Named("toDotDateResolver")
    static String toDotDateResolver(LocalDate date) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        return date.format(format);
    }
}
