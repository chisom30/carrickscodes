package com.chisom.redditApplication.service;

import com.chisom.redditApplication.dto.VoteDto;
import com.chisom.redditApplication.exceptions.PostNotFoundException;
import com.chisom.redditApplication.exceptions.SpringRedditException;
import com.chisom.redditApplication.model.Post;
import com.chisom.redditApplication.model.Vote;
import com.chisom.redditApplication.repository.PostRepository;
import com.chisom.redditApplication.repository.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.chisom.redditApplication.model.VoteType.UPVOTE;

@Service
@AllArgsConstructor
public class VoteService {
    private final PostRepository postRepository;
    private final AuthService authService;
    private final VoteRepository voteRepository;

    public void vote(VoteDto voteDto) {
        Post post = postRepository.findById(voteDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException("Post not found with ID - " + voteDto.getPostId()));
        Optional<Vote> voteByPostAndUser = postRepository.findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());
        if (voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVoteType().equals(voteDto.getVoteType())) {
            throw new SpringRedditException("You have already " + voteDto.getVoteType() + "'d for this post");
        }
        if(UPVOTE.equals(voteDto.getVoteType())){
            post.setVoteCount(post.getVoteCount() + 1);
        }else{
            post.setVoteCount(post.getVoteCount() - 1);
        }
        voteRepository.save(mapToVote(voteDto, post));
        postRepository.save(post);
    }

    private Vote mapToVote(VoteDto voteDto, Post post) {
        return Vote.builder()
                .voteType(voteDto.getVoteType())
                .post(post)
                .users(authService.getCurrentUser())
                .build();
    }
}
