package com.oekrem.jwt_deneme.controllers;

import com.oekrem.jwt_deneme.dtos.requests.CreateUserRequest;
import com.oekrem.jwt_deneme.dtos.requests.UpdateUserRequest;
import com.oekrem.jwt_deneme.dtos.responses.UserResponse;
import com.oekrem.jwt_deneme.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid CreateUserRequest createUserRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(createUserRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable UUID id, @RequestBody @Valid UpdateUserRequest updateUserRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(id, updateUserRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

}
