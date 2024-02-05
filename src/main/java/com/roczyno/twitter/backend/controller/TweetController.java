package com.roczyno.twitter.backend.controller;

import com.roczyno.twitter.backend.service.TweetService;
import com.roczyno.twitter.backend.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tweet")
public class TweetController {

    private TweetService tweetService;
    private UserService userService;
}