package com.example.demo.controller;

import com.example.demo.model.Guest;
import com.example.demo.service.GuestService;
import com.example.demo.service.SecurityService;
import com.example.demo.validator.GuestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class GuestController {
    @Autowired
    private GuestService guestService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private GuestValidator guestValidator;

    @GetMapping("/registration")
    public String registration(Model model) {
//        User user = new User();
        model.addAttribute("user", new Guest());

        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("user") Guest guestForm, BindingResult bindingResult) {
        guestValidator.validate(guestForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        guestService.save(guestForm);

        securityService.autoLogin(guestForm.getUsername(), guestForm.getPasswordConfirm());

        return "redirect:/hotels";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }

}