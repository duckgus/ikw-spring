package com.univa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UnivaController {

    @GetMapping("")
    public String indexPage() {
        return "index";
    }
    @GetMapping("/signin")
    public String ForumSigninPage() {
        return "/account/signin";
    }
    @GetMapping("/signup")
    public String ForumSignUpPage() {
        return "/account/signup";
    }
}
