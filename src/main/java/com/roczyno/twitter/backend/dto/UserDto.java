package com.roczyno.twitter.backend.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserDto {
    private long id;
    private String fullName;
    private String email;
    private String password;
    private String image;
    private boolean req_user;
    private boolean login_with_google;
    private List<UserDto> followers = new ArrayList<>();
    private List<UserDto> followings= new ArrayList<>();
    private boolean isVerified;
    private boolean followed;
}
