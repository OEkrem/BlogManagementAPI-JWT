package com.oekrem.jwt_deneme.services;

import com.oekrem.jwt_deneme.dtos.requests.CreateUserRequest;
import com.oekrem.jwt_deneme.dtos.requests.UpdateUserRequest;
import com.oekrem.jwt_deneme.dtos.responses.UserResponse;
import com.oekrem.jwt_deneme.models.User;

import java.util.List;
import java.util.UUID;

public interface UserService {

    List<UserResponse> getAll();
    UserResponse getUserById(UUID id);
    void deleteUserById(UUID id);
    UserResponse createUser(CreateUserRequest createUserRequest);
    UserResponse updateUser(UUID id, UpdateUserRequest updateUserRequest);

    User validateUserById(UUID id);
    User validateUserByEmail(String email);

}
