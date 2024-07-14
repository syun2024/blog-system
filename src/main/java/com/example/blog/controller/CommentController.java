package com.example.blog.controller;

import com.example.blog.entity.Blog;
import com.example.blog.entity.Comment;
import com.example.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/blogs/{blogId}/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping
    public String list(@PathVariable Integer blogId, Model model) {
        List<Comment> comments = commentService.findByBlogId(blogId);
        model.addAttribute("comments", comments);
        Blog blog = new Blog();
        blog.setId(blogId);
        model.addAttribute("blog", blog);
        return "comment/list";
    }

    @GetMapping("/new")
    public String createForm(@PathVariable Integer blogId, Model model) {
        Comment comment = new Comment();
        // comment.setBlogId(blogId);
        model.addAttribute("comment", comment);
        return "comment/form";
    }

    @PostMapping("/create")
    public String create(@PathVariable Integer blogId, @ModelAttribute Comment comment) {
        // comment.setBlogId(blogId);
        commentService.save(comment);
        return "redirect:/blogs/" + blogId + "/comments";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Integer blogId, @PathVariable Integer id, Model model) {
        Comment comment = commentService.findById(id);
        model.addAttribute("comment", comment);
        return "comment/form";
    }

    @PostMapping("/{id}/edit")
    public String update(@PathVariable Integer blogId, @PathVariable Integer id, @ModelAttribute Comment comment) {
        comment.setId(id);
        // comment.setBlogId(blogId);
        commentService.update(comment);
        return "redirect:/blogs/" + blogId + "/comments";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Integer blogId, @PathVariable Integer id) {
        commentService.delete(id);
        return "redirect:/blogs/" + blogId + "/comments";
    }
}
