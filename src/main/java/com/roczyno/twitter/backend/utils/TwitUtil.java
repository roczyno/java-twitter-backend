package com.roczyno.twitter.backend.utils;

import com.roczyno.twitter.backend.model.Like;
import com.roczyno.twitter.backend.model.Tweet;
import com.roczyno.twitter.backend.model.User;

import java.util.Objects;

public class TwitUtil {
    public final static boolean isLikedByReqUser(User reqUser, Tweet tweet){
        for(Like like: tweet.getLikes()){
            if (Objects.equals(like.getUser().getId(), reqUser.getId())) {
                return true;
            }
        }
        return false;
    }

    public final static boolean isRetweetedByReqUser(User reqUser, Tweet tweet){
        for (User user:tweet.getRetweetUser()){
            if(Objects.equals(user.getId(), reqUser.getId())){
                return true;
            }
        }
        return false;
    }
}
