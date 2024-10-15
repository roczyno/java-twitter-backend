package com.roczyno.twitter.backend.controller;

import com.roczyno.twitter.backend.dto.UserDto;
import com.roczyno.twitter.backend.request.UpdateUserRequest;
import com.roczyno.twitter.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long userId){
        return ResponseEntity.ok(userService.findUserById(userId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserDto>>searchUser(@RequestParam String query){
         return ResponseEntity.ok(userService.searchUser(query));
    }

    @PutMapping("/update")
    public ResponseEntity<UserDto> updateUser(@RequestBody UpdateUserRequest req, @RequestHeader("Authorization") String jwt) {
       return ResponseEntity.ok(userService.updateUser(userService.findUserProfileByJwt(jwt).getId(),req));

    }

    @PutMapping("/{userId}/follow")
    public ResponseEntity<String> followUser(@PathVariable Long userId,@RequestHeader("Authorization") String jwt){
        return ResponseEntity.ok(userService.followUser(userId,userService.findUserProfileByJwt(jwt)));
    }


}
