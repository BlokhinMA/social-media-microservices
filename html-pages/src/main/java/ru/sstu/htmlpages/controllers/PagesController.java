package ru.sstu.htmlpages.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.sstu.htmlpages.services.fromGatewayService;

import java.util.Objects;

@Controller
@RequiredArgsConstructor
@Log4j2
//@RequestMapping("/html-pages")
public class PagesController {

    //private final fromGatewayService fromGatewayService;

    /*@GetMapping("/admin_users/**")
    public String adminUsers() {
        return "adminUsers";
    }*/

    @GetMapping()
    public String index() {
        return "index";
    }

    @GetMapping("/sign_up")
    public String signUp() {
        return "sign_up";
    }

    @GetMapping("/sign_in")
    public String signIn(Model model) {
        //model.addAttribute("csrf", fromGatewayService.getToken());
        return "sign_in";
    }

    @GetMapping("/my_profile")
    public String myProfile(Model model) {
        //model.addAttribute("name", fromGatewayService.getUsername());
        return "my_profile";
    }

    @GetMapping("/profile/{login}")
    public String profile(@PathVariable String login) {
        /*if (Objects.equals(login, fromGatewayService.getUsername()))
            return "redirect:/my_profile";*/
        return "profile";
    }

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

    @GetMapping("/my_albums")
    public String myAlbums() {
        return "my_albums";
    }

    @GetMapping("/my_communities")
    public String myCommunities() {
        return "my_communities";
    }

    @GetMapping("/photo/*")
    public String photo() {
        return "photo";
    }

}
