package com.chisom.redditApplication.model;

import com.chisom.redditApplication.exceptions.PostNotFoundException;
import com.chisom.redditApplication.exceptions.SpringRedditException;

import java.util.Arrays;

public enum VoteType {
    UPVOTE(1), DOWNVOTE(-1),;
    private int direction;
    VoteType(int direction){
    }

    public static VoteType lookup(Integer direction){
        return Arrays.stream(VoteType.values()).filter(value-> value.getDirection(direction))
                .findAny()
                .orElseThrow(() -> new SpringRedditException("vote not found"));
    }

    private boolean getDirection(Integer direction) {
        return true;
    }
}
