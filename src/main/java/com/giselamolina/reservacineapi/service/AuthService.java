package com.giselamolina.reservacineapi.service;

import com.giselamolina.reservacineapi.dto.AuthResponse;
import com.giselamolina.reservacineapi.dto.LoginRequest;
import com.giselamolina.reservacineapi.dto.RegisterRequest;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}