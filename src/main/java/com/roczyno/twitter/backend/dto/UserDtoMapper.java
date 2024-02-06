package com.roczyno.twitter.backend.dto;

import com.roczyno.twitter.backend.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDtoMapper {
    public static UserDto toUserDto(User user){
        UserDto userDto= new UserDto();
        userDto.setId(user.getId());
        userDto.setFullName(user.getFullName());
        userDto.setImage(user.getImage());
        userDto.setFollowers(toUserDtos(user.getFollowers()));
        userDto.setFollowings(toUserDtos(user.getFollowings()));
        userDto.setLogin_with_google(user.isLogin_with_google());


        return userDto;
    }

    public static List<UserDto> toUserDtos(List<User> followers) {
        List<UserDto> userDtos= new ArrayList<>();
        for(User user:followers){
            UserDto userDto= new UserDto();
            userDto.setId(user.getId());
            userDto.setFullName(user.getFullName());
            userDto.setImage(user.getImage());
            userDtos.add(userDto);
        }
        return userDtos;
    }
}
