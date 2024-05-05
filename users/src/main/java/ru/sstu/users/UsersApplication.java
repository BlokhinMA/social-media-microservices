package ru.sstu.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import ru.sstu.users.models.User;

@SpringBootApplication
public class UsersApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsersApplication.class, args);
	}

	/*@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}*/

	@Bean
	public User login() {
		return new User();
	}

	@Bean
	public String accessToken() {
		return "";
	}

	@Bean
	public String refreshToken() {
		return "";
	}

}
