package com.springboot.jwt_authentication.service;

import com.springboot.jwt_authentication.dto.authentication.LoginRequest;
import com.springboot.jwt_authentication.dto.authentication.RegisterRequest;
import com.springboot.jwt_authentication.model.User;

public interface AuthService {

    String login(LoginRequest dto);

    User register(RegisterRequest dto);

}
