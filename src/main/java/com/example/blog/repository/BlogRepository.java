package com.example.blog.repository;

import com.example.blog.entity.Blog;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BlogRepository {
    @Select("SELECT * FROM blogs")
    List<Blog> findAll();

    @Select("SELECT * FROM blogs WHERE id = #{id}")
    Blog findById(Long id);

    @Insert("INSERT INTO blogs (title, content, author) VALUES (#{title}, #{content}, #{author})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void save(Blog blog);

    @Update("UPDATE blogs SET title = #{title}, content = #{content} WHERE id = #{id}")
    void update(Blog blog);

    @Delete("DELETE FROM blogs WHERE id = #{id}")
    void delete(Long id);
}