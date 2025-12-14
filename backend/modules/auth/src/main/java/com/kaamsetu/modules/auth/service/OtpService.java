package com.kaamsetu.modules.auth.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OtpService {

    record OtpEntry(String otp, Instant expiresAt) {}

    private final Map<String, OtpEntry> store = new ConcurrentHashMap<>();
    private final Random random = new Random();

    private final int ttlSeconds;

    public OtpService(@Value("${kaamsetu.auth.otp-ttl-seconds:300}") int ttlSeconds) {
        this.ttlSeconds = ttlSeconds;
    }

    public String generateAndStore(String phone) {
        String otp = String.format("%06d", random.nextInt(1_000_000));
        store.put(phone, new OtpEntry(otp, Instant.now().plusSeconds(ttlSeconds)));
        return otp;
    }

    public boolean verify(String phone, String otp) {
        OtpEntry entry = store.get(phone);
        if (entry == null) return false;
        if (Instant.now().isAfter(entry.expiresAt())) {
            store.remove(phone);
            return false;
        }
        boolean ok = entry.otp().equals(otp);
        if (ok) store.remove(phone);
        return ok;
    }

    // For cleanup - optional periodic task (not implemented here)
}
