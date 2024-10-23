package com.example.blog.repository;

<<<<<<< HEAD
import com.example.blog.entity.Blog;
import com.example.blog.entity.User;
import com.example.blog.entity.Category;
import com.example.blog.util.DatabaseUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
=======
>>>>>>> feature/check1

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.example.blog.entity.Blog;

@Repository
<<<<<<< HEAD
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
=======
public class BlogRepositoryImpl implements BlogRepository{
    
    private static final String POSTGRES_DRIVER = "org.postgresql.Driver";
    
    private static final String JDBC_CONNECTION = "jdbc:postgresql://localhost:5432/blog-system";
    
    private static final String USER = "postgres";
    
    private static final String PASS = "postgres";
    
    public List<Blog> findAll() {
        List<Blog> blogs = new ArrayList<>();
        
        try {
            Class.forName(POSTGRES_DRIVER);
            Connection connection = DriverManager.getConnection(JDBC_CONNECTION,USER,PASS);
            String SQL = "SELECT * FROM Blog WHERE delete_at IS NULL ORDER BY id DESC";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
>>>>>>> feature/check1
                Blog blog = new Blog();
                blog.setId(resultSet.getObject("id",UUID.class));
                blog.setTitle(resultSet.getString("title"));
                blog.setContent(resultSet.getString("content"));
                blog.setCreateAt(resultSet.getTimestamp("create_at").toLocalDateTime());
                blog.setUpdateAt(resultSet.getTimestamp("update_at").toLocalDateTime());
                blog.setDeleteAt(resultSet.getTimestamp("delete_at") != null ? resultSet.getTimestamp("delete_at").toLocalDateTime() : null);
                blogs.add(blog);
            }
        } catch (SQLException e) {
            // TODO: handle exception
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return blogs;
    }
<<<<<<< HEAD

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

=======
    
    @Override
    public void save(Blog blog) {
        String SQL = "INSERT INTO Blog (id,title,content) VALUES(?,?,?)";
        try {
            Class.forName(POSTGRES_DRIVER);
            Connection connection = DriverManager.getConnection(JDBC_CONNECTION,USER,PASS);
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            
            blog.setId(UUID.randomUUID());
            
            preparedStatement.setObject(1, blog.getId());
            preparedStatement.setString(2, blog.getTitle());
            preparedStatement.setString(3, blog.getContent());
//            preparedStatement.setTimestamp(4, java.sql.Timestamp.valueOf(blog.getCreateAt()));
//            preparedStatement.setTimestamp(5, java.sql.Timestamp.valueOf(blog.getUpdateAt()));
            
            preparedStatement.executeUpdate();
>>>>>>> feature/check1
        } catch (SQLException e) {
            // TODO: handle exception
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
    
    @Override
    public Blog findById(UUID id) {
        String SQL = "SELECT * FROM Blog WHERE id = ? AND delete_at IS NULL";
        Blog blog = null;
        try {
            Class.forName(POSTGRES_DRIVER);
            Connection connection = DriverManager.getConnection(JDBC_CONNECTION,USER,PASS);
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            
            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                blog = new Blog();
                blog.setId(resultSet.getObject("id",UUID.class));
                blog.setTitle(resultSet.getString("title"));
                blog.setContent(resultSet.getString("content"));
                blog.setCreateAt(resultSet.getTimestamp("create_at").toLocalDateTime());
            }
        } catch (SQLException e) {
            // TODO: handle exception
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return blog;
    }
    
    @Override
    public void update(Blog blog) {
<<<<<<< HEAD
        String sql = "UPDATE blogs SET title = ?, content = ?, category_id = ?, updated_at = ? WHERE id = ?";

        try (Connection conn = databaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, blog.getTitle());
            stmt.setString(2, blog.getContent());
            stmt.setInt(3, blog.getCategory().getId());
            stmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setInt(5, blog.getId());
            stmt.executeUpdate();

=======
        String SQL = "UPDATE Blog SET title=?,content=?,update_at = NOW() WHERE id = ?";
        try {
            Class.forName(POSTGRES_DRIVER);
            Connection connection = DriverManager.getConnection(JDBC_CONNECTION,USER,PASS);
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            
            preparedStatement.setString(1, blog.getTitle());
            preparedStatement.setString(2, blog.getContent());
            preparedStatement.setObject(3, blog.getId());
            
            preparedStatement.executeUpdate();
>>>>>>> feature/check1
        } catch (SQLException e) {
            // TODO: handle exception
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
    
    @Override
<<<<<<< HEAD
    public void delete(Integer id) {
        String sql = "UPDATE blogs SET deleted_at = NOW() WHERE id = ?";

        try (Connection conn = databaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

=======
    public void delete(UUID id) {
        String SQL = "UPDATE Blog SET delete_at = NOW() WHERE id = ?";
        try {
            Class.forName(POSTGRES_DRIVER);
            Connection connection = DriverManager.getConnection(JDBC_CONNECTION,USER,PASS);
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            
            preparedStatement.setObject(1, id);
            preparedStatement.executeUpdate();
>>>>>>> feature/check1
        } catch (SQLException e) {
            // TODO: handle exception
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
<<<<<<< HEAD

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
=======
>>>>>>> feature/check1
}
