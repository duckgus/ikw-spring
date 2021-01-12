package univa.forum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import univa.forum.dto.ForumUserDTO;
import univa.forum.service.ForumService;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/forum")
public class ForumController {
    private final ForumService forumService;

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
    @PostMapping("/signin")
    @ResponseBody
    public String ForumSigninPost(
            ForumUserDTO forumUser,
            Model model,
            HttpSession session) {
        Optional<ForumUserDTO> user = forumService.userSignin(forumUser);
        if(user.isPresent()) {
            session.setAttribute("ForumUserSession", user.get());
            return "ok";
        } else {
            return "error";
        }
    }
}
