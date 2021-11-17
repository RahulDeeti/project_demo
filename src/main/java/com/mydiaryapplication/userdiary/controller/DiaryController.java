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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Date;

@Controller
@RequestMapping("/api")
public class DiaryController {

    @Autowired
   private DiaryService diaryService;

    @PostMapping("/diaries")
    public String diaryMethod(@ModelAttribute DiaryData diaryData, Model theModel)
    {
        //Extract the userId
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        UserDetails userDetails = (UserDetails) auth.getPrincipal();

        String userId = userDetails.getUsername();

        long currentMilliSeconds = System.currentTimeMillis();

        Date today = new Date(currentMilliSeconds);

        diaryData.setOnDay(today);

        diaryData.setUserId(userId);

//        System.err.println(diaryData.getUserId());
//        System.err.println(diaryData.getOnDay());
//        System.err.println(diaryData.getDiaryNote());
        Diary tempDiary = diaryService.findByDate(userId, today);

          if( (tempDiary == null) && (!diaryData.getDiaryNote().isBlank()))
          {
              //Transfer values from DiaryDTO to Diary entity
              Diary diary = new Diary();

              //I am here right now
              diary.setDate(diaryData.getOnDay());

              diary.setEntry(diaryData.getDiaryNote());

              //save to database
              diaryService.save(userId, diary);

              theModel.addAttribute("createStatus", "success");
              return "createsuccess";
          }
          else
          {
                theModel.addAttribute("createStatus", "failure");

                return "createsuccess";
          }
    }
    @PostMapping("/readdiary")
    public String readDiary(@ModelAttribute DiaryData diaryData)
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
            diaryData.setDiaryNote("Oops! There is no entry on given date");
        }


        return "readdiary";
    }
    @PostMapping("/updatediary")
    public String updateDiary(@ModelAttribute DiaryData diaryData, Model theModel)
    {

//        System.err.println("********inside update diary**********");
//        System.err.println(diaryData.getDiaryNote());
//        System.err.println(diaryData.getOnDay());
//        System.err.println(diaryData.getUserId());

        String entry = diaryData.getDiaryNote().trim();
        if(!entry.equals("null"))
        {
            //Extract the userId
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            UserDetails userDetails = (UserDetails)auth.getPrincipal();

            String userId = userDetails.getUsername();

            Date date = diaryData.getOnDay();

            String diaryEntry = diaryData.getDiaryNote();

            System.err.println("Inside update diaryController");
            diaryService.updateByDate(userId, diaryEntry, date);
            theModel.addAttribute("updateStatus","success");
        }
        else
        {
            theModel.addAttribute("updateStatus", "failure");
        }
        return "updatesuccess";
    }

    @PostMapping("/deletediary")
    public String deleteDiary(@ModelAttribute DiaryData diaryData, Model theModel)
    {
        Date date = diaryData.getOnDay();
        if(date != null)
        {
            //Extract the userId
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            UserDetails userDetails = (UserDetails) auth.getPrincipal();

            String userId = userDetails.getUsername();

            Diary diary = diaryService.findByDate(userId, date);

            if(diary != null)
            {
                diaryService.deleteByDate(diary);
                theModel.addAttribute("deleteStatus","success");
            }
            else
            {
                theModel.addAttribute("deleteStatus","failure");
            }

        }
        else
        {
            theModel.addAttribute("deleteStatus", "failure");
        }
        return "deletesuccess";
    }

}
