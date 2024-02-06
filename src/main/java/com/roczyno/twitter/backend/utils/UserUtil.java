package com.roczyno.twitter.backend.utils;

import com.roczyno.twitter.backend.model.User;

public class UserUtil {
    public static final boolean isReqUser(User reqUser,User user2){
        return reqUser.getId() == user2.getId();
    }

    public static final boolean isFollowedByReqUser(User reqUser,User user2){
        return reqUser.getFollowings().contains(user2);

    }
}
