package com.roczyno.twitter.backend.controller;

import com.roczyno.twitter.backend.dto.TweetDto;
import com.roczyno.twitter.backend.dto.TweetDtoMapper;
import com.roczyno.twitter.backend.exception.TweetException;
import com.roczyno.twitter.backend.exception.UserException;
import com.roczyno.twitter.backend.model.Tweet;
import com.roczyno.twitter.backend.model.User;
import com.roczyno.twitter.backend.request.TweetReplyRequest;
import com.roczyno.twitter.backend.response.ApiResponse;
import com.roczyno.twitter.backend.service.TweetService;
import com.roczyno.twitter.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tweet")
@RequiredArgsConstructor
public class TweetController {
    private final TweetService tweetService;
    private final UserService userService;


    @PostMapping
    public ResponseEntity<TweetDto> createdTweet(@RequestBody Tweet req, @RequestHeader("Authorization") String jwt) {
       return ResponseEntity.ok(tweetService.createTweet(req,userService.findUserProfileByJwt(jwt)));
    }

    @PostMapping("/reply")
    public ResponseEntity<TweetDto> replyTweet(@RequestBody TweetReplyRequest req, @RequestHeader("Authorization") String jwt) {
      return ResponseEntity.ok(tweetService.createdReply(req,userService.findUserProfileByJwt(jwt)));
    }

    @PutMapping("/{tweetId}/retweet")
    public ResponseEntity<TweetDto> reTweet(@PathVariable Long tweetId, @RequestHeader("Authorization") String jwt){
       return ResponseEntity.ok(tweetService.retweet(tweetId,userService.findUserProfileByJwt(jwt)));
    }

    @GetMapping("/{tweetId}")
    public ResponseEntity<TweetDto> findTweetById(@PathVariable Long tweetId, @RequestHeader("Authorization") String jwt){
        return ResponseEntity.ok(tweetService.findTweetById(tweetId,userService.findUserProfileByJwt(jwt)));
    }

    @DeleteMapping("/{tweetId}")
    public ResponseEntity<String> deleteTweetById(@PathVariable Long tweetId, @RequestHeader("Authorization") String jwt){
       return ResponseEntity.ok(tweetService.deleteTweetById(tweetId,userService.findUserProfileByJwt(jwt).getId()));
    }

    @GetMapping("/all")
    public ResponseEntity<List<TweetDto>> getAllTweets(@RequestHeader("Authorization") String jwt){
        return ResponseEntity.ok(tweetService.findAllTweets(userService.findUserProfileByJwt(jwt)));
    }

    @GetMapping("/user")
    public ResponseEntity<List<TweetDto>> getUserAllTweets(@RequestHeader("Authorization") String jwt){
      return ResponseEntity.ok(tweetService.getUserTweet(userService.findUserProfileByJwt(jwt)));
    }

    @GetMapping("/user/likes")
    public ResponseEntity<List<TweetDto>> findTweetsByUserLikes(@RequestHeader("Authorization") String jwt){
      return ResponseEntity.ok(tweetService.findByLikesContainingUser(userService.findUserProfileByJwt(jwt)));
    }



}
