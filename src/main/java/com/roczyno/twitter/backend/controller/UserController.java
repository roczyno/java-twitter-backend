package com.roczyno.twitter.backend.controller;

import com.roczyno.twitter.backend.dto.UserDto;
import com.roczyno.twitter.backend.dto.UserDtoMapper;
import com.roczyno.twitter.backend.exception.UserException;
import com.roczyno.twitter.backend.model.User;
import com.roczyno.twitter.backend.service.UserService;
import com.roczyno.twitter.backend.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/profile/{userId}")
    public ResponseEntity<UserDto> getUserProfile(@RequestHeader("Authorization") String jwt)
            throws UserException {
        User user=userService.findUserProfileByJwt(jwt);
        UserDto userDto= UserDtoMapper.toUserDto(user);
        userDto.setReq_user(true);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable long userId, @RequestHeader("Authorization") String jwt)
            throws UserException {
        User reqUser=userService.findUserProfileByJwt(jwt);
        User user=userService.findUserById(userId);
        UserDto userDto= UserDtoMapper.toUserDto(user);
        userDto.setReq_user(UserUtil.isReqUser(reqUser,user));
        userDto.setFollowed(UserUtil.isFollowedByReqUser(reqUser,user));
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserDto>>searchUser(@RequestParam String query,
                                                   @RequestHeader("Authorization") String jwt) throws UserException {
        User reqUser = userService.findUserProfileByJwt(jwt);
        List<User> users= userService.searchUser(query);
        List<UserDto > userDtos = UserDtoMapper.toUserDtos(users);
        return new ResponseEntity<>(userDtos, HttpStatus.OK);


    }

    @PutMapping("/update")
    public ResponseEntity<UserDto> updateUser(@RequestBody User req,
                                              @RequestHeader("Authorization") String jwt) throws UserException {
        User reqUser= userService.findUserProfileByJwt(jwt);
        User user =userService.updateUser(reqUser.getId(),req);
        UserDto userDto= UserDtoMapper.toUserDto(user);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PutMapping("/{userId}/follow")
    public ResponseEntity<UserDto> followUser(@PathVariable Long userId,
                                              @RequestHeader("Authorization") String jwt) throws UserException {
        User reqUser= userService.findUserProfileByJwt(jwt);
        User user= userService.followUser(userId,reqUser);
        UserDto userDto= UserDtoMapper.toUserDto(user);
        userDto.setFollowed(UserUtil.isFollowedByReqUser(reqUser,user));
        return new ResponseEntity<>(userDto, HttpStatus.ACCEPTED);
    }


}
