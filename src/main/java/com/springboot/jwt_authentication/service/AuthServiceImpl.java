package com.springboot.jwt_authentication.service;

import com.springboot.jwt_authentication.dto.authentication.LoginRequest;
import com.springboot.jwt_authentication.dto.authentication.RegisterRequest;
import com.springboot.jwt_authentication.exception.UserExistedException;
import com.springboot.jwt_authentication.model.User;
import com.springboot.jwt_authentication.repository.UserRepository;
import com.springboot.jwt_authentication.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;


    @Override
    public String login(LoginRequest dto) {
        Optional<User> userOptional = userRepository.findByUsername(dto.getUsername());
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException(dto.getUsername());
        }
        UserDetails userdetails = User.builder()
                .username(dto.getUsername())
                .build();
        return jwtUtil.generateToken(userdetails);
    }

    @Override
    public User register(RegisterRequest dto) {
        Optional<User> userOptionalByUsername = userRepository.findByUsername(dto.getUsername());
        Optional<User> userOptionalByEmail = userRepository.findByEmail(dto.getEmail());
        if (userOptionalByUsername.isPresent() || userOptionalByEmail.isPresent()) {
            throw new UserExistedException();
        }

        return userRepository.save(User.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .email(dto.getEmail())
                .build());
    }

}
