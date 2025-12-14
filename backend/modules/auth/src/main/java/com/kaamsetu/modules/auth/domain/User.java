package com.kaamsetu.modules.auth.domain;

import java.time.Instant;
import java.util.UUID;

public class User {
    private final String id;
    private final String phone;
    private String name;
    private Instant createdAt;

    public User(String phone) {
        this.id = UUID.randomUUID().toString();
        this.phone = phone;
        this.createdAt = Instant.now();
    }

    public String id() { return id; }
    public String phone() { return phone; }
    public String name() { return name; }
    public Instant createdAt() { return createdAt; }
    public void setName(String name) { this.name = name; }
}
