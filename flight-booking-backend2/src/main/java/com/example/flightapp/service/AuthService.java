
package com.example.flightapp.service;

import com.example.flightapp.dto.AdminLoginRequest;
import com.example.flightapp.dto.LoginRequest;
import com.example.flightapp.dto.RegisterRequest;
import com.example.flightapp.entity.User;
import com.example.flightapp.repository.UserRepository;
import com.example.flightapp.security.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {
    private final UserRepository users;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository users, PasswordEncoder encoder, JwtUtil jwtUtil) {
        this.users = users; this.encoder = encoder; this.jwtUtil = jwtUtil;
    }

    public Map<String, Object> registerUser(RegisterRequest req) {
        if (users.findByEmail(req.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already registered");
        }
        User u = new User();
        u.setEmail(req.getEmail());
        u.setName(req.getName());
        u.setPhone(req.getPhone());
        u.setUsername(req.getEmail()); // username mirrors email for users
        u.setPassword(encoder.encode(req.getPassword()));
        u.setRole(User.Role.USER);
        users.save(u);

        Map<String,Object> claims = new HashMap<>();
        claims.put("role", u.getRole().name());
        String token = jwtUtil.generateToken(u.getId().toString(), claims);
        Map<String,Object> res = new HashMap<>();
        Map<String,Object> userMap = new HashMap<>();
        userMap.put("id", u.getId());
        userMap.put("email", u.getEmail());
        userMap.put("name", u.getName());
        res.put("token", token);
        res.put("user", userMap);
        return res;
    }

    public Map<String, Object> loginUser(LoginRequest req) {
        User u = users.findByEmail(req.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));
        if (!encoder.matches(req.getPassword(), u.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }
        Map<String,Object> claims = new HashMap<>();
        claims.put("role", u.getRole().name());
        String token = jwtUtil.generateToken(u.getId().toString(), claims);
        Map<String,Object> res = new HashMap<>();
        Map<String,Object> userMap = new HashMap<>();
        userMap.put("id", u.getId());
        userMap.put("email", u.getEmail());
        userMap.put("name", u.getName());
        res.put("token", token);
        res.put("user", userMap);
        return res;
    }

    public Map<String, String> loginAdmin(AdminLoginRequest req) {
        User admin = users.findByUsername(req.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));
        if (admin.getRole() != User.Role.ADMIN || !encoder.matches(req.getPassword(), admin.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }
        Map<String,Object> claims = new HashMap<>();
        claims.put("role", admin.getRole().name());
        String token = jwtUtil.generateToken(admin.getId().toString(), claims);
        Map<String,String> res = new HashMap<>();
        res.put("token", token);
        return res;
    }
}
