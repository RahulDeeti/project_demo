package com.mydiaryapplication.userdiary.controller;

import com.mydiaryapplication.userdiary.entity.User;
import com.mydiaryapplication.userdiary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    UserService userService;

    @GetMapping("/admin")
    public String adminLogin(Model theModel)
    {
        List<User> userList = userService.findAllUsers();
        theModel.addAttribute("totalUsers", userList);
        return "adminpage";
    }

    @GetMapping("/admin/deleteuser")
    public String deleteUser(@RequestParam(name = "id") String userId, Model theModel)
    {

        userService.delete(userId);
        List<User> userList = userService.findAllUsers();
        theModel.addAttribute("totalUsers", userList);

        return "adminpage";
    }
}
