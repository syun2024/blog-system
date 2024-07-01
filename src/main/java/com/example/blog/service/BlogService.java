package com.example.blog.service;

import com.example.blog.entity.Blog;
import com.example.blog.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogService {
    @Autowired
    private BlogRepository blogRepository;

    public List<Blog> findAll() {
        return blogRepository.findAll();
    }

    public Blog findById(Long id) {
        return blogRepository.findById(id);
    }

    public void save(Blog blog) {
        blogRepository.save(blog);
    }

    public void update(Blog blog) {
        blogRepository.update(blog);
    }

    public void delete(Long id) {
        blogRepository.delete(id);
    }
}