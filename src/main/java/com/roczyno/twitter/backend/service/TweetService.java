package com.roczyno.twitter.backend.service;

import com.roczyno.twitter.backend.exception.TweetException;
import com.roczyno.twitter.backend.exception.UserException;
import com.roczyno.twitter.backend.model.Tweet;
import com.roczyno.twitter.backend.model.User;
import com.roczyno.twitter.backend.request.TweetReplyRequest;
import org.springframework.stereotype.Service;

import java.util.List;


public interface TweetService {
    public Tweet createTweet(Tweet req, User user) throws UserException;
    public List<Tweet> findAllTweets();
    public Tweet retweet(Long tweetId, User user) throws UserException, TweetException;
    public Tweet findTweetById(Long tweetId) throws TweetException;
    public void deleteTweetById(Long tweetId,Long userId) throws TweetException,UserException;

    public Tweet removeFromRetweet(Long tweetId, User user) throws TweetException,UserException;
    public Tweet createdReply(TweetReplyRequest req, User user) throws TweetException,UserException;
    public List<Tweet> getUserTweet(User user);
    public List<Tweet> findByLikesContainingUser(User user);

}
