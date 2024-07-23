package com.example.blog.repository;

import com.example.blog.entity.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentRepository {
    List<Comment> findByBlogId(Integer blogId);

    Comment findById(Integer id);

    void save(Comment comment);

    void update(Comment comment);

    void delete(Integer id);

    void deleteByBlogId(Integer id);
}
