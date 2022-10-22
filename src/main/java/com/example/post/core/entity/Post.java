package com.example.post.core.entity;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private long id;

    @Column(name = "post_message", nullable = false)
    @Pattern(regexp = "[A-z0-9\s]{1,1000}")
    private String postMessage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    public Post(){}

    public Post(String postMessage) {
        this.postMessage = postMessage;
    }

    public Post(long id, String postMessage) {
        this.id = id;
        this.postMessage = postMessage;
    }

    public Post(long id, String postMessage, User user) {
        this.id = id;
        this.postMessage = postMessage;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPostMessage() {
        return postMessage;
    }

    public void setPostMessage(String postMessage) {
        this.postMessage = postMessage;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
