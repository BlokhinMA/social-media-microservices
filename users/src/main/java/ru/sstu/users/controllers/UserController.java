package ru.sstu.users.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sstu.users.models.User;
import ru.sstu.users.services.UserService;
import lombok.RequiredArgsConstructor;

import java.security.Principal;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/sign_up")
    public ResponseEntity<?> create(@RequestBody User user) {
        return ResponseEntity.ok(userService.create(user));
    }

    @GetMapping("/my_profile")
    public User getUserByPrincipal(Principal principal) {
        return userService.getUserByPrincipal(principal);
    }

    @GetMapping("/profile/{login}")
    public User getUserByLogin(@PathVariable String login, Principal principal) {
        return userService.getUserByLogin(login);
    }

}
