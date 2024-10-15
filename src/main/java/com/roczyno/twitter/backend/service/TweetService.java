package com.roczyno.twitter.backend.service;

import com.roczyno.twitter.backend.dto.TweetDto;
import com.roczyno.twitter.backend.model.Tweet;
import com.roczyno.twitter.backend.model.User;
import com.roczyno.twitter.backend.request.TweetReplyRequest;

import java.util.List;


public interface TweetService {
     TweetDto createTweet(Tweet req, User user) ;
     List<TweetDto> findAllTweets(User user);
     TweetDto retweet(Long tweetId, User user);
     TweetDto findTweetById(Long tweetId,User user);
     String deleteTweetById(Long tweetId,Long userId);
     Tweet removeFromRetweet(Long tweetId, User user);
     TweetDto createdReply(TweetReplyRequest req, User user);
     List<TweetDto> getUserTweet(User user);
     List<TweetDto> findByLikesContainingUser(User user);

}
