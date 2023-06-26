package com.example.testtask.demo.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserDto {
    String id;

    String name;
    String email;
}


