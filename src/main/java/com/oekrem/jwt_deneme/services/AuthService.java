package com.oekrem.jwt_deneme.services;

import org.springframework.security.core.userdetails.UserDetails;

public interface AuthService {

    UserDetails authenticate(String username, String password);
    String generateToken(UserDetails userDetails);
    UserDetails validateToken(String token);

}
