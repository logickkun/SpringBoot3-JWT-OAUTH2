package me.choihyunmyung.springbootdeveloper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserViewController {

    // 로그인 View
    @GetMapping("/login")
    public String login() {
        return "oauthLogin";
    }

    // 회원가입 View
    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }
}
