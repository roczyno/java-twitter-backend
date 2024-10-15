package com.roczyno.twitter.backend.service;

import com.roczyno.twitter.backend.dto.LikeDto;
import com.roczyno.twitter.backend.dto.LikeDtoMapper;
import com.roczyno.twitter.backend.model.Like;
import com.roczyno.twitter.backend.model.Tweet;
import com.roczyno.twitter.backend.model.User;
import com.roczyno.twitter.backend.repository.LikeRepository;
import com.roczyno.twitter.backend.repository.TweetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService{
    private final LikeRepository likeRepository;
    private final TweetRepository tweetRepository;
    private final TweetService tweetService;
    private final LikeDtoMapper likeDtoMapper;


    @Override
    public LikeDto likeTweet(Long tweetId, User user) {
        Like isLikeExist= likeRepository.isLiked(user.getId(), tweetId);
        if(isLikeExist!=null){
            likeRepository.deleteById(isLikeExist.getId());
            return likeDtoMapper.toLikeDto(isLikeExist,user);
        }

        Tweet tweet= tweetRepository.findById(tweetId).orElseThrow();
        Like like = new Like();
        like.setTweet(tweet);
        like.setUser(user);

        Like savedLike= likeRepository.save(like);
        tweet.getLikes().add(savedLike);
        tweetRepository.save(tweet);

       Like newSavedLike=likeRepository.save(savedLike);
       return likeDtoMapper.toLikeDto(newSavedLike,user);
    }

    @Override
    public List<LikeDto> getAllLikes(Long tweetId,User user) {
        Tweet tweet= tweetRepository.findById(tweetId).orElseThrow();
        List<Like> likes= likeRepository.findByTweetId(tweet.getId());
        return likeDtoMapper.toLikeDtos(likes,user);
    }
}
