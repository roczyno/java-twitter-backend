package com.roczyno.twitter.backend.dto;

import com.roczyno.twitter.backend.model.Tweet;
import com.roczyno.twitter.backend.model.User;
import com.roczyno.twitter.backend.utils.TwitUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TweetDtoMapper {
    private final UserDtoMapper userDtoMapper;

    public  TweetDto toTweetDto(Tweet tweet, User reqUser) {
        UserDto user = userDtoMapper.toUserDto(tweet.getUser());
        boolean isLiked = TwitUtil.isLikedByReqUser(reqUser, tweet);
        boolean isRetweeted = TwitUtil.isRetweetedByReqUser(reqUser, tweet);
        List<Long> retweetUserIds = new ArrayList<>();
        for (User user1 : tweet.getRetweetUser()) {
            retweetUserIds.add(user1.getId());
        }

        TweetDto tweetDto = new TweetDto();
        tweetDto.setId(tweet.getId());
        tweetDto.setContent(tweet.getContent());
        tweetDto.setCreatedAt(tweet.getCreatedAt());
        tweetDto.setImage(tweet.getImage());
        tweetDto.setTotalLikes(tweet.getLikes().size());
        tweetDto.setTotalReplies(tweet.getReplyTweets().size());
        tweetDto.setTotalRetweet(tweet.getRetweetUser().size());
        tweetDto.setUserDto(user);
        tweetDto.setLiked(isLiked);
        tweetDto.setRetweet(isRetweeted);
        tweetDto.setRetweetUsersId(retweetUserIds);
        tweetDto.setReplyTweets(toTweetDtos(tweet.getReplyTweets(), reqUser));
        tweetDto.setVideo(tweet.getVideo());

        return tweetDto;
    }

    public  List<TweetDto> toTweetDtos(List<Tweet> tweets, User reqUser) {
        List<TweetDto> tweetDtos = new ArrayList<>();
        for (Tweet tweet : tweets) {
            TweetDto tweetDto = toReplyTweetDto(tweet, reqUser);
            tweetDtos.add(tweetDto);
        }
        return tweetDtos;
    }

    public  TweetDto toReplyTweetDto(Tweet tweet, User reqUser) {
        UserDto user = userDtoMapper.toUserDto(tweet.getUser());
        boolean isLiked = TwitUtil.isLikedByReqUser(reqUser, tweet);
        boolean isRetweeted = TwitUtil.isRetweetedByReqUser(reqUser, tweet);
        List<Long> retweetUserIds = new ArrayList<>();
        for (User user1 : tweet.getRetweetUser()) {
            retweetUserIds.add(user1.getId());
        }

        TweetDto tweetDto = new TweetDto();
        tweetDto.setId(tweet.getId());
        tweetDto.setContent(tweet.getContent());
        tweetDto.setCreatedAt(tweet.getCreatedAt());
        tweetDto.setImage(tweet.getImage());
        tweetDto.setTotalLikes(tweet.getLikes().size());
        tweetDto.setTotalReplies(tweet.getReplyTweets().size());
        tweetDto.setTotalRetweet(tweet.getRetweetUser().size());
        tweetDto.setUserDto(user);
        tweetDto.setLiked(isLiked);
        tweetDto.setRetweet(isRetweeted);
        tweetDto.setRetweetUsersId(retweetUserIds);
        tweetDto.setVideo(tweet.getVideo());

        return tweetDto;
    }
}
