package com.chisom.redditApplication.mapper;

import com.chisom.redditApplication.dto.PostRequest;
import com.chisom.redditApplication.dto.PostResponse;
import com.chisom.redditApplication.model.Post;
import com.chisom.redditApplication.model.Subreddit;
import com.chisom.redditApplication.model.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "subreddit", source = "subreddit")
    @Mapping(target = "users", source = "users")
    @Mapping(target = "description", source = "postRequest.description")
    Post map(PostRequest postRequest, Subreddit subreddit, Users users);

    @Mapping(target = "id", source = "postId")
    @Mapping(target = "postName", source = "postName")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "url", source = "url")
    @Mapping(target = "subredditName", source = "subreddit.name")
    @Mapping(target = "userName", source = "users.username")
    PostResponse mapToDto(Post post);
}
