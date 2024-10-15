package com.roczyno.twitter.backend.request;

public record LoginRequest(
		String email,
		String password
) {
}
