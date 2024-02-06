package com.roczyno.twitter.backend.dto;

import com.roczyno.twitter.backend.model.Like;
import com.roczyno.twitter.backend.model.User;

import java.util.ArrayList;
import java.util.List;

public class LikeDtoMapper {
    public static LikeDto toLikeDto(Like like, User reqUser) {
        // Convert the user associated with the like to a UserDto
        UserDto user = UserDtoMapper.toUserDto(like.getUser());

        // Convert the requesting user (reqUser) to a UserDto
        UserDto reqUserDto = UserDtoMapper.toUserDto(reqUser);

        // Convert the tweet associated with the like to a TweetDto
        TweetDto tweet = TweetDtoMapper.toTweetDto(like.getTweet(), reqUser);

        // Create a new LikeDto object and populate its properties
        LikeDto likeDto = new LikeDto();
        likeDto.setId(like.getId());
        likeDto.setTweet(tweet);
        likeDto.setUser(user);

        // Return the populated LikeDto object
        return likeDto;
    }

    public static List<LikeDto> toLikeDtos(List<Like> likes,User reqUser){
        List<LikeDto> likeDtos= new ArrayList<>();

        for (Like like:likes){

            UserDto user = UserDtoMapper.toUserDto(like.getUser());

            TweetDto tweet = TweetDtoMapper.toTweetDto(like.getTweet(), reqUser);

            LikeDto likeDto = new LikeDto();
            likeDto.setId(like.getId());
            likeDto.setTweet(tweet);
            likeDto.setUser(user);
            likeDtos.add(likeDto);
        }
        return likeDtos;

    }
}
