package ru.sstu.communities;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

@SpringBootApplication
public class CommunitiesApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommunitiesApplication.class, args);
    }

    @Bean
    //@LoadBalanced
    public RestTemplate restTemplate () {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().forEach(converter -> {
            if (converter instanceof AbstractHttpMessageConverter) {
                ((AbstractHttpMessageConverter<?>) converter).setDefaultCharset(StandardCharsets.UTF_8);
            }
        });
        return restTemplate;
    }

}
