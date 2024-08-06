package com.example.blog.repository;

import com.example.blog.entity.Blog;
import com.example.blog.entity.User;
import com.example.blog.entity.Category;
import com.example.blog.util.DatabaseUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BlogRepositoryImpl implements BlogRepository {

    @Autowired
    private DatabaseUtil databaseUtil;

    @Override
    public List<Blog> findAll() {
        List<Blog> blogs = new ArrayList<>();
        String sql = "SELECT b.*, u.id as user_id, u.username, u.email, c.id as category_id, c.name_ja " +
                "FROM blogs b " +
                "JOIN users u ON b.user_id = u.id " +
                "LEFT JOIN categories c ON b.category_id = c.id " +
                "WHERE b.deleted_at IS NULL " +
                "ORDER BY id DESC";

        try (Connection conn = databaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Blog blog = new Blog();
                blog.setId(rs.getInt("id"));
                blog.setTitle(rs.getString("title"));
                blog.setContent(rs.getString("content"));
                blog.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                blog.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                blog.setDeletedAt(
                        rs.getTimestamp("deleted_at") != null ? rs.getTimestamp("deleted_at").toLocalDateTime() : null);

                User user = new User();
                user.setId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                blog.setUser(user);

                Category category = new Category();
                category.setId(rs.getInt("category_id"));
                category.setNameJa(rs.getString("name_ja"));
                blog.setCategory(category);

                blogs.add(blog);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return blogs;
    }

    @Override
    public Blog findById(Integer id) {
        Blog blog = null;
        String sql = "SELECT b.*, u.id as user_id, u.username, u.email, c.id as category_id, c.name_ja " +
                "FROM blogs b " +
                "JOIN users u ON b.user_id = u.id " +
                "LEFT JOIN categories c ON b.category_id = c.id " +
                "WHERE b.id = ? AND b.deleted_at IS NULL";

        try (Connection conn = databaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    blog = new Blog();
                    blog.setId(rs.getInt("id"));
                    blog.setTitle(rs.getString("title"));
                    blog.setContent(rs.getString("content"));
                    blog.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    blog.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                    blog.setDeletedAt(
                            rs.getTimestamp("deleted_at") != null ? rs.getTimestamp("deleted_at").toLocalDateTime()
                                    : null);

                    User user = new User();
                    user.setId(rs.getInt("user_id"));
                    user.setUsername(rs.getString("username"));
                    user.setEmail(rs.getString("email"));
                    blog.setUser(user);

                    Category category = new Category();
                    category.setId(rs.getInt("category_id"));
                    category.setNameJa(rs.getString("name_ja"));
                    blog.setCategory(category);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return blog;
    }

    @Override
    public void save(Blog blog) {
        String sql = "INSERT INTO blogs (title, content, user_id, category_id) VALUES (?, ?, ?, ?)";

        try (Connection conn = databaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, blog.getTitle());
            stmt.setString(2, blog.getContent());
            stmt.setInt(3, blog.getUser().getId());
            stmt.setInt(4, blog.getCategory().getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Blog blog) {
        String sql = "UPDATE blogs SET title = ?, content = ?, category_id = ?, updated_at = ? WHERE id = ?";

        try (Connection conn = databaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, blog.getTitle());
            stmt.setString(2, blog.getContent());
            stmt.setInt(3, blog.getCategory().getId());
            stmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setInt(5, blog.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "UPDATE blogs SET deleted_at = NOW() WHERE id = ?";

        try (Connection conn = databaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Integer countByTitle(String title) {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM blogs WHERE title = ? AND deleted_at IS NULL";

        try (Connection conn = databaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, title);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    @Override
    public Integer countByTitleAndNotId(String title, Integer id) {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM blogs WHERE title = ? AND id != ? AND deleted_at IS NULL";

        try (Connection conn = databaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, title);
            stmt.setInt(2, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }
}
