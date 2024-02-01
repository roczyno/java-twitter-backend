package com.roczyno.twitter.backend.repository;

import com.roczyno.twitter.backend.model.Tweet;
import com.roczyno.twitter.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TweetRepository extends JpaRepository<Tweet,Long> {
    List<Tweet> findAllByIsTweetTrueOrderByCreatedAtDesc();
    List<Tweet> findByRetweetUserContainsOrUser_IdAndIsTweetTrueOrderByCreatedAtDesc(User user, long userId);
    List<Tweet> findByLikesContainingOrderByCreatedAtDesc(User user);
    @Query("SELECT t FROM Tweet t JOIN t.likes l WHERE l.userId = :userId")
    List<Tweet> findByLikesUser_id(@Param("userId") Long userId);

}
