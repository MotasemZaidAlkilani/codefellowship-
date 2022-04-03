package com.example.codeFellows.controllers;

import com.example.codeFellows.models.ApplicationUser;
import com.example.codeFellows.models.post;
import com.example.codeFellows.repositries.postRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.codeFellows.repositries.userRepositry;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;

@Controller
public class userController {
    Logger logger = Logger.getLogger(userController.class.getName());
    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private  final userRepositry userRepositry;
    public userController(userRepositry userRepositry, com.example.codeFellows.repositries.postRepositry postRepositry) {
        this.userRepositry = userRepositry;
        this.postRepositry = postRepositry;
    }
    @GetMapping("/")
    public String getHomePage(HttpServletRequest request, Model model){

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            ApplicationUser user = userRepositry.findByUsername(authentication.getName());
            model.addAttribute("username", user.getUsername());

     return "index";
    }
    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public String getUsersPage(@PathVariable int id,Model model){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        ApplicationUser I=userRepositry.findByUsername(authentication.getName());
        model.addAttribute("username",I.getUsername());
        if(userRepositry.existsById(id)) {
            ApplicationUser user = userRepositry.findById(id);
            model.addAttribute("postsList",user.getPosts());

            model.addAttribute("firstName",user.getFirstName());
            model.addAttribute("lastName",user.getLastName());
            model.addAttribute("dateOfBirth",user.getDateOfBirth());
            model.addAttribute("bio",user.getBio());
            model.addAttribute("errorMessage","");

        }
        else{
            model.addAttribute("errorMessage","this id not exist");

        }

return  "/users";
    }
    @GetMapping("/users")
    public String getUserPage(Model model){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        ApplicationUser I=userRepositry.findByUsername(authentication.getName());
        model.addAttribute( "username", I.getUsername());

        return "users";
    }
    public void fdf(){

    }
    @GetMapping("/login")
    public String getLoginPage(){

        return "login";
    }
    @GetMapping("/signup")
    public String getSignupPage() {

        return "signup";
    }
    @GetMapping("/myprofile")
    public String getProfilePage(HttpServletRequest request, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ApplicationUser user = userRepositry.findByUsername(authentication.getName());
        model.addAttribute("postsList",user.getPosts());
        model.addAttribute("username", user.getUsername());
        model.addAttribute("firstName", user.getFirstName());
        model.addAttribute("lastName", user.getLastName());
        model.addAttribute("dateOfBirth", user.getDateOfBirth());
        model.addAttribute("bio", user.getBio());
        return "myprofile";

    }

    @PostMapping("/signup")
    public String signupUser(@RequestParam String username, @RequestParam String password,
                             @RequestParam String firstName,@RequestParam String lastName,
                             @RequestParam String dateOfBirth,@RequestParam String bio){
        ApplicationUser user = new ApplicationUser(username,encoder.encode(password),firstName,lastName,dateOfBirth,bio);
        userRepositry.save(user);
        return "login";
    }
    private final postRepositry postRepositry;
    @PostMapping("/newpost")
    public String createAppUser(@RequestParam String body,
                                      @RequestParam String createdAt,
                                      Model model) {
        if(body!=""){
        post newPost = new post(body,createdAt);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try {
            newPost.setUser(userRepositry.findByUsername(authentication.getName()));
            String doneMessage = "added to your profile";
            model.addAttribute("message", doneMessage);

            postRepositry.save(newPost);
        }catch (Exception e) {
            String ErrorMessage = "Error";
            model.addAttribute("message", ErrorMessage);
        }

        }else{
            String emptyMessage = "please enter something";
            model.addAttribute("message", emptyMessage);
        }
        return "index";
    }
}
