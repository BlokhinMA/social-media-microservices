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

import java.util.List;

@Service
@AllArgsConstructor
public class CommunityService {

    private final CommunityRepository communityRepository;
    private final CommunityMemberRepository communityMemberRepository;
    private final CommunityPostRepository communityPostRepository;
    private final RestTemplate restTemplate;
    private final String url = "http://localhost:8765/logging?for_audit=";

    public List<Community> showAllOwn(String userLogin) {
        List<Community> communities = communityRepository.findAllByCreatorLogin(userLogin);
        restTemplate.postForObject(url + "false", "Пользователь " + userLogin + " обратился к списку своих сообществ: " + communities, String.class);
        return communities;
    }

    public List<Community> showAll(String memberLogin) {
        List<Community> communities = communityRepository.findAllByMemberLogin(memberLogin);
        restTemplate.postForObject(url + "false", "Пользователь " + memberLogin + " обратился к списку сообществ: " + communities, String.class);
        return communities;
    }

    public Community create(Community community) {
        Community createdCommunity = communityRepository.save(community);
        restTemplate.postForObject(url + "true", "Пользователь " + createdCommunity.getCreatorLogin() + " создал сообщество: " + createdCommunity, String.class);
        return createdCommunity;
    }

    public Community delete(int id) {
        List<CommunityMember> members = communityMemberRepository.findAllByCommunityId(id);
        List<CommunityPost> posts = communityPostRepository.findAllByCommunityId(id);
        Community deletedCommunity = communityRepository.deleteById(id);
        deletedCommunity.setMembers(members);
        deletedCommunity.setPosts(posts);
        restTemplate.postForObject(url + "true", "Пользователь " + deletedCommunity.getCreatorLogin() + " удалил сообщество: " + deletedCommunity, String.class);
        return deletedCommunity;
    }

    public Community show(int id, String login) {
        Community community = communityRepository.findById(id);
        community.setMembers(communityMemberRepository.findAllByCommunityId(id));
        community.setPosts(communityPostRepository.findAllByCommunityId(id));
        restTemplate.postForObject(url + "false", "Пользователь " + login + " обратился к сообществу: " + community, String.class);
        return community;
    }

    public CommunityMember join(CommunityMember communityMember) {
        if (communityRepository.findById(communityMember.getCommunityId()) == null)
            return null;
        CommunityMember joinedCommunityMember = communityMemberRepository.save(communityMember);
        restTemplate.postForObject(url + "true", "Пользователь " + joinedCommunityMember.getMemberLogin() + " присоединился к сообществу: " + joinedCommunityMember, String.class);
        return joinedCommunityMember;
    }

    /*public boolean isMember(Principal principal, int communityId) {
        return communityMemberRepository.findByMemberLoginAndCommunityId(principal.getName(), communityId) != null;
    }*/

    public CommunityMember leave(CommunityMember communityMember) {
        if (communityRepository.findById(communityMember.getCommunityId()) == null)
            return null;
        CommunityMember leftCommunityMember = communityMemberRepository.deleteByMemberLoginAndCommunityId(communityMember);
        restTemplate.postForObject(url + "true", "Пользователь " + leftCommunityMember.getMemberLogin() + " покинул сообщество: " + leftCommunityMember, String.class);
        return leftCommunityMember;
    }

    public CommunityMember kickCommunityMember(CommunityMember communityMember) {
        if (communityRepository.findById(communityMember.getCommunityId()) == null)
            return null;
        CommunityMember kickedCommunityMember = communityMemberRepository.deleteById(communityMember.getId());
        restTemplate.postForObject(url + "true", "Пользователь " + communityRepository.findById(kickedCommunityMember.getCommunityId()).getCreatorLogin() + " выгнал из сообщества: " + kickedCommunityMember, String.class);
        return kickedCommunityMember;
    }

    public CommunityPost createPost(CommunityPost communityPost) {
        if (communityRepository.findById(communityPost.getCommunityId()) == null)
            return null;
        CommunityPost createdCommunityPost = communityPostRepository.save(communityPost);
        restTemplate.postForObject(url + "true", "Пользователь " + createdCommunityPost.getAuthorLogin() + " написал пост: " + createdCommunityPost, String.class);
        return createdCommunityPost;
    }

    public CommunityPost deletePost(CommunityPost communityPost) {
        if (communityRepository.findById(communityPost.getCommunityId()) == null)
            return null;
        CommunityPost deletedCommunityPost = communityPostRepository.deleteById(communityPost.getId());
        restTemplate.postForObject(url + "true", "Пользователь " + deletedCommunityPost.getAuthorLogin() + " удалил пост: " + deletedCommunityPost, String.class);
        return deletedCommunityPost;
    }

    public List<Community> find(String keyword, String login) {
        if (keyword != null && !keyword.isEmpty()) {
            List<Community> communities = communityRepository.findAllLikeName(keyword);
            restTemplate.postForObject(url + "false", "Пользователь " + login + " выполнил поиск сообществ: " + communities, String.class);
            return communities;
        }
        return null;
    }

    public List<Community> getAll(String login) {
        List<Community> communities = communityRepository.findAll();
        restTemplate.postForObject(url + "false", "Админ " + login + " обратился к списку всех сообществ: " + communities, String.class);
        return communities;
    }

    public List<CommunityMember> getAllMembers(String login) {
        List<CommunityMember> communityMembers = communityMemberRepository.findAll();
        restTemplate.postForObject(url + "false", "Админ " + login + " обратился к списку всех участников сообществ: " + communityMembers, String.class);
        return communityMembers;
    }

    public List<CommunityPost> getAllPosts(String login) {
        List<CommunityPost> communityPosts = communityPostRepository.findAll();
        restTemplate.postForObject(url + "false", "Админ " + login + " обратился к списку всех постов сообществ: " + communityPosts, String.class);
        return communityPosts;
    }

}
