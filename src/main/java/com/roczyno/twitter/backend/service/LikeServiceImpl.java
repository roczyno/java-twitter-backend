package com.roczyno.twitter.backend.service;

import com.roczyno.twitter.backend.exception.TweetException;
import com.roczyno.twitter.backend.exception.UserException;
import com.roczyno.twitter.backend.model.Like;
import com.roczyno.twitter.backend.model.Tweet;
import com.roczyno.twitter.backend.model.User;
import com.roczyno.twitter.backend.repository.LikeRepository;
import com.roczyno.twitter.backend.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeServiceImpl implements LikeService{
    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private TweetRepository tweetRepository;
    @Autowired
    private TweetService tweetService;
    @Override
    public Like likeTweet(Long tweetId, User user) throws UserException, TweetException {
        Like isLikeExist= likeRepository.isLiked(user.getId(), tweetId);
        if(isLikeExist!=null){
            likeRepository.deleteById(isLikeExist.getId());
            return isLikeExist;
        }

        Tweet tweet= tweetService.findTweetById(tweetId);
        Like like = new Like();
        like.setTweet(tweet);
        like.setUser(user);

        Like savedLike= likeRepository.save(like);
        tweet.getLikes().add(savedLike);
        tweetRepository.save(tweet);

        return savedLike;
    }

    @Override
    public List<Like> getAllLikes(Long tweetId) throws TweetException {
        Tweet tweet= tweetService.findTweetById(tweetId);
        List<Like> likes= likeRepository.findAll();
        return likes;
    }
}
