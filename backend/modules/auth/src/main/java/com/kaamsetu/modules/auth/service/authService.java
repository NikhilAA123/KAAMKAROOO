package com.kaamsetu.modules.auth.service;

import com.kaamsetu.modules.auth.domain.User;
import com.kaamsetu.modules.auth.dto.AuthTokenResponse;
import com.kaamsetu.modules.auth.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final OtpService otpService;

    public AuthService(UserRepository userRepository, OtpService otpService) {
        this.userRepository = userRepository;
        this.otpService = otpService;
    }

    public void requestOtp(String phone) {
        // create user if not exist (lightweight)
        userRepository.findByPhone(phone).orElseGet(() -> userRepository.save(new User(phone)));
        String otp = otpService.generateAndStore(phone);
        // TODO: integrate with real SMS provider adapter
        System.out.printf("DEBUG: OTP for %s is %s%n", phone, otp);
    }

    public AuthTokenResponse verifyOtpAndIssueToken(String phone, String otp) {
        boolean ok = otpService.verify(phone, otp);
        if (!ok) {
            throw new IllegalArgumentException("Invalid OTP or expired");
        }
        User user = userRepository.findByPhone(phone).orElseThrow(() -> new IllegalStateException("User not found"));
        // Create a naive token (UUID). Replace with JWT later.
        String token = UUID.randomUUID().toString();
        // TODO: store token in Redis for session management if needed
        return new AuthTokenResponse(token, user.id());
    }
}
