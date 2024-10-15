package com.roczyno.twitter.backend.service;

import com.roczyno.twitter.backend.dto.UserDto;
import com.roczyno.twitter.backend.exception.UserException;
import com.roczyno.twitter.backend.model.User;
import com.roczyno.twitter.backend.request.UpdateUserRequest;

import java.util.List;

public interface UserService {
    UserDto findUserById(Long userId);
    User findUserProfileByJwt(String jwt);
    UserDto updateUser(Long userId, UpdateUserRequest user);
    String followUser (Long userId,User user);
    List<UserDto> searchUser(String query);


}
