package com.kaamsetu.modules.auth.repository;

import com.kaamsetu.modules.auth.domain.User;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryUserRepository implements UserRepository {

    private final Map<String, User> byPhone = new ConcurrentHashMap<>();
    private final Map<String, User> byId = new ConcurrentHashMap<>();

    @Override
    public Optional<User> findByPhone(String phone) {
        return Optional.ofNullable(byPhone.get(phone));
    }

    @Override
    public User save(User user) {
        byPhone.put(user.phone(), user);
        byId.put(user.id(), user);
        return user;
    }

    @Override
    public Optional<User> findById(String id) {
        return Optional.ofNullable(byId.get(id));
    }
}
