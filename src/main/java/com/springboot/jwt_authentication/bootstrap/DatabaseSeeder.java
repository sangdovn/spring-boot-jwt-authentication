package com.springboot.jwt_authentication.bootstrap;

import com.springboot.jwt_authentication.model.Role;
import com.springboot.jwt_authentication.model.RoleEnum;
import com.springboot.jwt_authentication.model.User;
import com.springboot.jwt_authentication.repository.RoleRepository;
import com.springboot.jwt_authentication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Order(1)
public class DatabaseSeeder implements ApplicationListener<ContextRefreshedEvent> {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        loadRoles();
        loadUsers();
    }

    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }

    private void loadRoles() {
        RoleEnum[] roles = {RoleEnum.ADMIN, RoleEnum.USER};

        Arrays.stream(roles).forEach(role -> {
            Optional<Role> roleOptional = roleRepository.findByName(role);
            if (roleOptional.isEmpty()) {
                roleRepository.save(Role.builder()
                        .name(role)
                        .build());
            }
        });
    }

    private void loadUsers() {
        Optional<Role> roleAdmin = roleRepository.findByName(RoleEnum.ADMIN);
        Optional<Role> roleUser = roleRepository.findByName(RoleEnum.USER);

        roleAdmin.ifPresent(role -> {
            Optional<User> userOptional = userRepository.findByUsername("admin");

            if (userOptional.isEmpty()) {
                userRepository.save(User.builder()
                        .firstName("admin")
                        .lastName("admin")
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
                        .email("admin@admin.com")
                        .roles(Set.of(role))
                        .build());
            }
        });

        roleUser.ifPresent(role -> {
            String username;
            for (int i = 0; i < 10; i++) {
                username = "user" + (i + 1);
                Optional<User> userOptional = userRepository.findByUsername(username);

                if (userOptional.isEmpty()) {
                    userRepository.save(User.builder()
                            .firstName(username)
                            .lastName("demo")
                            .username(username)
                            .password(passwordEncoder.encode("secret"))
                            .email(username + "@email.com")
                            .roles(Set.of(role))
                            .build());
                }
            }
        });
    }
}
