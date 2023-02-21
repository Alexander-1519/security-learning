package com.example.securitylearning.auth;

import java.util.Optional;

public interface UserDao {

    public Optional<UserDetailsImpl> getUserByUsername(String username);
}
