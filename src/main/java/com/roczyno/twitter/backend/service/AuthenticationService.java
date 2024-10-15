package com.roczyno.twitter.backend.service;

import com.roczyno.twitter.backend.config.JwtProvider;
import com.roczyno.twitter.backend.exception.UserException;
import com.roczyno.twitter.backend.model.User;
import com.roczyno.twitter.backend.model.Verification;
import com.roczyno.twitter.backend.repository.UserRepository;
import com.roczyno.twitter.backend.request.CreateUser;
import com.roczyno.twitter.backend.request.LoginRequest;
import com.roczyno.twitter.backend.response.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

	private final UserRepository userRepository;

	private final JwtProvider jwtProvider;

	private final CustomUserDetailsServiceImplementation customUserDetailsServiceImplementation;

	private final PasswordEncoder passwordEncoder;



	public String registerUser(CreateUser user ){
		String email = user.email();
		String password = user.password();
		String fullName= user.fullName();

		User isEmailExist= userRepository.findByEmail(email);
		if(isEmailExist!=null){
			throw new UserException("User already exist");
		}
		User createdUser = new User();
		createdUser.setEmail(email);
		createdUser.setFullName(fullName);
		createdUser.setPassword(passwordEncoder.encode(password));
		createdUser.setVerification(new Verification());

		userRepository.save(createdUser);
		return "registration successful";

	}

	public AuthResponse login(LoginRequest user){
		String username= user.email();
		String password=user.password();

		Authentication authentication= authenticate(username,password);
		String token = jwtProvider.generateToken(authentication);
		return new AuthResponse(token,true);
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
