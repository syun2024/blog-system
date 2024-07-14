package com.example.blog.service;

import com.example.blog.entity.Blog;
import com.example.blog.repository.BlogRepository;
import com.example.blog.repository.CommentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogService {
    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private CommentRepository commentRepository;

    public List<Blog> findAll() {
        return blogRepository.findAll();
    }

    public Blog findById(Integer id) {
        return blogRepository.findById(id);
    }

    public void save(Blog blog) {
        blogRepository.save(blog);
    }

    public void update(Blog blog) {
        blogRepository.update(blog);
    }

    public void delete(Integer id) {
        commentRepository.deleteByBlogId(id);
        blogRepository.delete(id);
    }
}