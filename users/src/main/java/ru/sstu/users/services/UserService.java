package ru.sstu.users.services;

import lombok.AllArgsConstructor;
import ru.sstu.users.repositories.UserRepository;
import ru.sstu.users.models.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUserByLogin(String login) {
        User user = userRepository.findByLogin(login);
        if (user != null) {
            user.setEmail(null);
            user.setPassword(null);
            return user;
        }
        return null;
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

}
