package com.example.blog.repository;

import com.example.blog.entity.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentRepository {
    @Select("SELECT * FROM comments WHERE blog_id = #{blogId} AND deleted_at IS NULL")
    List<Comment> findByBlogId(Integer blogId);

    @Select("SELECT * FROM comments WHERE id = #{id}")
    Comment findById(Integer id);

    @Insert("INSERT INTO comments (content, blog_id) VALUES (#{content}, #{blogId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void save(Comment comment);

    @Update("UPDATE comments SET content = #{content} WHERE id = #{id}")
    void update(Comment comment);

    @Update("UPDATE comments SET deleted_at = NOW() WHERE id = #{id}")
    void delete(Integer id);

    @Update("UPDATE comments SET deleted_at = NOW() WHERE blog_id = #{blogId}")
    void deleteByBlogId(Integer id);
}
