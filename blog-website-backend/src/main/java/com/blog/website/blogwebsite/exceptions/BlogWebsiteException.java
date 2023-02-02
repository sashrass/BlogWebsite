package com.blog.website.blogwebsite.exceptions;

public class BlogWebsiteException extends RuntimeException {
    public BlogWebsiteException(String exMessage, Exception exception) {
        super(exMessage, exception);
    }

    public BlogWebsiteException(String exMessage) {
        super(exMessage);
    }
}
