package com.example.blog.controller;

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
    public String list(@PathVariable Long blogId, Model model) {
        List<Comment> comments = commentService.findByBlogId(blogId);
        model.addAttribute("comments", comments);
        return "comment/list";
    }

    @GetMapping("/new")
    public String createForm(@PathVariable Long blogId, Model model) {
        Comment comment = new Comment();
        // comment.setBlogId(blogId);
        model.addAttribute("comment", comment);
        return "comment/form";
    }

    @PostMapping("/create")
    public String create(@PathVariable Long blogId, @ModelAttribute Comment comment) {
        // comment.setBlogId(blogId);
        commentService.save(comment);
        return "redirect:/blogs/" + blogId + "/comments";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long blogId, @PathVariable Long id, Model model) {
        Comment comment = commentService.findById(id);
        model.addAttribute("comment", comment);
        return "comment/form";
    }

    @PostMapping("/{id}/edit")
    public String update(@PathVariable Long blogId, @PathVariable Long id, @ModelAttribute Comment comment) {
        comment.setId(id);
        // comment.setBlogId(blogId);
        commentService.update(comment);
        return "redirect:/blogs/" + blogId + "/comments";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long blogId, @PathVariable Long id) {
        commentService.delete(id);
        return "redirect:/blogs/" + blogId + "/comments";
    }
}
