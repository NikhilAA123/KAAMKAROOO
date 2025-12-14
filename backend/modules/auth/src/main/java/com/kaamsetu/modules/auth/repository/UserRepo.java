package com.kaamsetu.modules.auth.repository;

import com.kaamsetu.modules.auth.domain.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByPhone(String phone);
    Optional<User> findById(String id);
    User save(User user);
}
