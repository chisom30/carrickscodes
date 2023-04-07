package com.chisom.redditApplication.repository;

import com.chisom.redditApplication.model.Comment;
import com.chisom.redditApplication.model.Post;
import com.chisom.redditApplication.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);

    List<Comment> findAllByUsername(Users users);
}
