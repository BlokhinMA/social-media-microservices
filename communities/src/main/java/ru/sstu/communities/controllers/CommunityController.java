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

    /*@GetMapping("/my_communities")
    public List<Community> myCommunities(Principal principal) {
        return communityService.showAll(principal);
    }*/

    @PostMapping("/add_community")
    public ResponseEntity<?> create(@RequestBody Community community) {
        return ResponseEntity.ok(communityService.create(community));
    }

    /*@DeleteMapping("/delete_community")
    public ResponseEntity<?> delete(int id) {
        return ResponseEntity.ok(communityService.delete(id));
    }

    @GetMapping("/communities/{memberLogin}")
    public List<Community> showAll(@PathVariable String memberLogin) {
        return communityService.showAll(memberLogin);
    }*/

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable int id) {
        return ResponseEntity.ok(communityService.show(id));
    }

    /*@PostMapping("/join_community")
    public ResponseEntity<?> join(String principal, int communityId) {
        return ResponseEntity.ok(communityService.join(principal, communityId));
    }

    @DeleteMapping("/leave_community")
    public ResponseEntity<?> leave(Principal principal, CommunityMember communityMember) {
        return ResponseEntity.ok(communityService.leave(principal, communityMember));
    }

    @DeleteMapping("/kick_community_member")
    public ResponseEntity<?> kickCommunityMember(CommunityMember communityMember, Principal principal) {
        return ResponseEntity.ok(communityService.kickCommunityMember(communityMember, principal));
    }

    @PostMapping("/add_community_post")
    public ResponseEntity<?> createPost(CommunityPost communityPost, Principal principal) {
        return ResponseEntity.ok(communityService.createPost(communityPost, principal));
    }

    @DeleteMapping("/delete_community_post")
    public ResponseEntity<?> deletePost(CommunityPost communityPost, Principal principal) {
        return ResponseEntity.ok(communityService.deletePost(communityPost, principal));
    }

    @GetMapping("/find_communities")
    public List<Community> find(String keyword) {
        return communityService.find(keyword);
    }

    @GetMapping("/all")
    public List<Community> getAll() {
        return communityService.getAll();
    }

    @GetMapping("/all_members")
    public List<CommunityMember> getAllMembers() {
        return communityService.getAllMembers();
    }

    @GetMapping("/all_posts")
    public List<CommunityPost> getAllPosts() {
        return communityService.getAllPosts();
    }*/

}
