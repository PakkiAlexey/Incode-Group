package com.example.testtask.demo.services;

import com.example.testtask.demo.dto.UserDto;
import com.example.testtask.demo.models.User;
import com.example.testtask.demo.persistence.UserRepository;
import com.example.testtask.demo.utils.UserDtoConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    private UserService userService;
    private User validUser;
    private User anotherValidUser;
    private UserDto validUserDto;
    private UserDto userWithEmptyNameDto;
    private UserDto userWithInvalidEmailDto;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository);

        validUser = new User("validName", "validEmail@gmail.com");
        anotherValidUser = new User("anotherValidName", "anotherValidEmail@gmail.com");

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
    void shouldFindUser() {
        when(userRepository.findById(anyString())).thenReturn(Optional.of(validUser));

        assertEquals("validName", userService.getById("anyId").getName());
        assertEquals("validEmail@gmail.com", userService.getById("anyId").getEmail());
    }

    @Test
    void shouldThrowObjectNotFoundExceptionWhenFindUserWithNonexistenceId() {
        when(userRepository.findById("nonexistence"))
                .thenThrow(new ObjectNotFoundException("There is no user with such id"));

        ObjectNotFoundException ex = assertThrows(ObjectNotFoundException.class, () ->
                userService.getById("nonexistence"));

        assertEquals("There is no user with such id", ex.getMessage());
    }

    @Test
    void shouldCreateUser() {
        when(userRepository.save(validUser)).thenReturn(validUser);
        assertEquals("validName", userService.create(validUserDto).getName());
        assertEquals("validEmail@gmail.com", userService.create(validUserDto).getEmail());
    }

    @Test
    void shouldThrowInvalidUserDtoExceptionWhenCreateUserWithEmptyName() {
        InvalidUserDataException ex = assertThrows(InvalidUserDataException.class, () ->
                userService.create(userWithEmptyNameDto));

        assertEquals("User name must not be empty", ex.getMessage());
    }

    @Test
    void shouldThrowInvalidUserDtoExceptionWhenCreateUserWithInvalidEmail() {
        InvalidUserDataException ex = assertThrows(InvalidUserDataException.class, () ->
                userService.create(userWithInvalidEmailDto));

        assertEquals("Please input valid email", ex.getMessage());
    }

    @Test
    void shouldUpdateUser() {
        when(userRepository.findById(anyString())).thenReturn(Optional.of(validUser));

        User updatedValidUser = new User("updatedValidName", "updatedValidEmail@gmail.com");
        when(userRepository.save(validUser)).thenReturn(updatedValidUser);

        assertEquals("updatedValidName", userService.update("anyString", validUserDto).getName());
        assertEquals("updatedValidEmail@gmail.com", userService.update("anyString", validUserDto).getEmail());
    }

    @Test
    void shouldThrowObjectNotFoundExceptionWhenUpdateUserWithNonexistenceId() {
        when(userRepository.findById("nonexistence"))
                .thenThrow(new ObjectNotFoundException("There is no user with such id"));

        ObjectNotFoundException ex = assertThrows(ObjectNotFoundException.class, () ->
                userService.update("nonexistence", validUserDto));

        assertEquals("There is no user with such id", ex.getMessage());
    }

    @Test
    void shouldThrowInvalidUserDtoExceptionWhenUpdateUserWithEmptyName() {
        User validUser = new User("validName", "validEmail@gmail.com");
        when(userRepository.findById(anyString())).thenReturn(Optional.of(validUser));

        InvalidUserDataException ex = assertThrows(InvalidUserDataException.class, () ->
                userService.update("anyString", userWithEmptyNameDto));

        assertEquals("User name must not be empty", ex.getMessage());
    }

    @Test
    void shouldThrowInvalidUserDtoExceptionWhenUpdateUserWithInvalidEmail() {
        when(userRepository.findById(anyString())).thenReturn(Optional.of(validUser));

        InvalidUserDataException ex = assertThrows(InvalidUserDataException.class, () ->
                userService.update("anyString", userWithInvalidEmailDto));

        assertEquals("Please input valid email", ex.getMessage());
    }

    @Test
    void shouldDeleteUser() {
        String userId = "validId";

        userService.delete(userId);

        verify(userRepository).deleteById(userId);
    }

    @Test
    void shouldFindAllUsers() {
        List<User> expectedUsers = Arrays.asList(validUser, anotherValidUser);

        when(userRepository.findAll()).thenReturn(expectedUsers);

        List<UserDto> actualUsersDto = userService.findAll();

        List<UserDto> expectedUsersDto = expectedUsers.stream()
                .map(UserDtoConverter::toDto)
                .collect(Collectors.toList());

        assertEquals(expectedUsersDto.size(), actualUsersDto.size());
        assertEquals(expectedUsersDto, actualUsersDto);
    }
}
