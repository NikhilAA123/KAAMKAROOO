package com.kaamsetu.modules.auth.controller;

import com.kaamsetu.common.dto.ApiResponse;
import com.kaamsetu.modules.auth.dto.AuthTokenResponse;
import com.kaamsetu.modules.auth.dto.RequestOtpRequest;
import com.kaamsetu.modules.auth.dto.VerifyOtpRequest;
import com.kaamsetu.modules.auth.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/request-otp")
    public ResponseEntity<ApiResponse<Void>> requestOtp(@Valid @RequestBody RequestOtpRequest req) {
        authService.requestOtp(req.phone());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(ApiResponse.ok(null));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<ApiResponse<AuthTokenResponse>> verifyOtp(@Valid @RequestBody VerifyOtpRequest req) {
        AuthTokenResponse resp = authService.verifyOtpAndIssueToken(req.phone(), req.otp());
        return ResponseEntity.ok(ApiResponse.ok(resp));
    }
}
