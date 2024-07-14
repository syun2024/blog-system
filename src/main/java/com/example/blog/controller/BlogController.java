package com.example.blog.controller;

import com.example.blog.entity.Blog;
import com.example.blog.entity.User;
import com.example.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/blogs")
public class BlogController {
    @Autowired
    private BlogService blogService;

    @GetMapping
    public String list(Model model) {
        List<Blog> blogs = blogService.findAll();
        model.addAttribute("blogs", blogs);
        return "blog/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        Blog blog = blogService.findById(id);
        model.addAttribute("blog", blog);
        return "blog/detail";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("blog", new Blog());
        return "blog/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute Blog blog, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "blog/form";
        }
        User loginUser = (User) session.getAttribute("loginUser");
        blog.setUser(loginUser);
        System.out.println(blog.getUser().getId());
        blogService.save(blog);
        return "redirect:/blogs";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Integer id, Model model) {
        Blog blog = blogService.findById(id);
        model.addAttribute("blog", blog);
        return "blog/form";
    }

    @PostMapping("/{id}/edit")
    public String update(@PathVariable Integer id, @Valid @ModelAttribute Blog blog, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "blog/form";
        }
        blog.setId(id);
        blogService.update(blog);
        return "redirect:/blogs";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Integer id) {
        blogService.delete(id);
        return "redirect:/blogs";
    }
}