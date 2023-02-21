package com.example.securitylearning.auth;

import com.example.securitylearning.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserDaoImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<UserDetailsImpl> getUserByUsername(String username) {
        return getAllUsers()
                .stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
    }

    private List<UserDetailsImpl> getAllUsers() {
        return List.of(
                new UserDetailsImpl(new ArrayList<>(UserRole.ADMIN.getGrantedAuthority()),
                        passwordEncoder.encode("admin"),
                        "admin",
                        true,
                        true,
                        true,
                        true),
                new UserDetailsImpl(new ArrayList<>(UserRole.ADMIN_TRAINEE.getGrantedAuthority()),
                        passwordEncoder.encode("adminTrainee"),
                        "adminTrainee",
                        true,
                        true,
                        true,
                        true),
                new UserDetailsImpl(new ArrayList<>(UserRole.STUDENT.getGrantedAuthority()),
                        passwordEncoder.encode("student"),
                        "student",
                        true,
                        true,
                        true,
                        true)
        );
    }
}
