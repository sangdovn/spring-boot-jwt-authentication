package com.springboot.jwt_authentication.controller;

import com.springboot.jwt_authentication.dto.user.UserProfile;
import com.springboot.jwt_authentication.model.User;
import com.springboot.jwt_authentication.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;


    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserProfile> userProfiles = users.stream()
                .map(user -> modelMapper.map(user, UserProfile.class))
                .toList();
        return ResponseEntity.ok(userProfiles);
    }

    @GetMapping("me")
    public ResponseEntity<?> me() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        UserProfile profile = modelMapper.map(currentUser, UserProfile.class);
        return ResponseEntity.ok(profile);
    }

}