package com.cupfeedeal.domain.Auth.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/v1/auth/login")
public class KakaoController {

    @Value("${CLIENT_ID}")
    private String client_id;

    @Value("${REDIRECT_URI}")
    private String redirect_uri;


    @GetMapping("/page")
    public String loginPage(Model model) {
        String location = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=" + client_id + "&redirect_uri=" + redirect_uri;
        model.addAttribute("location", location);

        return "login";
    }
}