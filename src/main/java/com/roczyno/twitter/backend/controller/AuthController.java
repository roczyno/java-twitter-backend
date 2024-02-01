package com.roczyno.twitter.backend.controller;


import com.roczyno.twitter.backend.config.JwtProvider;
import com.roczyno.twitter.backend.exception.UserException;
import com.roczyno.twitter.backend.model.User;
import com.roczyno.twitter.backend.model.Verification;
import com.roczyno.twitter.backend.repository.UserRepository;
import com.roczyno.twitter.backend.response.AuthResponse;
import com.roczyno.twitter.backend.service.CustomUserDetailsServiceImplementation;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private CustomUserDetailsServiceImplementation customUserDetailsServiceImplementation;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws UserException {
        System.out.println("register");
        String email = user.getEmail();
        String password = user.getPassword();
        String fullName= user.getFullName();

        User isEmailExist= userRepository.findByEmail(email);
        if(isEmailExist!=null){
            throw new UserException("User already exist");
        }
        User createdUser = new User();
        createdUser.setEmail(email);
        createdUser.setFullName(fullName);
        createdUser.setPassword(passwordEncoder.encode(password));
        createdUser.setVerification(new Verification());

        User savedUser = userRepository.save(createdUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(email,password);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);
        AuthResponse res= new AuthResponse(token,true);
        return new ResponseEntity<AuthResponse>(res, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody User user){

        String username= user.getEmail();
        String password=user.getPassword();

        Authentication authentication= authenticate(username,password);
        String token = jwtProvider.generateToken(authentication);
        AuthResponse res= new AuthResponse(token,true);
        return new ResponseEntity<AuthResponse>(res, HttpStatus.ACCEPTED);

    }

    private Authentication authenticate(String username, String password) {

        UserDetails userDetails= customUserDetailsServiceImplementation.loadUserByUsername(username);
        if(userDetails ==null){
            throw new BadCredentialsException("Invalid username");
        }
        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("Wrong credentials");
        }
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }
}
