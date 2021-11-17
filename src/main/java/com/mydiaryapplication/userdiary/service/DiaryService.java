package com.mydiaryapplication.userdiary.service;


import com.mydiaryapplication.userdiary.entity.Diary;

import java.sql.Date;

public interface DiaryService {

    public void save(String userId, Diary theDiary);

    public void deleteByDate(Diary diary);

    public Diary findByDate(String userId, Date date);

    public void updateByDate(String userId, String diaryEntry, Date date);

}
