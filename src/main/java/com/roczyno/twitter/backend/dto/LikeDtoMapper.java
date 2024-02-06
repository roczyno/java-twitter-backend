package com.roczyno.twitter.backend.dto;

import com.roczyno.twitter.backend.model.Like;
import com.roczyno.twitter.backend.model.User;

public class LikeDtoMapper {
    public static LikeDto toLikeDto(Like like, User reqUser){
        UserDto user =UserDtoMapper.toUserDto(like.getUser());
        UserDto reqUserDto= UserDtoMapper.toUserDto(reqUser);
        TweetDto tweet= TweetDtoMapper.toTweetDto(like.getTweet(),reqUser);

        LikeDto likeDto= new LikeDto();
        likeDto.setId(like.getId());
        likeDto.setTweet(tweet);
        likeDto.setUser(user);
        return likeDto;

    }
}
