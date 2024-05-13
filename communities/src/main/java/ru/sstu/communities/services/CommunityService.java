package ru.sstu.communities.services;

import lombok.AllArgsConstructor;
import org.springframework.web.client.RestTemplate;
import ru.sstu.communities.models.Community;
import ru.sstu.communities.models.CommunityMember;
import ru.sstu.communities.models.CommunityPost;
import ru.sstu.communities.repositories.CommunityPostRepository;
import ru.sstu.communities.repositories.CommunityRepository;
import ru.sstu.communities.repositories.CommunityMemberRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@AllArgsConstructor
public class CommunityService {

    private final CommunityRepository communityRepository;
    private final CommunityMemberRepository communityMemberRepository;
    private final CommunityPostRepository communityPostRepository;
    private final RestTemplate restTemplate;

    public List<Community> showAllOwn(String userLogin) {
        List<Community> communities = communityRepository.findAllByCreatorLogin(userLogin);
        restTemplate.postForObject("http://localhost:8765/logging", "Пользователь " + userLogin + " обратился к списку своих сообществ: " + communities, String.class);
        return communities;
    }

    public List<Community> showAll(String memberLogin) {
        return communityRepository.findAllByMemberLogin(memberLogin);
    }

    public Community create(Community community) {
        //community.setCreatorLogin(principal);
        Community createdCommunity = communityRepository.save(community);
        return createdCommunity;
    }

    public Community delete(int id) {
        List<CommunityMember> members = communityMemberRepository.findAllByCommunityId(id);
        List<CommunityPost> posts = communityPostRepository.findAllByCommunityId(id);
        Community deletedCommunity = communityRepository.deleteById(id);
        deletedCommunity.setMembers(members);
        deletedCommunity.setPosts(posts);

        return deletedCommunity;
    }

    public Community show(int id) {
        Community community = communityRepository.findById(id);
        community.setMembers(communityMemberRepository.findAllByCommunityId(id));
        community.setPosts(communityPostRepository.findAllByCommunityId(id));
        return community;
    }

    public CommunityMember join(Principal principal, int communityId) {
        if (communityRepository.findById(communityId) == null)
            return null;
        CommunityMember communityMember = new CommunityMember();
        communityMember.setMemberLogin(principal.getName());
        communityMember.setCommunityId(communityId);
        CommunityMember joinedCommunityMember = communityMemberRepository.save(communityMember);
        return joinedCommunityMember;
    }

    /*public boolean isMember(Principal principal, int communityId) {
        return communityMemberRepository.findByMemberLoginAndCommunityId(principal.getName(), communityId) != null;
    }*/

    public CommunityMember leave(Principal principal, CommunityMember communityMember) {
        if (communityRepository.findById(communityMember.getCommunityId()) == null)
            return null;
        communityMember.setMemberLogin(principal.getName());
        CommunityMember leftCommunityMember = communityMemberRepository.deleteByMemberLoginAndCommunityId(communityMember);
        return leftCommunityMember;
    }

    public CommunityMember kickCommunityMember(CommunityMember communityMember, Principal principal) {
        if (communityRepository.findById(communityMember.getCommunityId()) == null)
            return null;
        CommunityMember kickedCommunityMember = communityMemberRepository.deleteById(communityMember.getId());
        return kickedCommunityMember;
    }

    public CommunityPost createPost(CommunityPost communityPost, Principal principal) {
        if (communityRepository.findById(communityPost.getCommunityId()) == null)
            return null;
        communityPost.setAuthorLogin(principal.getName());
        CommunityPost createdPost = communityPostRepository.save(communityPost);
        return createdPost;
    }

    public CommunityPost deletePost(CommunityPost communityPost, Principal principal) {
        if (communityRepository.findById(communityPost.getCommunityId()) == null)
            return null;
        CommunityPost deletedCommunityPost = communityPostRepository.deleteById(communityPost.getId());
        return deletedCommunityPost;
    }

    public List<Community> find(String keyword) {
        if (keyword != null && !keyword.isEmpty())
            return communityRepository.findAllLikeName(keyword);
        return null;
    }

    public List<Community> getAll() {
        return communityRepository.findAll();
    }

    public List<CommunityMember> getAllMembers() {
        return communityMemberRepository.findAll();
    }

    public List<CommunityPost> getAllPosts() {
        return communityPostRepository.findAll();
    }

}
