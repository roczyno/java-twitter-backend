package com.roczyno.twitter.backend.repository;

import com.roczyno.twitter.backend.model.Tweet;
import com.roczyno.twitter.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TweetRepository extends JpaRepository<Tweet,Long> {
    List<Tweet> findAllByIsTweetTrueOrderByCreatedAtDesc();
    List<Tweet> findByRetweetUserContainsOrUser_IdAndIsTweetTrueOrderByCreatedAtDesc(User user, long userId);
}
