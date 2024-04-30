package ru.sstu.apigateway.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.sstu.apigateway.models.User;

@Service
@RequiredArgsConstructor
@Log4j2
public class CustomUserDetailsService implements ReactiveUserDetailsService {

    private final WebClient webClient;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        Mono<User> user = webClient.get()
                .uri("/users/auth_{username}", username)
                .retrieve()
                .bodyToMono(User.class);
        //log.error("Пользователь {} зашел в систему", user.toString());
        return user.cast(UserDetails.class);
    }

}
