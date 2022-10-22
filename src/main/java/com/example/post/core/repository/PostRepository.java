package com.example.post.core.repository;

import com.example.post.core.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface PostRepository  {

    Post getPost(long id);
}
