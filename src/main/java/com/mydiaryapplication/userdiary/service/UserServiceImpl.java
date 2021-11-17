package com.mydiaryapplication.userdiary.service;

import com.mydiaryapplication.userdiary.dao.UserDAO;
import com.mydiaryapplication.userdiary.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserDAO userDAO;

    @Override
    @Transactional
    public void save(User user) {
        userDAO.save(user);
    }
    @Override
    @Transactional
    public User get(String userId)
    {
        return userDAO.get(userId);
    }

    @Override
    @Transactional
    public void delete(String userId)
    {
        userDAO.delete(userId);
    }

    @Override
    @Transactional
    public boolean searchForId(String userId) {
        return userDAO.searchForId(userId);
    }

    @Override
    @Transactional
    public boolean searchForMail(String userId, String mail) {
        return userDAO.searchForMail(userId, mail);
    }

    @Override
    @Transactional
    public User searchForUser(String userId) {
        return userDAO.searchForUser(userId);
    }

    @Override
    @Transactional
    public List<User> findAllUsers() {
        return userDAO.findAllUsers();
    }

}
