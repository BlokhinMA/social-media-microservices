package ru.sstu.users.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.sstu.users.models.User;
import ru.sstu.users.services.AuthService;

@Controller
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {

    private final AuthService authService;

    @ModelAttribute("token")
    public String token() {
        return authService.getAccessToken();
    }

    @ModelAttribute("user")
    public User user() {
        return authService.getUser();
    }



    @GetMapping("/users")
    public String adminUsers() {
        return authService.redirectIfNotAdmin("admin_users");
    }



    @GetMapping("/albums")
    public String adminAlbums() {
        return authService.redirectIfNotAdmin("admin_albums");
    }



    @GetMapping("/photos")
    public String adminPhotos() {
        return authService.redirectIfNotAdmin("admin_photos");
    }

    @GetMapping("/photo_tags")
    public String adminPhotoTags() {
        return authService.redirectIfNotAdmin("admin_photo_tags");
    }

    @GetMapping("/photo_comments")
    public String adminPhotoComments() {
        return authService.redirectIfNotAdmin("admin_photo_comments");
    }



    @GetMapping("/communities")
    public String adminCommunities() {
        return authService.redirectIfNotAdmin("admin_communities");
    }

    @GetMapping("/community_members")
    public String adminCommunityMembers() {
        return authService.redirectIfNotAdmin("admin_community_members");
    }

    @GetMapping("/community_posts")
    public String adminCommunityPosts() {
        return authService.redirectIfNotAdmin("admin_community_posts");
    }

}
