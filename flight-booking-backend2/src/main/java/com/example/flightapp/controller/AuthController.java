
package com.example.flightapp.controller;

import com.example.flightapp.dto.AdminLoginRequest;
import com.example.flightapp.dto.LoginRequest;
import com.example.flightapp.dto.RegisterRequest;
import com.example.flightapp.service.AuthService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthService auth;
    public AuthController(AuthService auth) { this.auth = auth; }

    @PostMapping("/users/register")
    public Map<String, Object> register(@Validated @RequestBody RegisterRequest req) {
        return auth.registerUser(req);
    }

    @PostMapping("/users/login")
    public Map<String, Object> login(@Validated @RequestBody LoginRequest req) {
        return auth.loginUser(req);
    }

    @PostMapping("/admin/login")
    public Map<String, String> adminLogin(@Validated @RequestBody AdminLoginRequest req) {
        return auth.loginAdmin(req);
    }
}
