package com.example.telros.mapper;


import com.example.telros.dto.UserDTO;
import com.example.telros.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

@Mapper
public interface UserMapper {

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "userImage", ignore = true)
    User toEntity(UserDTO source);

    @Mapping(target = "imageBase64", source = "userImage", qualifiedByName = "toDtoImageResolver")
    UserDTO toDto(User source);
    List<UserDTO> toDto(List<User> source);

    @Named("toDtoImageResolver")
    static String toDtoImageResolver(byte[] image) {
        if (image == null)
            return null;
        byte[] encodedBase64 = Base64.getEncoder().encode(image); //Base64.encode(image);
        return new String(encodedBase64, StandardCharsets.UTF_8);
    }
}
