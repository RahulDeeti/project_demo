package com.mydiaryapplication.userdiary.dao;

import com.mydiaryapplication.userdiary.entity.Diary;

import java.sql.Date;


public interface DiaryDao {

    public void save(String userId,Diary theDiary);

    public void deleteByDate(Diary diary);

    public Diary findByDate(String userId, Date date);

    public void updateByDate(String userId, String diaryEntry,Date date);

}
