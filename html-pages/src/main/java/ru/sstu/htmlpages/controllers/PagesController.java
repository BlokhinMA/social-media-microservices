package ru.sstu.htmlpages.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//@RequestMapping("/html-pages")
public class PagesController {

    /*@GetMapping("/admin_users/**")
    public String adminUsers() {
        return "adminUsers";
    }*/

    @GetMapping("/album/*")
    public String album() {
        return "album";
    }

    @GetMapping("/albums/*")
    public String albums() {
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
