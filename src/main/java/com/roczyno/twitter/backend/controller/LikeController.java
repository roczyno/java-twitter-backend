package com.roczyno.twitter.backend.controller;

import com.roczyno.twitter.backend.dto.LikeDto;
import com.roczyno.twitter.backend.dto.LikeDtoMapper;
import com.roczyno.twitter.backend.exception.TweetException;
import com.roczyno.twitter.backend.exception.UserException;
import com.roczyno.twitter.backend.model.Like;
import com.roczyno.twitter.backend.model.User;
import com.roczyno.twitter.backend.service.LikeService;
import com.roczyno.twitter.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LikeController {

    @Autowired
    private UserService userService;
    @Autowired
    private LikeService likeService;

    @PostMapping("/{tweetId}/like")
    public ResponseEntity<LikeDto> likeTweet(@PathVariable Long tweetId, @RequestHeader("Authorization") String jwt)
            throws UserException, TweetException {
        User user= userService.findUserProfileByJwt(jwt);
        Like like= likeService.likeTweet(tweetId,user);
        LikeDto likeDto= LikeDtoMapper.toLikeDto(like,user);
        return new ResponseEntity<>(likeDto, HttpStatus.CREATED);
    }
    @GetMapping("/tweet/{tweetId}/likes")
    public ResponseEntity<List<LikeDto>> getAllLikes(@PathVariable Long tweetId, @RequestHeader("Authorization") String jwt)
            throws UserException, TweetException {
        User user= userService.findUserProfileByJwt(jwt);
        List<Like> likes= likeService.getAllLikes(tweetId);
        List<LikeDto> likeDtos= LikeDtoMapper.toLikeDtos(likes,user);
        return new ResponseEntity<>(likeDtos, HttpStatus.OK);
    }



}
