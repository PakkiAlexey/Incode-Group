package com.example.testtask.demo.utils;

import com.example.testtask.demo.dto.UserDto;
import com.example.testtask.demo.models.User;


public class UserDtoConverter {

    private UserDtoConverter(){}
    public static UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public static User toUser(UserDto userDto) {
        return new User(userDto.getName(), userDto.getEmail());
    }
}
