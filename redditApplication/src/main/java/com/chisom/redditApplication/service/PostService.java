package com.chisom.redditApplication.service;

import com.chisom.redditApplication.dto.PostRequest;
import com.chisom.redditApplication.dto.PostResponse;
import com.chisom.redditApplication.exceptions.PostNotFoundException;
import com.chisom.redditApplication.exceptions.SubredditNotFoundException;
import com.chisom.redditApplication.exceptions.UserNotFoundException;
import com.chisom.redditApplication.mapper.PostMapper;
import com.chisom.redditApplication.model.Post;
import com.chisom.redditApplication.model.Subreddit;
import com.chisom.redditApplication.model.Users;
import com.chisom.redditApplication.repository.PostRepository;
import com.chisom.redditApplication.repository.SubredditRepository;
import com.chisom.redditApplication.repository.UsersRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {
    private final UsersRepository usersRepository;
    private final PostRepository postRepository;
    private final SubredditRepository subredditRepository;
    private final AuthService authService;
    private PostMapper postMapper;

    public Post save(PostRequest postRequest) {
        Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName())
                .orElseThrow(() -> new SubredditNotFoundException(postRequest.getSubredditName()));
        Users currentUser = authService.getCurrentUser();
        return postMapper.map(postRequest, subreddit, currentUser);
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id.toString()));
        return postMapper.mapToDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsBySureddit(Long subredditId){
        Subreddit subreddit = subredditRepository.findById(subredditId)
                .orElseThrow(()-> new SubredditNotFoundException(subredditId.toString()));
        List<Post> posts = postRepository.findPostBySubreddit(subreddit);
        return posts.stream().map(postMapper::mapToDto).collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByUsername(String username){
        Users users = usersRepository.findByUsername(username)
                .orElseThrow(()-> new UserNotFoundException(username);
        List<Post> posts = postRepository.findByUser(users);
        return posts.stream().map(postMapper::mapToDto).collect(toList());
    }
}
