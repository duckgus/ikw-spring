package duck.duck_forum.controller;

import duck.duck_forum.domain.DuckPost;
import duck.duck_forum.domain.Duck_User;
import duck.duck_forum.service.DuckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class DuckController {

    private final DuckService duckService;
    @Autowired
    public DuckController(DuckService duckService) {
        this.duckService = duckService;
    }

    @GetMapping("/")
    public String home() {
        System.out.println("index");
        return "index";
    }
    /*로그인*/
    @GetMapping("signin")
    public String Login() {
        System.out.println("로그인 페이지");
        return "login";
    }
    @PostMapping("signin")
    public String signin(DuckPost duckPost, Model model, HttpSession httpSession) {
        System.out.println("로그인");
        Optional<DuckPost> user = duckService.login(duckPost);
        if(user.isPresent()){
            httpSession.setAttribute("DuckSession", user.get());
            return "ok";
        }else{
            return "false";
        }
//        System.out.println("로그인");

//        return "/main/index";
    }

    /*회원가입*/
    @GetMapping("signup")
    public String SignUpPage() {
        System.out.println("회원가입 페이지");
        return "signup";
    }
    @PostMapping("signup")
    public String Signup(DuckPost duckPost) {
        Duck_User user = new Duck_User();
        user.setUsername(duckPost.getUsername());
        user.setPassword(duckPost.getPassword());

        duckService.join(user);
        System.out.println("회원가입");
        return "redirect:/";
    }

    @GetMapping("logout")
    public String logout() {
        System.out.println("로그아웃");
        return "index";
    }

}
