package ru.sstu.communities.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sstu.communities.models.Community;
import ru.sstu.communities.models.CommunityMember;
import ru.sstu.communities.models.CommunityPost;
import ru.sstu.communities.services.CommunityService;

import java.util.List;

@RestController
@RequestMapping("/communities")
@AllArgsConstructor
public class CommunityController {

    private final CommunityService communityService;

    @GetMapping("/community_management/{userLogin}")
    public List<Community> showAllOwn(@PathVariable String userLogin) {
        return communityService.showAllOwn(userLogin);
    }

    @GetMapping("/my_communities")
    public List<Community> myCommunities(@RequestParam("member_login") String memberLogin) {
        return communityService.showAll(memberLogin);
    }

    @PostMapping("/add_community")
    public ResponseEntity<?> create(@RequestBody Community community) {
        return ResponseEntity.ok(communityService.create(community));
    }

    @DeleteMapping("/delete_community")
    public ResponseEntity<?> delete(@RequestParam int id) {
        return ResponseEntity.ok(communityService.delete(id));
    }

    @GetMapping("/communities/{memberLogin}")
    public List<Community> showAll(@PathVariable String memberLogin) {
        return communityService.showAll(memberLogin);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable int id, @RequestParam String login) {
        return ResponseEntity.ok(communityService.show(id, login));
    }

    @PostMapping("/join_community")
    public ResponseEntity<?> join(@RequestBody CommunityMember communityMember) {
        return ResponseEntity.ok(communityService.join(communityMember));
    }

    @DeleteMapping("/leave_community")
    public ResponseEntity<?> leave(@RequestBody CommunityMember communityMember) {
        return ResponseEntity.ok(communityService.leave(communityMember));
    }

    @DeleteMapping("/kick_community_member")
    public ResponseEntity<?> kickCommunityMember(@RequestBody CommunityMember communityMember) {
        return ResponseEntity.ok(communityService.kickCommunityMember(communityMember));
    }

    @PostMapping("/add_community_post")
    public ResponseEntity<?> createPost(@RequestBody CommunityPost communityPost) {
        return ResponseEntity.ok(communityService.createPost(communityPost));
    }

    @DeleteMapping("/delete_community_post")
    public ResponseEntity<?> deletePost(@RequestBody CommunityPost communityPost) {
        return ResponseEntity.ok(communityService.deletePost(communityPost));
    }

    @GetMapping("/find_communities")
    public List<Community> find(@RequestParam String keyword, @RequestParam String login) {
        return communityService.find(keyword, login);
    }

    @GetMapping("/all")
    public List<Community> getAll(@RequestParam String login) {
        return communityService.getAll(login);
    }

    @GetMapping("/all_community_members")
    public List<CommunityMember> getAllMembers(@RequestParam String login) {
        return communityService.getAllMembers(login);
    }

    @GetMapping("/all_community_posts")
    public List<CommunityPost> getAllPosts(@RequestParam String login) {
        return communityService.getAllPosts(login);
    }

}
