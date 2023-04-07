package com.chisom.redditApplication.repository;

import com.chisom.redditApplication.model.Post;
import com.chisom.redditApplication.model.Subreddit;
import com.chisom.redditApplication.model.Users;
import com.chisom.redditApplication.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findPostBySubreddit(Subreddit subreddit);
    List<Post> findByUser(Users users);

    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, Users currentUser);
}
