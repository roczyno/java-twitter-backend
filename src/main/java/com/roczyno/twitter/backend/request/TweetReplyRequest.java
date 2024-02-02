package com.roczyno.twitter.backend.request;

import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Data
public class TweetReplyRequest {
    private String content;
    private String image;
    private Long tweetId;
    private LocalDateTime createdAt;
}
