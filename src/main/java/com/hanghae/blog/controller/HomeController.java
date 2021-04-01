package com.hanghae.blog.controller;

import com.hanghae.blog.security.UserDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails == null) {
            model.addAttribute("message", "null");
            return "index";
        } //else (userDetails != null){
        model.addAttribute("username", userDetails.getUsername());
        return "index";
        //}
    }

    @GetMapping("/articles")
    public String articledetail(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails == null) {
            model.addAttribute("message", "null");
            return "articledetail";
        } //else (userDetails != null) {
        model.addAttribute("username", userDetails.getUsername());
        return "articledetail";
        //}
    }

    @GetMapping("/error")
    public String errorpage(){
        return "forbidden";
    }

}
