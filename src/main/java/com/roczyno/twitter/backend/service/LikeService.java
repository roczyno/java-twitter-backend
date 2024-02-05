package com.roczyno.twitter.backend.service;

import com.roczyno.twitter.backend.exception.TweetException;
import com.roczyno.twitter.backend.exception.UserException;
import com.roczyno.twitter.backend.model.Like;
import com.roczyno.twitter.backend.model.User;

import java.util.List;

public interface LikeService {
    public Like likeTweet(Long tweetId, User user) throws UserException, TweetException;
    public List<Like> getAllLikes(Long tweetId) throws TweetException;
}
