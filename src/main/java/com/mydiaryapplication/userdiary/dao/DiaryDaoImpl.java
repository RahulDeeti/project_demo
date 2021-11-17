package com.mydiaryapplication.userdiary.dao;

import com.mydiaryapplication.userdiary.entity.Diary;
import com.mydiaryapplication.userdiary.entity.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.sql.Date;

@Repository
public class DiaryDaoImpl implements DiaryDao{

    @Autowired
    private EntityManager entityManager;

    @Override
    public void save(String userId, Diary theDiary) {

        Session currentSession = entityManager.unwrap(Session.class);

        User user = currentSession.get(User.class, userId);

        theDiary.setUser(user);

        user.addDiary(theDiary);

    }

    @Override
    public void deleteByDate(Diary diary) {

        Session currentSession = entityManager.unwrap(Session.class);

        currentSession.delete(diary);

    }

    @Override
    public Diary findByDate(String userId,Date date) {
           Session currentSession = entityManager.unwrap(Session.class);

           String fetchDiaryEntry = "from Diary where user = :someId and date = :someDay";

           User userObj = currentSession.get(User.class, userId);

           Query query = currentSession.createQuery(fetchDiaryEntry, Diary.class);

           query.setParameter("someId", userObj);
           query.setParameter("someDay", date);

           Diary diary = (Diary)query.uniqueResult();

           return diary;
    }

    @Override
    public void updateByDate(String userId, String diaryEntry, Date date)
    {
        Session currentSession = entityManager.unwrap(Session.class);

        User userName = currentSession.get(User.class, userId);

        String updateQuery = "update Diary set entry = :updatedDiary where date = :someDay and user = :userName";

        Query query = currentSession.createQuery(updateQuery);

        query.setParameter("updatedDiary",diaryEntry);

        query.setParameter("someDay", date);

        query.setParameter("userName", userName);

        int status = query.executeUpdate();

        System.out.println(status);

    }


}
