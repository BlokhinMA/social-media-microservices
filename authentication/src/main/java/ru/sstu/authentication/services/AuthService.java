package ru.sstu.authentication.services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.sstu.authentication.models.LogRequest;
import ru.sstu.authentication.models.RegRequest;
import ru.sstu.authentication.models.AuthResponse;
import ru.sstu.authentication.models.User;

@Service
@AllArgsConstructor
@Log4j2
public class AuthService {

    private final RestTemplate restTemplate;
    private final JwtUtil jwtUtil;

    public AuthResponse register(RegRequest request) {
        request.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        User registeredUser = restTemplate.postForObject("http://users/users/sign_up", request, User.class);

        assert registeredUser != null;
        String accessToken = jwtUtil.generate(String.valueOf(registeredUser.getId()), registeredUser.getRole(), "ACCESS");
        String refreshToken = jwtUtil.generate(String.valueOf(registeredUser.getId()), registeredUser.getRole(), "REFRESH");

        return new AuthResponse(accessToken, refreshToken);
    }

    public AuthResponse login(LogRequest request) {
        User user = restTemplate.getForObject("http://users/users/auth_" + request.getLogin(), User.class);

        if (user != null && BCrypt.checkpw(request.getPassword(), user.getPassword())) {
            String accessToken = jwtUtil.generate(String.valueOf(user.getId()), user.getRole(), "ACCESS");
            String refreshToken = jwtUtil.generate(String.valueOf(user.getId()), user.getRole(), "REFRESH");

            return new AuthResponse(accessToken, refreshToken);
        } else return null;
    }

}
