package com.example.demo.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserAuthenticatorController {

    @GetMapping("/helper/username")
    @ResponseBody
    public String getUsername(Authentication authentication){
        if(authentication instanceof AnonymousAuthenticationToken)
            return "anon";
        else
            return authentication.getName();
    }
}
