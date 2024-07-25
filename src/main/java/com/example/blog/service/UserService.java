package com.example.blog.service;

import com.example.blog.entity.User;
import com.example.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.blog.exception.DuplicateDataException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findByUsername(String username) { // 追加
        return userRepository.findByUsername(username);
    }

    public void save(User user) {
        if (isUsernameDuplicate(user.getUsername())) {
            throw new DuplicateDataException("Username already exists");
        }
        userRepository.save(user);
    }

    public boolean isUsernameDuplicate(String username) {
        return userRepository.countByUsername(username) > 0;
    }
}
