package com.example.testtask.demo.utils;

import com.example.testtask.demo.dto.UserDto;
import com.example.testtask.demo.services.InvalidUserDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDataValidationTest {
    private UserDto validUserDto;
    private UserDto userWithEmptyNameDto;
    private UserDto userWithInvalidEmailDto;

    @BeforeEach
    public void init() {
        validUserDto = UserDto.builder()
                .name("validName")
                .email("validEmail@gmail.com")
                .build();

        userWithEmptyNameDto = UserDto.builder()
                .name("")
                .email("validEmail@gmail.com")
                .build();

        userWithInvalidEmailDto = UserDto.builder()
                .name("validName")
                .email("invalidMail")
                .build();
    }

    @Test
    void shouldNotThrowAnyException() {
        assertDoesNotThrow(() -> UserDataValidation.validateDto(validUserDto));
    }

    @Test
    void shouldThrowInvalidUserDataExceptionWhenNameIsEmpty() {
        InvalidUserDataException ex = assertThrows(InvalidUserDataException.class, () ->
                UserDataValidation.validateDto(userWithEmptyNameDto));

        assertEquals("User name must not be empty", ex.getMessage());
    }

    @Test
    void shouldThrowInvalidUserDtoExceptionWhenEmailIsInvalid() {
        InvalidUserDataException ex = assertThrows(InvalidUserDataException.class, () ->
                UserDataValidation.validateDto(userWithInvalidEmailDto));

        assertEquals("Please input valid email", ex.getMessage());
    }
}