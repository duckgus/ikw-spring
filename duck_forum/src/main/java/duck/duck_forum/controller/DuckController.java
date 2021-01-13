package duck.duck_forum.controller;

import duck.duck_forum.domain.Duck_User;
import duck.duck_forum.dto.DuckUserDto;
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

    @GetMapping("")
    public String indexPage() {
        return "index";
    }

    @GetMapping("/sign/signin")
    public String signinPage() {
        return "/sign/signin";
    }

    @PostMapping("/sign/signin")
//    @ResponseBody
    public String signin(DuckUserDto duckUserDto, Model model, HttpSession session){
        Optional<DuckUserDto> user = duckService.Signin(duckUserDto);
        if (user.isPresent()) {
//            session.setAttribute("Duck_UserSession", user.get());
            return "/index";

        }else{
            return null;
        }
    }

    @GetMapping("/sign/signup")
    public String signupPage() {
        return "sign/signup";
    }

    @PostMapping("/sign/signup")
    public String signup(DuckForm form) {
        Duck_User dUser = new Duck_User();
        dUser.setUsername(form.getUsername());
        dUser.setPassword(form.getPassword());
        dUser.setNickname(form.getNickname());
        dUser.setEmail(form.getEmail());
        dUser.setNation(form.getNation());

        duckService.signup(dUser);
        return "sign/signin";
    }
}

