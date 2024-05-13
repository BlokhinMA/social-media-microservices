package ru.sstu.users.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import ru.sstu.users.models.User;
import ru.sstu.users.services.AuthService;

import java.util.Objects;

@Controller
@AllArgsConstructor
public class PagesController {

    private final AuthService authService;

    @ModelAttribute("token")
    public String token() {
        return authService.getAccessToken();
    }

    @ModelAttribute("user")
    public User user() {
        return authService.getUser();
    }



    @GetMapping()
    public String index() {
        return authService.redirect("index");
    }

    @GetMapping("/sign_up")
    public String signUp() {
        return authService.redirect("sign_up");
    }

    @GetMapping("/sign_in")
    public String signIn() {
        return authService.redirect("sign_in");
    }

    @GetMapping("/my_profile")
    public String myProfile() {
        return authService.redirectIfNotAuth("my_profile");
    }

    @GetMapping("/profile/{login}")
    public String profile(@PathVariable String login) {
        if (user().getLogin() == null)
            return "redirect:/sign_in";
        if (Objects.equals(login, authService.getUser().getLogin()))
            return "redirect:/my_profile";
        return "profile";
    }



    @GetMapping("/my_albums")
    public String myAlbums() {
        return authService.redirectIfNotAuth("my_albums");
    }

    @GetMapping("/album/*")
    public String album() {
        return authService.redirectIfNotAuth("album");
    }

    @GetMapping("/albums/{userLogin}")
    public String albums(@PathVariable String userLogin) {
        if (user().getLogin() == null)
            return "redirect:/sign_in";
        if (Objects.equals(userLogin, authService.getUser().getLogin()))
            return "redirect:/my_albums";
        return "albums";
    }

    @GetMapping("/find_albums")
    public String findAlbums() {
        return authService.redirectIfNotAuth("find_albums");
    }

    @GetMapping("/find_photos")
    public String findPhotos() {
        return authService.redirectIfNotAuth("find_photos");
    }

    @GetMapping("/photo/*")
    public String photo() {
        return authService.redirectIfNotAuth("photo");
    }



    @GetMapping("/community_management")
    public String communityManagement() {
        return authService.redirectIfNotAuth("community_management");
    }

    @GetMapping("/my_communities")
    public String myCommunities() {
        return authService.redirectIfNotAuth("my_communities");
    }

    @GetMapping("/communities/{memberLogin}")
    public String communities(@PathVariable String memberLogin) {
        if (user().getLogin() == null)
            return "redirect:/sign_in";
        if (Objects.equals(memberLogin, authService.getUser().getLogin()))
            return "redirect:/my_communities";
        return "communities";
    }

    @GetMapping("/community/*")
    public String community() {
        return authService.redirectIfNotAuth("community");
    }

    @GetMapping("/find_communities")
    public String findCommunities() {
        return authService.redirectIfNotAuth("find_communities");
    }

}
