package com.example.post.core.service;

import com.example.post.core.entity.Post;

public interface PostService {

    Post getById(long id);

    void save(Post post);

    void delete(long id);
}
