package com.univa.controller;

import com.univa.domain.UnivaUser;
import com.univa.dto.univaUserDTO;
import com.univa.service.UnivaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class UnivaController {
    private final UnivaService univaService;

    @Autowired
    public UnivaController(UnivaService univaService) {
        this.univaService = univaService;
    }

    @GetMapping("")
    public String indexPage() {
        return "index";
    }



    @GetMapping("/signin")
    public String UnivaSigninPage() {
        return "/account/signin";
    }
    @GetMapping("/signup")
    public String UnivaSignUpPage() {
        return "/account/signup";
    }

/*    @PostMapping("/signin")
    public String UnivaSigninPost(univaUserDTO univaUser, Model model, HttpSession session) {
        Optional<univaUserDTO> user = univaService.userSignin()
    }*/

    @PostMapping("/signup")
    @ResponseBody
    public String UnivaSignUpPost(univaUserDTO univaUser, Model model) {
        return univaService.userSignUp(univaUser);
    }

}
