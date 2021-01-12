package univa.univa2.forum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import univa.univa2.forum.domain.ForumUser;
import univa.univa2.forum.service.ForumService;

@Controller
@RequestMapping("/forum")
public class ForumController {

    private final ForumService forumService;
    @Autowired
    public ForumController(ForumService forumService) {
        this.forumService = forumService;
    }
    @GetMapping("")
    public String home() {
        return "index";
    }

    @GetMapping("/signup")
    public String ForumSignupPage() {
        return "/account/signup";
    }
    @PostMapping("/signup")
    public String SignUp(UserForm userForm) {
        ForumUser forumUser = new ForumUser();
        forumUser.setUsername(userForm.getUsername());
        forumUser.setPassword(userForm.getPassword());
        forumUser.setNickname(userForm.getNickname());

        forumService.SignUp(forumUser);
        return "redirect:/forum";
    }
    @GetMapping("/signin")
    public String ForumSigninPage() {
        return "/account/signin";
    }
}
