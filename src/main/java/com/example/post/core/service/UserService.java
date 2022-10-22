package com.example.post.core.service;

import com.example.post.core.entity.User;

import java.util.List;

public interface UserService {

    void save(User user);

    User getById(long id);

    void delete(long id);

    User getByUsername(String username);

    List<User> getAll();
}
