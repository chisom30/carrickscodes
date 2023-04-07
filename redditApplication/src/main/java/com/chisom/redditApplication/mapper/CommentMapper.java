package com.chisom.redditApplication.mapper;

import com.chisom.redditApplication.dto.CommentDto;
import com.chisom.redditApplication.model.Comment;
import com.chisom.redditApplication.model.Post;
import com.chisom.redditApplication.model.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "text", source = "commentDto.text")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "post", source = "post")
    Comment map(CommentDto commentDto, Users users, Post post);

    @Mapping(target = "postId", expression = "java(comment.getPost().getPostId)")
    @Mapping(target = "userName", expression = "java(comment.getUser().getUsername)")
    CommentDto mapToDto(Comment comment);
}
