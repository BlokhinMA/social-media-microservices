package ru.sstu.users.services;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import ru.sstu.users.models.LogRequest;
import ru.sstu.users.models.AuthResponse;
import ru.sstu.users.models.User;
import ru.sstu.users.repositories.UserRepository;

@Service
@AllArgsConstructor
@Log4j2
public class AuthService {

    //private final RestTemplate restTemplate;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    @Getter
    private String accessToken;
    private String refreshToken;
    @Getter
    private User user;

    public AuthResponse register(User request) {
        request.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        //User registeredUser = restTemplate.postForObject("http://users/users/sign_up", request, User.class);
        User registeredUser =  userRepository.save(request);

        assert registeredUser != null;
        String accessToken = jwtUtil.generate(String.valueOf(registeredUser.getId()), registeredUser.getRole(), "ACCESS");
        String refreshToken = jwtUtil.generate(String.valueOf(registeredUser.getId()), registeredUser.getRole(), "REFRESH");

        return new AuthResponse(accessToken, refreshToken);
    }

    public AuthResponse auth(LogRequest request) {
        //User user = restTemplate.getForObject("http://users/users/auth_" + request.getLogin(), User.class);
        User authorizedUser = userRepository.findByLogin(request.getLogin());

        if (authorizedUser != null && BCrypt.checkpw(request.getPassword(), authorizedUser.getPassword())) {
            accessToken = jwtUtil.generate(String.valueOf(authorizedUser.getId()), authorizedUser.getRole(), "ACCESS");
            refreshToken = jwtUtil.generate(String.valueOf(authorizedUser.getId()), authorizedUser.getRole(), "REFRESH");

            user = authorizedUser;

            return new AuthResponse(accessToken, refreshToken);
        } else return null;
    }

    public String redirect(String path) {
        if (user.getLogin() != null)
            return "redirect:/my_profile";
        return path;
    }

}
