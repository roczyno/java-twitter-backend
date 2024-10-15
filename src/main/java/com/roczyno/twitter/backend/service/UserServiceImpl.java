package com.roczyno.twitter.backend.service;

import com.roczyno.twitter.backend.config.JwtProvider;
import com.roczyno.twitter.backend.dto.UserDto;
import com.roczyno.twitter.backend.dto.UserDtoMapper;
import com.roczyno.twitter.backend.exception.UserException;
import com.roczyno.twitter.backend.model.User;
import com.roczyno.twitter.backend.repository.UserRepository;
import com.roczyno.twitter.backend.request.UpdateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final UserDtoMapper userDtoMapper;

    @Override
    public UserDto findUserById(Long userId) {
        User user=userRepository.findById(userId).orElseThrow(()->new UserException("User with id not found"));
       return userDtoMapper.toUserDto(user);
    }

    @Override
    public User findUserProfileByJwt(String jwt) {
        String email=jwtProvider.getEmailFromToken(jwt);
        User user=userRepository.findByEmail(email);
        if(user==null){
            throw new UserException("User with email" +email+ " not found");
        }
        return user;
    }

    @Override
    public UserDto updateUser(Long userId, UpdateUserRequest req) {
        User user = userRepository.findById(userId).orElseThrow(()->new UserException("User with id not found"));
        if(req.fullName()!=null){
            user.setFullName(req.fullName());
        }
        if(req.image()!=null){
            user.setImage(req.image());
        }
        User updatedUser= userRepository.save(user);
        return userDtoMapper.toUserDto(updatedUser);
    }

    @Override
    public String followUser(Long userId, User user){

        User userToBeFollowed = userRepository.findById(userId).orElseThrow(()->new UserException("User with id not found"));

        if (user.getFollowings().contains(userToBeFollowed) && userToBeFollowed.getFollowers().contains(user)) {

            user.getFollowers().remove(userToBeFollowed);
            userToBeFollowed.getFollowings().remove(user);
        } else {

            user.getFollowers().add(userToBeFollowed);
            userToBeFollowed.getFollowings().add(user);
        }

        userRepository.save(userToBeFollowed);
        userRepository.save(user);

      return "follow successful";
    }


    @Override
    public List<UserDto> searchUser(String query) {
        List<User> users=userRepository.searchUser(query);
        return userDtoMapper.toUserDtos(users);
    }
}
