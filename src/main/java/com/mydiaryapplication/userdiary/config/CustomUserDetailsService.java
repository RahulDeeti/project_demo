package com.mydiaryapplication.userdiary.config;

import com.mydiaryapplication.userdiary.entity.User;

import com.mydiaryapplication.userdiary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService
{
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
       User user = userService.searchForUser(userId);

       if(user == null)
       {
           throw new UsernameNotFoundException("User not found");
       }
        return new CustomUserDetails(user);
    }
}
