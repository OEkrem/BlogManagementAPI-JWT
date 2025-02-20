package com.oekrem.jwt_deneme.services.impl;

import com.oekrem.jwt_deneme.dtos.mappers.UserMapper;
import com.oekrem.jwt_deneme.dtos.requests.CreateUserRequest;
import com.oekrem.jwt_deneme.dtos.requests.UpdateUserRequest;
import com.oekrem.jwt_deneme.dtos.responses.UserResponse;
import com.oekrem.jwt_deneme.exceptions.UserExceptions.EmailAlreadyTakenException;
import com.oekrem.jwt_deneme.models.User;
import com.oekrem.jwt_deneme.repositories.UserRepository;
import com.oekrem.jwt_deneme.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public List<UserResponse> getAll() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::toUserResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserResponse getUserById(UUID id) {
        User user = validateUserById(id);
        return userMapper.toUserResponse(user);
    }

    @Override
    @Transactional
    public void deleteUserById(UUID id) {
        userRepository.delete(validateUserById(id));
    }

    @Override
    @Transactional
    public UserResponse createUser(CreateUserRequest createUserRequest) {
        userRepository.findUserByEmail(createUserRequest.getEmail())
                .ifPresent( user -> {throw new EmailAlreadyTakenException("Email already in use: " + user.getEmail());} );

        User user = userMapper.toUserFromCreateUserRequest(createUserRequest);
        User createdUser = userRepository.save(user);
        return userMapper.toUserResponse(createdUser);
    }

    @Override
    @Transactional
    public UserResponse updateUser(UUID id, UpdateUserRequest updateUserRequest) {
        validateUserById(id);
        User user = userMapper.toUserFromUpdateUserRequest(updateUserRequest);
        user.setId(id);
        User updatedUser = userRepository.save(user);
        return userMapper.toUserResponse(updatedUser);
    }

    @Override
    public User validateUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));
    }

    @Override
    public User validateUserByEmail(String email) {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User with email " + email + " not found"));
    }


}
