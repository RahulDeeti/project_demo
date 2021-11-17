package com.mydiaryapplication.userdiary.dao;

import com.mydiaryapplication.userdiary.entity.User;

import java.util.List;

public interface UserDAO {
    public void save(User user);

    public User get(String userId);

    public void delete(String userId);

    public boolean searchForId(String userId);

    public boolean searchForMail(String userId, String mailId);

    public User searchForUser(String userId);

    public List<User> findAllUsers();

}
