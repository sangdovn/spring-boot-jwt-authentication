package com.springboot.jwt_authentication.util;

import com.springboot.jwt_authentication.model.Role;
import org.modelmapper.AbstractConverter;

import java.util.Set;
import java.util.stream.Collectors;

public class RoleSetToStringSetConverter extends AbstractConverter<Set<Role>, Set<String>> {

    @Override
    protected Set<String> convert(Set<Role> roleSet) {
        return roleSet.stream()
                .map(role -> role.getName().name())
                .collect(Collectors.toSet());
    }
}
