package ru.sstu.apigateway;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

import java.security.Key;

@SpringBootApplication
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);

		/*String secretKey = "MiAVzqUXy5Tfr1kVIGpPMiAVzqUXy5Tfr1kVIGpP"; // Здесь необходимо указать секретный ключ, используемый для подписи токена
		String token = ""; // Здесь необходимо поместить JWT-токен, который нужно проверить

		try {
			Key key = Keys.hmacShaKeyFor(secretKey.getBytes());

			//Jws<Claims> claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
			Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
			System.out.println("Токен верифицирован успешно: " + claims.getBody());
			// Доступ к данным токена доступен через метод getBody()
			// Здесь можно продолжить выполнение кода
		} catch (Exception e) {
			System.out.println("Ошибка верификации токена: " + e.getMessage());
			// Обработка ошибки верификации
		}*/

	}

	/*@Bean
	@LoadBalanced
	public WebClient webClient() {
		return WebClient.builder().baseUrl("http://localhost:9001").build();
	}

	@Bean
	public CsrfToken csrfToken() {
		return new CsrfToken() {
			@Override
			public String getHeaderName() {
				return "";
			}

			@Override
			public String getParameterName() {
				return "";
			}

			@Override
			public String getToken() {
				return "";
			}
		};
	}*/

}
