package com.mydiaryapplication.userdiary;

import com.mydiaryapplication.userdiary.dao.DiaryDaoImpl;
import com.mydiaryapplication.userdiary.entity.Diary;
import com.mydiaryapplication.userdiary.entity.User;
import com.mydiaryapplication.userdiary.service.DiaryServiceImpl;

import org.junit.Before;
import org.junit.Test;
import  static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.runner.RunWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DiaryServiceTest
{
    @Mock
    DiaryDaoImpl diaryDaoImpl;

    @InjectMocks
    DiaryServiceImpl diaryServiceImpl;

    List<Diary> diaryList = new ArrayList<>();
    List<User> userList = new ArrayList<>();

    @Before
    public void setup()
    {
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();

        Diary diary1 = new Diary();

        diary1.setEntry("Hello");
        diary1.setDate(new Date(System.currentTimeMillis()));
        diary1.setUser(user1);

        Diary diary2 = new Diary();

        diary2.setEntry("Hello");
        diary2.setDate(new Date(System.currentTimeMillis()));
        diary2.setUser(user2);

        Diary diary3 = new Diary();
        diary3.setEntry("Hello");
        diary3.setDate(new Date(System.currentTimeMillis()));
        diary3.setUser(user3);

        diaryList.add(diary1);
        diaryList.add(diary2);
        diaryList.add(diary3);

        userList.add(user1);
        userList.add(user2);
        userList.add(user3);

    }

    @Test
    public void testSave()
    {
        Diary diary = diaryList.get(0);
        diaryServiceImpl.save("Rahul@12", diary);
        verify(diaryDaoImpl, times(1)).save("Rahul@12",diary);
    }

    @Test
    public void testDeleteByDate()
    {
        Date date = new Date(System.currentTimeMillis());
        Diary diary = diaryList.get(0);
        diaryServiceImpl.deleteByDate(diary);
        verify(diaryDaoImpl, times(1)).deleteByDate(diary);
    }

    @Test
    public void testFindByDate()
    {
        Date date = new Date(System.currentTimeMillis());
        diaryServiceImpl.findByDate("Ranjan@12",date);
        when(diaryDaoImpl.findByDate("Ranjan@12", date)).thenReturn(diaryList.get(0));
        assertNotNull(diaryServiceImpl.findByDate("Ranjan@12",date));

    }

    @Test
    public void testUpdateByDate()
    {
        Date date = new Date(System.currentTimeMillis());
        diaryServiceImpl.updateByDate("Ranjan@12","Hello", date);
        verify(diaryDaoImpl, times(1)).updateByDate("Ranjan@12","Hello",date);
    }
}
