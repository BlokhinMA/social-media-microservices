package ru.sstu.users.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sstu.users.models.AuthResponse;
import ru.sstu.users.models.LogRequest;
import ru.sstu.users.models.User;
import ru.sstu.users.services.AuthService;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@Log4j2
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign_up")
    public ResponseEntity<?> signUp(@RequestBody User request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/sign_in")
    public ResponseEntity<?> signIn(@RequestBody LogRequest request) {
        AuthResponse authResponse = authService.auth(request);
        if (authResponse != null)
            return ResponseEntity.ok(authResponse);
        else return ResponseEntity.badRequest().build();
    }

    @GetMapping("/get_token")
    public String getToken() {
        return authService.getAccessToken();
    }

}
