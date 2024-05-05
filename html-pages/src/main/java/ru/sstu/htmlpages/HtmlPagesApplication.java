package ru.sstu.htmlpages;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class HtmlPagesApplication {

    public static void main(String[] args) {
        SpringApplication.run(HtmlPagesApplication.class, args);
    }

    /*@Bean
    @LoadBalanced
    public WebClient webClient() {
        return WebClient.builder().baseUrl("http://localhost:8765").build();
    }*/

}
