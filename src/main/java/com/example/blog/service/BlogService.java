package com.example.blog.service;

import com.example.blog.entity.Blog;
import com.example.blog.repository.BlogRepository;
import com.example.blog.repository.CommentRepository;
import com.example.blog.exception.DuplicateDataException;
import com.example.blog.exception.DatabaseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.context.MessageSource;

import java.util.List;

@Service
public class BlogService {
    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MessageSource messageSource;

    public List<Blog> findAll() {
        return blogRepository.findAll();
    }

    public Blog findById(Integer id) {
        return blogRepository.findById(id);
    }

    @Transactional
    public void createBlog(Blog blog) {
        if (isTitleDuplicate(blog.getTitle())) {
            throw new DuplicateDataException(
                    messageSource.getMessage("error.blog.title.duplicate", null, null));
        }

        try {
            blogRepository.save(blog);
        } catch (Exception e) {
            throw new DatabaseException(messageSource.getMessage("error.database", null, null),
                    e);
        }
    }

    @Transactional
    public void updateBlog(Integer id, Blog blog) {
        if (isTitleDuplicateForUpdate(blog.getTitle(), id)) {
            throw new DuplicateDataException(
                    messageSource.getMessage("error.blog.title.duplicate", null, null));
        }

        try {
            blog.setId(id);
            blogRepository.update(blog);
        } catch (Exception e) {
            throw new DatabaseException(
                    messageSource.getMessage("error.database", null, null), e);
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
