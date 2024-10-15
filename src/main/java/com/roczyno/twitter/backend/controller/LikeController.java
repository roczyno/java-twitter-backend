package com.roczyno.twitter.backend.controller;

import com.roczyno.twitter.backend.dto.LikeDto;
import com.roczyno.twitter.backend.dto.LikeDtoMapper;
import com.roczyno.twitter.backend.exception.TweetException;
import com.roczyno.twitter.backend.exception.UserException;
import com.roczyno.twitter.backend.model.Like;
import com.roczyno.twitter.backend.model.User;
import com.roczyno.twitter.backend.service.LikeService;
import com.roczyno.twitter.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LikeController {
    private final UserService userService;
    private final LikeService likeService;

    @PostMapping("/{tweetId}/like")
    public ResponseEntity<LikeDto> likeTweet(@PathVariable Long tweetId, @RequestHeader("Authorization") String jwt){
        return ResponseEntity.ok(likeService.likeTweet(tweetId,userService.findUserProfileByJwt(jwt)));
    }
    @GetMapping("/tweet/{tweetId}/likes")
    public ResponseEntity<List<LikeDto>> getAllLikes(@PathVariable Long tweetId, @RequestHeader("Authorization") String jwt){
        return ResponseEntity.ok(likeService.getAllLikes(tweetId,userService.findUserProfileByJwt(jwt)));
    }
}
