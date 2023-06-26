package com.example.testtask.demo.utils;

import com.example.testtask.demo.dto.UserDto;
import com.example.testtask.demo.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserDtoConverterTest {
    User validUser;
    UserDto validUserDto;

    @BeforeEach
    void init() {
        validUser = new User("validName", "validEmail@gmail.com");

        validUserDto = UserDto.builder()
                .name("validName")
                .email("validEmail@gmail.com")
                .build();
    }

    @Test
    void shouldConvertUserModelToDto() {
        assertEquals(validUserDto, UserDtoConverter.toDto(validUser));
    }

    @Test
    void shouldConvertUserDtoToModel() {
        assertEquals(validUser, UserDtoConverter.toUser(validUserDto));
    }
}