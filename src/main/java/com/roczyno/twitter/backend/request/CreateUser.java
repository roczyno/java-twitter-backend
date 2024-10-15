package com.roczyno.twitter.backend.request;

public record CreateUser(
		String email,
		String fullName,
		String password
) {
}
