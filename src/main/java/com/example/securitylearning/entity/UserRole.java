package com.example.securitylearning.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.example.securitylearning.entity.UserPermission.*;

public enum UserRole {
    STUDENT(Set.of()),
    ADMIN(Set.of(COURSE_WRITE, COURSE_READ, STUDENT_WRITE, STUDENT_READ)),
    ADMIN_TRAINEE(Set.of(COURSE_READ, STUDENT_READ));

    private final Set<UserPermission> permissions;

    UserRole(Set<UserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<UserPermission> getPermissions() {
        return permissions;
    }

    public Set<GrantedAuthority> getGrantedAuthority() {
        Set<GrantedAuthority> grantedAuthorities = permissions
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());

        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return grantedAuthorities;
    }
}
