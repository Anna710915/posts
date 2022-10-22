package com.example.post.core.repository;

import com.example.post.core.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long>, JpaRepository<User, Long> {

    User getUserByUsername(String username);
}
