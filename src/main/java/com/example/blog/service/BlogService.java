package com.example.blog.service;

import com.example.blog.entity.Blog;
import com.example.blog.repository.BlogRepository;
import com.example.blog.repository.CommentRepository;
import com.example.blog.exception.DuplicateDataException;
import com.example.blog.exception.DatabaseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public void createBlog(Blog blog) {
        if (isTitleDuplicate(blog.getTitle())) {
            throw new DuplicateDataException("Title already exists");
        }

        try {
            blogRepository.save(blog);
        } catch (DataAccessException e) {
            throw new DatabaseException("An error occurred while accessing the database", e);
        }
    }

    @Transactional
    public void updateBlog(Integer id, Blog blog) {
        if (isTitleDuplicateForUpdate(blog.getTitle(), id)) {
            throw new DuplicateDataException("Title already exists");
        }

        try {
            blog.setId(id);
            blogRepository.update(blog);
        } catch (DataAccessException e) {
            throw new DatabaseException("An error occurred while accessing the database", e);
        }
    }

    @Transactional
    public void delete(Integer id) {
        commentRepository.deleteByBlogId(id);
        blogRepository.delete(id);
    }

    public boolean isTitleDuplicate(String title) {
        return blogRepository.countByTitle(title) > 0;
    }

    public boolean isTitleDuplicateForUpdate(String title, Integer id) {
        return blogRepository.countByTitleAndNotId(title, id) > 0;
    }
}
