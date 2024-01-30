package com.roczyno.twitter.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String fullName;
    private String email;
    private String password;
    private String image;
    private boolean reg_user;
    private boolean login_with_google;
    @Embedded
    private Verification verification;
    @JsonIgnore
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Tweet> tweets = new ArrayList<>();
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Like> likes = new ArrayList<>();
    @JsonIgnore
    @ManyToMany
    private List<User> followers = new ArrayList<>();
    @JsonIgnore
    @ManyToMany
    private List<User> followings = new ArrayList<>();

}
