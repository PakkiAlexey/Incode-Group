package com.example.testtask.demo.services;

import com.example.testtask.demo.dto.UserDto;
import com.example.testtask.demo.models.User;
import com.example.testtask.demo.persistence.UserRepository;
import com.example.testtask.demo.utils.UserDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.testtask.demo.utils.UserDataValidation.validateDto;

@Service
public class UserService {

    private final UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto getById(String id) {

        return userRepository.findById(id)
                .map(UserDtoConverter::toDto)
                .orElseThrow(() -> new ObjectNotFoundException("There is no user with such id"));
    }

    public UserDto create(UserDto userDto) {
        validateDto(userDto);

        User user = UserDtoConverter.toUser(userDto);
        return UserDtoConverter.toDto(userRepository.save(user));
    }

    public UserDto update(String id, UserDto userDto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("There is no user with such id"));

        validateDto(userDto);

        existingUser.setName(userDto.getName());
        existingUser.setEmail(userDto.getEmail());

        return UserDtoConverter.toDto(userRepository.save(existingUser));

    }
    public void delete(String id) {
        userRepository.deleteById(id);
    }

    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(UserDtoConverter::toDto)
                .collect(Collectors.toList());
    }
}
