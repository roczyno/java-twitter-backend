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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tweet")
public class TweetController {

    private TweetService tweetService;
    private UserService userService;


    @PostMapping("/create")
    public ResponseEntity<TweetDto> createdTweet(@RequestBody Tweet req, @RequestHeader("Authorization") String jwt)
            throws UserException{
        User user = userService.findUserProfileByJwt(jwt);
        Tweet tweet= tweetService.createTweet(req,user);
        TweetDto tweetDto= TweetDtoMapper.toTweetDto(tweet,user);
        return new ResponseEntity<>(tweetDto, HttpStatus.CREATED);
    }

    @PostMapping("/reply")
    public ResponseEntity<TweetDto> replyTweet(@RequestBody TweetReplyRequest req, @RequestHeader("Authorization") String jwt)
            throws UserException, TweetException{
        User user = userService.findUserProfileByJwt(jwt);
        Tweet tweet= tweetService.createdReply(req,user);
        TweetDto tweetDto= TweetDtoMapper.toTweetDto(tweet,user);
        return new ResponseEntity<>(tweetDto, HttpStatus.CREATED);
    }

    @PutMapping("/{tweetId}/retweet")
    public ResponseEntity<TweetDto> reTweet(@PathVariable Long tweetId, @RequestHeader("Authorization") String jwt)
            throws UserException, TweetException{
        User user = userService.findUserProfileByJwt(jwt);
        Tweet tweet= tweetService.retweet(tweetId,user);
        TweetDto tweetDto= TweetDtoMapper.toTweetDto(tweet,user);
        return new ResponseEntity<>(tweetDto, HttpStatus.OK);
    }

    @GetMapping("/{tweetId}")
    public ResponseEntity<TweetDto> findTweetById(@PathVariable Long tweetId, @RequestHeader("Authorization") String jwt)
            throws UserException, TweetException{
        User user = userService.findUserProfileByJwt(jwt);
        Tweet tweet= tweetService.findTweetById(tweetId);
        TweetDto tweetDto= TweetDtoMapper.toTweetDto(tweet,user);
        return new ResponseEntity<>(tweetDto, HttpStatus.OK);
    }

    @DeleteMapping("/{tweetId}")
    public ResponseEntity<ApiResponse> deleteTweetById(@PathVariable Long tweetId, @RequestHeader("Authorization") String jwt)
            throws UserException, TweetException{
        User user = userService.findUserProfileByJwt(jwt);
       tweetService.deleteTweetById(tweetId,user.getId());

       ApiResponse res = new ApiResponse();
       res.setMessage("Tweet deleted successfully");
       res.setStatus(true);

       return new ResponseEntity<>(res,HttpStatus.OK);


    }

    @GetMapping("/")
    public ResponseEntity<List<TweetDto>> getAllTweets(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserProfileByJwt(jwt);
       List<Tweet> tweets= tweetService.findAllTweets();
        List<TweetDto> tweetDtos= TweetDtoMapper.toTweetDtos(tweets,user);
        return new ResponseEntity<>(tweetDtos,HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TweetDto>> getUserAllTweets(@PathVariable Long userId, @RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserProfileByJwt(jwt);
        List<Tweet> tweets= tweetService.getUserTweet(user);
        List<TweetDto> tweetDtos= TweetDtoMapper.toTweetDtos(tweets,user);
        return new ResponseEntity<>(tweetDtos,HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/likes")
    public ResponseEntity<List<TweetDto>> findTweetByLikesContainingUser(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserProfileByJwt(jwt);
        List<Tweet> tweets= tweetService.findByLikesContainingUser(user);
        List<TweetDto> tweetDtos= TweetDtoMapper.toTweetDtos(tweets,user);
        return new ResponseEntity<>(tweetDtos,HttpStatus.OK);
    }



}