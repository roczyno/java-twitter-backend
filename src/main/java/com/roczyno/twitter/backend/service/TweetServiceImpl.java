package com.roczyno.twitter.backend.service;

import com.roczyno.twitter.backend.exception.TweetException;
import com.roczyno.twitter.backend.exception.UserException;
import com.roczyno.twitter.backend.model.Tweet;
import com.roczyno.twitter.backend.model.User;
import com.roczyno.twitter.backend.repository.TweetRepository;
import com.roczyno.twitter.backend.request.TweetReplyRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TweetServiceImpl implements TweetService{
    @Autowired
    private TweetRepository tweetRepository;
    @Override
    public Tweet createTweet(Tweet req, User user) throws UserException {
        Tweet tweet = new Tweet();
        tweet.setContent(req.getContent());
        tweet.setCreatedAt(LocalDateTime.now());
        tweet.setImage(req.getImage());
        tweet.setVideo(req.getVideo());
        tweet.setUser(user);
        tweet.setReply(false);
        tweet.setTweet(true);
        return tweetRepository.save(tweet);
    }

    @Override
    public List<Tweet> findAllTweets() {

        return tweetRepository.findAllByIsTweetTrueOrderByCreatedAtDesc();
    }

    @Override
    public Tweet retweet(Long tweetId, User user) throws UserException, TweetException {
        Tweet tweet= findTweetById(tweetId);
        if(tweet.getReTweetUser().contains(user)){
            tweet.getReTweetUser().remove(user);
        }else {
            tweet.getReTweetUser().add(user);
        }
        return tweetRepository.save(tweet);
    }

    @Override
    public Tweet findTweetById(Long tweetId) throws TweetException {
        return null;
    }

    @Override
    public void deleteTweetById(Long tweetId, Long userId) throws TweetException, UserException {

    }

    @Override
    public Tweet removeFromRetweet(Long tweetId, User user) throws TweetException, UserException {
        return null;
    }

    @Override
    public Tweet createdReply(TweetReplyRequest req, User user) throws TweetException, UserException {
        return null;
    }

    @Override
    public List<Tweet> getUserTweet(User user) {
        return null;
    }

    @Override
    public List<Tweet> findByLikesContainingUser(User user) {
        return null;
    }
}
