package com.mydiaryapplication.userdiary.service;

import com.mydiaryapplication.userdiary.dao.DiaryDao;
import com.mydiaryapplication.userdiary.entity.Diary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;

@Service
public class DiaryServiceImpl implements DiaryService{

    private DiaryDao diaryDao;

    @Autowired
    public DiaryServiceImpl(DiaryDao diaryDao) {
        this.diaryDao = diaryDao;
    }

    @Override
    @Transactional
    public void save(String userId,Diary theDiary) {
            diaryDao.save(userId,theDiary);
    }

    @Override
    @Transactional
    public void deleteByDate(Diary diary) {
        diaryDao.deleteByDate(diary);
    }

    @Override
    @Transactional
    public Diary findByDate(String userId, Date date) {
        return diaryDao.findByDate(userId, date);
    }

    @Override
    @Transactional
    public void updateByDate(String userId, String diaryEntry, Date date) {
            diaryDao.updateByDate(userId, diaryEntry, date);
    }


}
