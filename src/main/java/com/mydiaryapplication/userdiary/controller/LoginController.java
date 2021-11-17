package com.mydiaryapplication.userdiary.controller;

import com.mydiaryapplication.userdiary.config.CustomUserDetails;
import com.mydiaryapplication.userdiary.entity.User;
import com.mydiaryapplication.userdiary.service.UserService;
import com.mydiaryapplication.userdiary.userdata.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Controller
public class LoginController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home()
    {
        return "Index";
    }
    @GetMapping("/login")
    public String login()
    {
        return "login";
    }
    @GetMapping("/register")
    public String register(Model model)
    {
        model.addAttribute("userData", new UserData());
        return "registration";
    }
    @GetMapping("/user")
    public String userHome(Model theModel)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //CustomUserDetails userDetails = (CustomUserDetails)auth.getPrincipal();

        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String userId = userDetails.getUsername();
        System.out.println(userId);
        theModel.addAttribute("userId",userId);
        return "userpage";
    }

    @PostMapping("/register")
    public String userRegistration(@Valid UserData userData, BindingResult bindingResult, Model model){
        String userId = userData.getUserId();
        String email = userData.getEmail();

        if(bindingResult.hasErrors()){
            model.addAttribute("registrationForm", userData);
            return "registration";
        }
        else if(!userService.searchForId(userId))
        {
            model.addAttribute("message","idUsed");
            return "registration";
        }
        else if(!userService.searchForMail(userId, email))
        {
            model.addAttribute("message","emailUsed");
            return "registration";
        }
        else
        {
            User user = new User();
            user.setUserName(userData.getUserId());
            user.setFirstName(userData.getFirstName());
            user.setLastName(userData.getLastName());
            user.setEmail(userData.getEmail());
            user.setPassword(passwordEncoder.encode(userData.getPassword()));

            userService.save(user);


            model.addAttribute("message","Success");
        }
        return "registration";
    }
}
