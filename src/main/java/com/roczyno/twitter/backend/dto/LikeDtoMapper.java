package com.roczyno.twitter.backend.dto;

import com.roczyno.twitter.backend.model.Like;
import com.roczyno.twitter.backend.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeDtoMapper {
    private final TweetDtoMapper tweetDtoMapper;
    private final UserDtoMapper userDtoMapper;
    public LikeDto toLikeDto(Like like, User reqUser) {
        // Convert the user associated with the like to a UserDto
        UserDto user = userDtoMapper.toUserDto(like.getUser());

        // Convert the requesting user (reqUser) to a UserDto
        UserDto reqUserDto = userDtoMapper.toUserDto(reqUser);

        // Convert the tweet associated with the like to a TweetDto
        TweetDto tweet = tweetDtoMapper.toTweetDto(like.getTweet(), reqUser);

        // Create a new LikeDto object and populate its properties
        LikeDto likeDto = new LikeDto();
        likeDto.setId(like.getId());
        likeDto.setTweet(tweet);
        likeDto.setUser(user);

        // Return the populated LikeDto object
        return likeDto;
    }

    public  List<LikeDto> toLikeDtos(List<Like> likes,User reqUser){
        List<LikeDto> likeDtos= new ArrayList<>();

        for (Like like:likes){

            UserDto user = userDtoMapper.toUserDto(like.getUser());

            TweetDto tweet = tweetDtoMapper.toTweetDto(like.getTweet(), reqUser);

            LikeDto likeDto = new LikeDto();
            likeDto.setId(like.getId());
            likeDto.setTweet(tweet);
            likeDto.setUser(user);
            likeDtos.add(likeDto);
        }
        return likeDtos;

    }
}
