package ru.sstu.users.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.sstu.users.models.User;
import ru.sstu.users.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{login}")
    public User getUserByLogin(@PathVariable String login) {
        return userService.getUserByLogin(login);
    }

    @GetMapping("/all")
    public List<User> getAll() {
        return userService.getAll();
    }

}
