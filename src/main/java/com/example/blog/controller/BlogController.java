package com.example.blog.controller;

import com.example.blog.entity.Blog;
import com.example.blog.entity.Category;
import com.example.blog.entity.Comment;
import com.example.blog.entity.User;
import com.example.blog.service.BlogService;
import com.example.blog.service.CategoryService;
import com.example.blog.service.CommentService;
import com.example.blog.exception.DatabaseException;
import com.example.blog.exception.DuplicateDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/blogs")
public class BlogController {
    @Autowired
    private BlogService blogService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CommentService commentService;

    @GetMapping
    public String list(Model model) {
        List<Blog> blogs = blogService.findAll();
        model.addAttribute("blogs", blogs);
        return "blog/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        Blog blog = blogService.findById(id);
        List<Comment> comments = commentService.findByBlogId(id);
        blog.setComments(comments);
        model.addAttribute("blog", blog);

        if (!model.containsAttribute("comment")) {
            model.addAttribute("comment", new Comment());
        }

        return "blog/detail";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        if (!model.containsAttribute("blog")) {
            model.addAttribute("blog", new Blog());
        }

        model.addAttribute("categories", categoryService.findAll());
        return "blog/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute Blog blog, BindingResult bindingResult, HttpSession session,
            Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.blog",
                    bindingResult);
            redirectAttributes.addFlashAttribute("blog", blog);
            return "redirect:/blogs/new";
        }

        User loginUser = (User) session.getAttribute("loginUser");
        blog.setUser(loginUser);

        try {
            blogService.createBlog(blog);
        } catch (DuplicateDataException e) {
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.blog",
                    bindingResult);
            redirectAttributes.addFlashAttribute("blog", blog);
            redirectAttributes.addFlashAttribute("titleError", e.getMessage());
            return "redirect:/blogs/new";
        } catch (DatabaseException e) {
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.blog",
                    bindingResult);
            redirectAttributes.addFlashAttribute("blog", blog);
            redirectAttributes.addFlashAttribute("databaseError", e.getMessage());
            return "redirect:/blogs/new";
        }

        return "redirect:/blogs";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Integer id, Model model) {
        Blog blog = blogService.findById(id);
        List<Category> categories = categoryService.findAll();
        model.addAttribute("blog", blog);
        model.addAttribute("categories", categories);
        return "blog/form";
    }

    @PostMapping("/{id}/edit")
    public String update(@PathVariable Integer id, @Valid @ModelAttribute Blog blog, BindingResult bindingResult,
            HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.blog", bindingResult);
            redirectAttributes.addFlashAttribute("blog", blog);
            return "redirect:/blogs/" + id + "/edit";
        }

        User loginUser = (User) session.getAttribute("loginUser");
        blog.setUser(loginUser);

        try {
            blogService.updateBlog(id, blog);
        } catch (DuplicateDataException e) {
            redirectAttributes.addFlashAttribute("titleError", e.getMessage());
            redirectAttributes.addFlashAttribute("blog", blog);
            return "redirect:/blogs/" + id + "/edit";
        } catch (DatabaseException e) {
            redirectAttributes.addFlashAttribute("databaseError", e.getMessage());
            redirectAttributes.addFlashAttribute("blog", blog);
            return "redirect:/blogs/" + id + "/edit";
        }

        return "redirect:/blogs";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Integer id) {
        blogService.delete(id);
        return "redirect:/blogs";
    }
}
