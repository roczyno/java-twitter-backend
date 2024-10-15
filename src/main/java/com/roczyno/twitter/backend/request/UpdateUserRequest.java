package com.roczyno.twitter.backend.request;

public record UpdateUserRequest(
		String fullName,
		String image
) {
}
