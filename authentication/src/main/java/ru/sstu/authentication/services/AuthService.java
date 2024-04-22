package ru.sstu.authentication.services;

import lombok.AllArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.sstu.authentication.models.AuthRequest;
import ru.sstu.authentication.models.AuthResponse;
import ru.sstu.authentication.models.User;

@Service
@AllArgsConstructor
public class AuthService {

    private final RestTemplate restTemplate;
    private final JwtUtil jwtUtil;

    public AuthResponse register(AuthRequest request) {
        request.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        User registeredUser = restTemplate.postForObject("http://users/users/sign_up", request, User.class);

        assert registeredUser != null;
        String accessToken = jwtUtil.generate(String.valueOf(registeredUser.getId()), registeredUser.getRole(), "ACCESS");
        String refreshToken = jwtUtil.generate(String.valueOf(registeredUser.getId()), registeredUser.getRole(), "REFRESH");

        return new AuthResponse(accessToken, refreshToken);
    }

}
