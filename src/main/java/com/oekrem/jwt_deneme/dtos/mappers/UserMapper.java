package com.oekrem.jwt_deneme.dtos.mappers;

import com.oekrem.jwt_deneme.dtos.requests.CreateUserRequest;
import com.oekrem.jwt_deneme.dtos.requests.UpdateUserRequest;
import com.oekrem.jwt_deneme.dtos.responses.UserResponse;
import com.oekrem.jwt_deneme.models.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    public User toUserFromCreateUserRequest(CreateUserRequest createUserRequest);
    public User toUserFromUpdateUserRequest(UpdateUserRequest updateUserRequest);
    public UserResponse toUserResponse(User user);

}
