package com.roczyno.twitter.backend.repository;

import com.roczyno.twitter.backend.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like,Long> {
    @Query("SELECT l FROM LIKE l WHERE l.user.d=:userId AND l.tweet.id=:tweetId")
     Like isLiked(@Param("userId") long userId,@Param("tweetId") long tweetId);

    @Query("SELECT l FROM LIKE l WHERE l.tweet.id=:tweetId")
    List<Like> findByTweetId(@Param("tweetId") Long tweetId);


}
