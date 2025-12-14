package com.kaamsetu.modules.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record VerifyOtpRequest(@NotBlank String phone, @NotBlank String otp) { }
