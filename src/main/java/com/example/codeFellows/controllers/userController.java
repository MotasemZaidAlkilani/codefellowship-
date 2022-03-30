package com.example.codeFellows.controllers;

import com.example.codeFellows.models.ApplicationUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.codeFellows.repositries.userRepositry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;

@Controller
public class userController {
    Logger logger = Logger.getLogger(userController.class.getName());
    @Autowired
    PasswordEncoder encoder;

    public userController(userRepositry userRepositry) {
        this.userRepositry = userRepositry;
    }
    @GetMapping("/")
    public String getHomePage(HttpServletRequest request, Model model){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        ApplicationUser user=userRepositry.findByUsername(authentication.getName());
        model.addAttribute("username",user.getUsername() );
        model.addAttribute("firstName",user.getFirstName() );
        model.addAttribute("lastName",user.getLastName() );
        model.addAttribute("bio",user.getBio() );
     return "index";
    }
    @GetMapping("/login")
    public String getLoginPage(){

        return "login";
    }
    @GetMapping("/signup")
    public String getSignupPage() {

        return "signup";
    }


    @Autowired
    private  final userRepositry userRepositry;
    @PostMapping("/signup")
    public String signupUser(@RequestParam String username, @RequestParam String password,
                             @RequestParam String firstName,@RequestParam String lastName,
                             @RequestParam String dateOfBirth,@RequestParam String bio){
        ApplicationUser user = new ApplicationUser(username,encoder.encode(password),firstName,lastName,dateOfBirth,bio);
        userRepositry.save(user);
        return "login";
    }
}
