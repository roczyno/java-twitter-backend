package com.roczyno.twitter.backend.request;

import com.roczyno.twitter.backend.model.Tweet;

import java.time.LocalDateTime;

public record TweetRequest(
		 String content,
		 String image,
		 String video,
		 Tweet replyFor,
		 boolean isReply,
		 boolean isTweet,
		 LocalDateTime createdAt
) {
}
