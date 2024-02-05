package com.roczyno.twitter.backend.service;

import com.roczyno.twitter.backend.config.JwtProvider;
import com.roczyno.twitter.backend.exception.UserException;
import com.roczyno.twitter.backend.model.User;
import com.roczyno.twitter.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtProvider jwtProvider;
    @Override
    public User findUserById(Long userId) throws UserException {
        User user = userRepository.findById(userId).orElseThrow(()->new UserException("User with id not found"));
        return user;
    }

    @Override
    public User findUserProfileByJwt(String jwt) throws UserException {
        String email=jwtProvider.getEmailFromToken(jwt);
        User user=userRepository.findByEmail(email);
        if(user==null){
            throw new UserException("User with email" +email+ " not found");
        }
        return user;
    }

    @Override
    public User updateUser(Long userId, User req) throws UserException {
        User user = findUserById(userId);
        if(req.getFullName()!=null){
            user.setFullName(req.getFullName());
        }
        if(req.getEmail()!=null){
            user.setEmail(req.getEmail());
        }
        if(req.getImage()!=null){
            user.setImage(req.getImage());
        }
        return userRepository.save(user);
    }

    @Override
    public User followUser(Long userId, User user) throws UserException {
        // Retrieve the user to be followed/unfollowed by their ID
        User userToBeFollowed = findUserById(userId);

        // Check if the users are already following each other
        if (user.getFollowings().contains(userToBeFollowed) && userToBeFollowed.getFollowers().contains(user)) {
            // If already following, remove the follow relationship
            user.getFollowers().remove(userToBeFollowed);
            userToBeFollowed.getFollowings().remove(user);
        } else {
            // If not following, add the follow relationship
            user.getFollowers().add(userToBeFollowed);
            userToBeFollowed.getFollowings().add(user);
        }
        // Save the changes to both users
        userRepository.save(userToBeFollowed);
        userRepository.save(user);
        // Return the user being followed/unfollowed
        return userToBeFollowed;
    }


    @Override
    public List<User> searchUser(String query) {
        return userRepository.searchUser(query);
    }
}
