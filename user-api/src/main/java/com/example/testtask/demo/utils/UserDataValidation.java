package com.example.testtask.demo.utils;

import com.example.testtask.demo.dto.UserDto;
import com.example.testtask.demo.services.InvalidUserDataException;

import java.util.regex.Pattern;

public class UserDataValidation {

    private UserDataValidation(){}
    public static void validateDto(UserDto userDto) {
        if (userDto.getName().isEmpty()) {
            throw new InvalidUserDataException("User name must not be empty");
        }

        if (!isValidEmail(userDto.getEmail())) {
            throw new InvalidUserDataException("Please input valid email");
        }
    }
    public static boolean isValidEmail(String emailId) {
        return Pattern.
                compile("^(.+)@(\\S+)$")
                .matcher(emailId).matches();
    }
}
