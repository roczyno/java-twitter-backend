package com.roczyno.twitter.backend.service;

import com.roczyno.twitter.backend.dto.LikeDto;
import com.roczyno.twitter.backend.model.User;

import java.util.List;

public interface LikeService {
    LikeDto likeTweet(Long tweetId, User user);
    List<LikeDto> getAllLikes(Long tweetId,User user);
}
