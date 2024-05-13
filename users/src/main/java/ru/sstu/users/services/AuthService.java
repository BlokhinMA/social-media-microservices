package ru.sstu.users.services;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import ru.sstu.users.models.AuthResponse;
import ru.sstu.users.models.User;
import ru.sstu.users.repositories.UserRepository;

import java.util.Objects;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    @Getter
    private String accessToken;
    private String refreshToken;
    @Getter
    private User user;

    public AuthResponse register(User request) {
        request.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        request.setRole("USER");
        User registeredUser = userRepository.save(request);

        assert registeredUser != null;
        String accessToken = jwtUtil.generate(String.valueOf(registeredUser.getId()), registeredUser.getRole(), "ACCESS");
        String refreshToken = jwtUtil.generate(String.valueOf(registeredUser.getId()), registeredUser.getRole(), "REFRESH");

        return new AuthResponse(accessToken, refreshToken);
    }

    public AuthResponse auth(User request) {
        User authorizedUser = userRepository.findByLogin(request.getLogin());

        if (authorizedUser != null && BCrypt.checkpw(request.getPassword(), authorizedUser.getPassword())) {
            accessToken = jwtUtil.generate(String.valueOf(authorizedUser.getId()), authorizedUser.getRole(), "ACCESS");
            refreshToken = jwtUtil.generate(String.valueOf(authorizedUser.getId()), authorizedUser.getRole(), "REFRESH");

            user = authorizedUser;

            return new AuthResponse(accessToken, refreshToken);
        } else return null;
    }

    public void logout() {
        accessToken = "";
        user = new User();
    }

    public String redirect(String page) {
        if (!accessToken.isEmpty())
            return "redirect:/my_profile";
        return page;
    }

    public String redirectIfNotAuth(String page) {
        if (accessToken.isEmpty())
            return "redirect:/sign_in";
        return page;
    }

    public String redirectIfNotAdmin(String page) {
        if (accessToken.isEmpty())
            return "redirect:/sign_in";
        if (Objects.equals(user.getRole(), "USER"))
            return "redirect:/my_profile";
        return page;
    }

}
