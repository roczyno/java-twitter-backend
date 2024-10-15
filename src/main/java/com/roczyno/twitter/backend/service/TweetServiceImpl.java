package com.roczyno.twitter.backend.service;

import com.roczyno.twitter.backend.dto.TweetDto;
import com.roczyno.twitter.backend.dto.TweetDtoMapper;
import com.roczyno.twitter.backend.exception.TweetException;
import com.roczyno.twitter.backend.exception.UserException;
import com.roczyno.twitter.backend.model.Tweet;
import com.roczyno.twitter.backend.model.User;
import com.roczyno.twitter.backend.repository.TweetRepository;
import com.roczyno.twitter.backend.request.TweetReplyRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService{
    private final TweetRepository tweetRepository;
    private final TweetDtoMapper tweetDtoMapper;

    @Override
    public TweetDto createTweet(Tweet req, User user) {
        Tweet tweet = new Tweet();
        tweet.setContent(req.getContent());
        tweet.setCreatedAt(LocalDateTime.now());
        tweet.setImage(req.getImage());
        tweet.setVideo(req.getVideo());
        tweet.setUser(user);
        tweet.setReply(false);
        tweet.setTweet(true);
        Tweet savedTweet= tweetRepository.save(tweet);
		return tweetDtoMapper.toTweetDto(savedTweet,user);
    }

    @Override
    public List<TweetDto> findAllTweets(User user) {
        List<Tweet> tweets= tweetRepository.findAll();
        return tweetDtoMapper.toTweetDtos(tweets,user);
    }

    @Override
    public TweetDto retweet(Long tweetId, User user){
        Tweet tweet=tweetRepository.findById(tweetId)
                .orElseThrow(()-> new TweetException("Tweet with "+ tweetId+" not found"));
        if(tweet.getRetweetUser().contains(user)){
            tweet.getRetweetUser().remove(user);
        }else {
            tweet.getRetweetUser().add(user);
        }
        Tweet savedRetweet=tweetRepository.save(tweet);
        return tweetDtoMapper.toTweetDto(savedRetweet,user);
    }

    @Override
    public TweetDto findTweetById(Long tweetId,User user){
        Tweet tweet= tweetRepository.findById(tweetId)
                .orElseThrow(()-> new TweetException("Tweet with "+ tweetId+" not found"));
        return tweetDtoMapper.toTweetDto(tweet,user);
    }

    @Override
    public String deleteTweetById(Long tweetId, Long userId) throws TweetException, UserException {
        Tweet tweet=tweetRepository.findById(tweetId)
                .orElseThrow(()-> new TweetException("Tweet with "+ tweetId+" not found"));
        if(!userId.equals(tweet.getUser().getId())){
            throw new UserException("You can only delete your tweet");
        }
        tweetRepository.deleteById(tweetId);
        return "tweet deleted";
    }

    @Override
    public Tweet removeFromRetweet(Long tweetId, User user) {
        return null;
    }


    @Override
    public TweetDto createdReply(TweetReplyRequest req, User user) {
        Tweet replyFor = tweetRepository.findById(req.getTweetId())
                .orElseThrow(()-> new TweetException("Tweet with "+ req.getTweetId()+" not found"));
        Tweet tweet = new Tweet();
        tweet.setContent(req.getContent());
        tweet.setCreatedAt(LocalDateTime.now());
        tweet.setImage(req.getImage());
        tweet.setUser(user);
        tweet.setReply(true);
        tweet.setTweet(false);
        tweet.setReplyFor(replyFor);


        Tweet savedReply = tweetRepository.save(tweet);

        replyFor.getReplyTweets().add(savedReply);
        Tweet  newSavedReply=tweetRepository.save(replyFor);
        return tweetDtoMapper.toTweetDto(newSavedReply,user);

    }


    @Override
    public List<TweetDto> getUserTweet(User user) {
        List<Tweet> tweets= tweetRepository
                .findByUserContainsAndTweetTrueOrderByCreatedAtDesc(user);
        return tweetDtoMapper.toTweetDtos(tweets,user);
    }

    @Override
    public List<TweetDto> findByLikesContainingUser(User user) {
        List<Tweet> tweets=tweetRepository.findByLikesUser_id(user.getId());
        return tweetDtoMapper.toTweetDtos(tweets,user);
    }
}
