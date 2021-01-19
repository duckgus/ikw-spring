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
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.swing.*;
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
    public String signin(Duck_User user, Model model,HttpSession session) {
        System.out.println("로그인");
        System.out.println("username : " + user.getUsername());
        System.out.println("password : " +user.getPassword());
        System.out.println("session : " + session.getAttribute(user.getUsername()));
        System.out.println("[Controller user]"+user);
        Optional<Duck_User> duck = duckService.login(user);
        if (duck.isPresent()) {
            session.setAttribute("username", user.getUsername());
            System.out.println("로그인 성공");
            return "/main/index";
//            return "/main/index";

                                                  }else {
            System.out.println("로그인 실패");
            ModelAndView mav = new ModelAndView();
            mav.addObject("msg","fail");
//            model.addAttribute("msg", "메시지");
/*            JOptionPane.showMessageDialog(null,
                    "로그인실패","제목",
                    JOptionPane.WARNING_MESSAGE);*/

            return "/login";
        }


/*        if(user.isPresent()){
            httpSession.setAttribute("DuckSession", user.get());
            return "ok";
        }else{
            return "false";
        }*/
    }

    /*회원가입*/
    @GetMapping("signup")
    public String SignUpPage() {
        System.out.println("회원가입 페이지");
        return "signup";
    }
    @PostMapping("signup")
    public String Signup(Duck_User user) {
        user.getUsername();
        user.getPassword();
        System.out.println(user);

        duckService.join(user);
        System.out.println("회원가입");
        return "redirect:/";
    }

    @GetMapping("logout")
    public String logout(HttpSession session) {
        System.out.println("로그아웃");
        session.invalidate();
        return "index";
    }

}
