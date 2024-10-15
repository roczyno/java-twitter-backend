package com.roczyno.twitter.backend.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
@Data
public class TweetDto {
    private Long id;
    private String content;
    private String image;
    private String video;
    private LocalDateTime createdAt;
    private int totalLikes;
    private int totalReplies;
    private int totalRetweet;
    private UserDto userDto;
    private boolean isLiked;
    private boolean isRetweet;
    private List<Long> retweetUsersId;
    private List<TweetDto> replyTweets;

}
