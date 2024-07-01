package com.example.blog.repository;

import com.example.blog.entity.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentRepository {
    @Select("SELECT * FROM comments WHERE blog_id = #{blogId}")
    List<Comment> findByBlogId(Long blogId);

    @Select("SELECT * FROM comments WHERE id = #{id}")
    Comment findById(Long id);

    @Insert("INSERT INTO comments (content, author, blog_id) VALUES (#{content}, #{author}, #{blogId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void save(Comment comment);

    @Update("UPDATE comments SET content = #{content}, author = #{author} WHERE id = #{id}")
    void update(Comment comment);

    @Delete("DELETE FROM comments WHERE id = #{id}")
    void delete(Long id);
}
