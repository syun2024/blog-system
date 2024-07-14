package com.example.blog.repository;

import com.example.blog.entity.Blog;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BlogRepository {
    @Select("SELECT * FROM blogs WHERE deleted_at IS NULL")
    List<Blog> findAll();

    @Select("SELECT * FROM blogs WHERE id = #{id}")

    Blog findById(Integer id);

    @Insert("INSERT INTO blogs (title, content) VALUES (#{title}, #{content})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void save(Blog blog);

    @Update("UPDATE blogs SET title = #{title}, content = #{content} WHERE id = #{id}")
    void update(Blog blog);

    @Update("UPDATE blogs SET deleted_at = NOW() WHERE id = #{id}")
    void delete(Integer id);
}