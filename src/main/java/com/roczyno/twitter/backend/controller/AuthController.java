package com.roczyno.twitter.backend.controller;


import com.roczyno.twitter.backend.request.CreateUser;
import com.roczyno.twitter.backend.request.LoginRequest;
import com.roczyno.twitter.backend.response.AuthResponse;
import com.roczyno.twitter.backend.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<String> createUserHandler(@RequestBody CreateUser user)  {
       return ResponseEntity.ok(authenticationService.registerUser(user));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest user){
        return ResponseEntity.ok(authenticationService.login(user));
    }
}
