package com.example.labb.demo.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.Map;

@Controller
public class LoginController {

    @GetMapping("/")
    public String greeting(Model model, Principal loggedInUser, Authentication authentication){
        OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken)authentication;
        OAuth2User oAuth2User = oAuth2AuthenticationToken.getPrincipal();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        model.addAttribute("nameprincipal", loggedInUser);
        model.addAttribute("nameauth", authentication);
        model.addAttribute("username", attributes.get("login"));
        model.addAttribute("avatar_url", attributes.get("avatar_url"));
        model.addAttribute("html_url", attributes.get("html_url"));
        return "newsview";

    }

}
