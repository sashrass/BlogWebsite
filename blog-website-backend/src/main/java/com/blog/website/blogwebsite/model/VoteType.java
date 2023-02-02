package com.blog.website.blogwebsite.model;

import com.blog.website.blogwebsite.exceptions.BlogWebsiteException;

import java.util.Arrays;

public enum VoteType {
    UPVOTE(1), DOWNVOTE(-1),
    ;

    private int direction;

    VoteType(int direction) {
    }

    public static VoteType lookup(Integer direction) {
        return Arrays.stream(VoteType.values())
                .filter(value -> value.getDirection().equals(direction))
                .findAny()
                .orElseThrow(() -> new BlogWebsiteException("Vote not found"));
    }

    public Integer getDirection() {
        return direction;
    }
}