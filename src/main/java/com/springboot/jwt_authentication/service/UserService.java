package com.springboot.jwt_authentication.service;

import com.springboot.jwt_authentication.model.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

}
