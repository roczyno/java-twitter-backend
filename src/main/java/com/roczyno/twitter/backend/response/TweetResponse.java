package com.roczyno.twitter.backend.response;

import com.roczyno.twitter.backend.model.Tweet;

import java.time.LocalDateTime;

public record TweetResponse(
		Long id,
		String content,
		String image,
		String video,
		Tweet replyFor,
		boolean isReply,
		boolean isTweet,
		LocalDateTime createdAt
) {
}
