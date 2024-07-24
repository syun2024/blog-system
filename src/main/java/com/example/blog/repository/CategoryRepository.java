package com.example.blog.repository;

import com.example.blog.entity.Category;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryRepository {
    List<Category> findAll();
}
