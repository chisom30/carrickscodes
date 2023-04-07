package com.chisom.redditApplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private Long postId;
    private String subredditName;
    private String postName;
    private String url;
    private String description;
    //new fields
    private Integer voteCount;
    private Integer commentCount;
    private String duration;
}
