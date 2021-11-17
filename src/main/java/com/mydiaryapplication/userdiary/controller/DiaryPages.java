package com.mydiaryapplication.userdiary.controller;

import com.mydiaryapplication.userdiary.entity.Diary;
import com.mydiaryapplication.userdiary.service.DiaryService;
import com.mydiaryapplication.userdiary.userdata.DiaryData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Date;

@Controller
@RequestMapping("/diarypage")
public class DiaryPages {

    @Autowired
    DiaryService diaryService;

    @GetMapping("/writediary")
    public String writeDiary(Model theModel)
    {

        //Create Diary DTO Object
        DiaryData diaryData = new DiaryData();

        theModel.addAttribute("diaryData",diaryData);

        return "creatediary";
    }

    @GetMapping("/readdiary")
    public String readDiary(Model theModel)
    {
        DiaryData diaryData = new DiaryData();
        theModel.addAttribute("diaryData", diaryData);

        return "readdiary";
    }
    @GetMapping("/updatediary")
    public String updateDiary(@ModelAttribute DiaryData diaryData, Model theModel)
    {
        return "updatediary";
    }

    @PostMapping("/updatediary")
    public String postUpdateDiary(@ModelAttribute DiaryData diaryData, Model theModel)
    {
        //Extract the userId
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        UserDetails userDetails = (UserDetails) auth.getPrincipal();

        String userId = userDetails.getUsername();

        Date date = diaryData.getOnDay();

        Diary diary = diaryService.findByDate(userId, date);

        if(diary != null)
        {
            diaryData.setDiaryNote(diary.getEntry());
        }
        else
        {
            diaryData.setDiaryNote("null");
        }
        return "updatediary";
    }
    @GetMapping("/deletediary")
    public String deleteDiary(Model theModel)
    {
        DiaryData diaryData = new DiaryData();
        theModel.addAttribute("diaryData", diaryData);
        return "deletediary";
    }

}
