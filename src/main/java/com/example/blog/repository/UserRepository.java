package com.example.blog.repository;

import com.example.blog.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserRepository {
    User findByUsername(String username);

    int countByUsername(String username);

    void save(User user);
}
