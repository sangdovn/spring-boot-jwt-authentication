package com.springboot.jwt_authentication.repository;

import com.springboot.jwt_authentication.model.Role;
import com.springboot.jwt_authentication.model.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(RoleEnum name);
}
