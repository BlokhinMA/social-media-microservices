package ru.sstu.htmlpages.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@Controller
@AllArgsConstructor
@Log4j2
//@RequestMapping("/html-pages")
public class PagesController {

    private final RestTemplate restTemplate;

    /*@GetMapping("/admin_users/**")
    public String adminUsers() {
        return "adminUsers";
    }*/

    @GetMapping("/album/*")
    public String album(Model model) {
        /*String token = restTemplate.getForObject("http://auth/get_token", String.class);
        model.addAttribute("token", token);*/
        return "album";
    }

    @GetMapping("/albums/*")
    public String albums(Model model) {
        /*String token = restTemplate.getForObject("http://auth/get_token", String.class);
        model.addAttribute("token", token);*/
        return "albums";
    }

    @GetMapping("/communities/*")
    public String communities() {
        return "communities";
    }

    @GetMapping("/community/*")
    public String community() {
        return "community";
    }

    @GetMapping("/community_management")
    public String communityManagement() {
        return "community_management";
    }

    @GetMapping("/find_albums")
    public String findAlbums() {
        return "find_albums";
    }

    @GetMapping("/find_communities")
    public String findCommunities() {
        return "find_communities";
    }

    @GetMapping("/find_photos")
    public String findPhotos() {
        return "find_photos";
    }

    @GetMapping()
    public String index() {
        return "index";
    }

    @GetMapping("/my_albums")
    public String myAlbums() {
        return "my_albums";
    }

    @GetMapping("/my_communities")
    public String myCommunities() {
        return "my_communities";
    }

    @GetMapping("/my_profile")
    public String myProfile() {
        return "my_profile";
    }

    @GetMapping("/photo/*")
    public String photo() {
        return "photo";
    }

    @GetMapping("/profile/*")
    public String profile() {
        return "profile";
    }

    @GetMapping("/sign_in")
    public String signIn() {
        return "sign_in";
    }

    @GetMapping("/sign_up")
    public String signUp() {
        return "sign_up";
    }

}
