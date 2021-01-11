package univa.univa2.forum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import univa.univa2.forum.dto.ForumUserDTO;
import univa.univa2.forum.service.ForumService;

@Controller
@RequestMapping(value = "/forum")
public class ForumController {
    private final ForumService forumService;

    @Autowired
    public ForumController(ForumService forumService) {
        this.forumService = forumService;
    }

    @GetMapping("")
    public String indexPage() {
        return "index";
    }
    @GetMapping("/signup")
    public String ForumSignupPage() {
        return "/account/signup";
    }
    @PostMapping("/signup")
    @ResponseBody
    public String ForumSignupPost(
            ForumUserDTO forumUser,
            Model model) {
        return forumService.userSignup(forumUser);
    }

    @GetMapping("/signin")
    public String ForumSigninPage() {
        return "/account/signin";
    }
}
