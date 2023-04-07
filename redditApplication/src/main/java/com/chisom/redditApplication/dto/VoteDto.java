package com.chisom.redditApplication.dto;

import com.chisom.redditApplication.model.VoteType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteDto {
    private Long postId;
    private VoteType voteType;
}
