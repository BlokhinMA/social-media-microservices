package ru.sstu.authentication.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sstu.authentication.models.AuthResponse;
import ru.sstu.authentication.models.LogRequest;
import ru.sstu.authentication.models.RegRequest;
import ru.sstu.authentication.services.AuthService;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LogRequest request) {
        AuthResponse authResponse = authService.login(request);
        if (authResponse != null)
            return ResponseEntity.ok(authResponse);
        else return ResponseEntity.badRequest().build();
    }

}
