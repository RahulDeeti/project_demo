package com.mydiaryapplication.userdiary;


import com.mydiaryapplication.userdiary.dao.UserDAOImpl;
import com.mydiaryapplication.userdiary.entity.User;

import com.mydiaryapplication.userdiary.service.UserServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    UserDAOImpl userDAOImpl;

    @InjectMocks
    UserServiceImpl userServiceImpl;

    private List<User> users;

    @Before
    public void setup()
    {
        users = new ArrayList<>();

        User userOne = new User();
        userOne.setFirstName("Rahul");
        userOne.setLastName("Deeti");
        userOne.setEmail("rahuld@gmail.com");
        userOne.setUserName("Rahul@12");
        userOne.setPassword("rahul");

        User userTwo = new User();
        userTwo.setFirstName("Vinay");
        userTwo.setLastName("Verma");
        userTwo.setEmail("vinayv@gmail.com");
        userTwo.setUserName("Vinay@12");
        userTwo.setPassword("vinay");

        User userThree = new User();
        userThree.setFirstName("Ramesh");
        userThree.setLastName("Kona");
        userThree.setEmail("rameshk@gmail.com");
        userThree.setUserName("Ramesh@12");
        userThree.setPassword("ramesh");

        users.add(userOne);
        users.add(userTwo);
        users.add(userThree);
    }

    @Test
    public void testGetAllUsers()
    {
        User user;
        //return mocked user list
        when(userDAOImpl.findAllUsers()).thenReturn(users);

        //test
        List<User> userList = userServiceImpl.findAllUsers();

        assertEquals(3, users.size());

        verify(userDAOImpl, times(1)).findAllUsers();

        assertSame(users, userList);

        assertSame(users.get(0), userList.get(0));
        assertSame(users.get(1), userList.get(1));
        assertSame(users.get(2), userList.get(2));
    }

    @Test
    public void testSearchForId()
    {
//        System.out.println(users);
        User user = users.get(0);
        String userId = user.getUserName();

        when(userDAOImpl.get(userId)).thenReturn(user);

        assertNotNull(userServiceImpl.get(userId));

        assertEquals(user.getUserName(), userServiceImpl.get(userId).getUserName());
        assertEquals(user.getEmail(), userServiceImpl.get(userId).getEmail());
        assertEquals(user.getFirstName(), userServiceImpl.get(userId).getFirstName());
        assertEquals(user.getLastName(), userServiceImpl.get(userId).getLastName());

        when(userDAOImpl.get("Ranjan@12")).thenReturn(null);

        User user1 = userServiceImpl.get("Ranjan@12");

        assertNull(user1);
    }

    @Test
    public void testSave() //to test whether single user saved or not
    {
        User user = users.get(1);

        userServiceImpl.save(user);

        verify(userDAOImpl, times(1)).save(user);
    }

    @Test
    public void testGet()
    {
        User user = users.get(0);
        when(userDAOImpl.get("Rahul@12")).thenReturn(user);
        assertNotNull(userServiceImpl.get("Rahul@12"));

        when(userDAOImpl.get("Ranjan@12")).thenReturn(null);

        assertNull(userServiceImpl.get("Ranjan@12"));

    }

    @Test
    public void testSearchForMail()
    {
        String userId = "Rahul@12";
        String email = "rahuld@gmail.com";
        User user = users.get(0);
        when(userDAOImpl.searchForMail(userId, email)).thenReturn(true);
        assertTrue(userServiceImpl.searchForMail(userId, email));

        userId = "Ranjan@12";
        email = "ranjan@gmail.com";

        when(userDAOImpl.searchForMail(userId, email)).thenReturn(false);

        assertFalse(userServiceImpl.searchForMail(userId, email));


    }

    @Test
    public  void testDelete()
    {
        String userId = users.get(1).getUserName();

        userServiceImpl.delete(userId);

        verify(userDAOImpl, times(1)).delete(userId);
    }

    @Test
    public void testSearchForUser()
    {
        String userId = users.get(0).getUserName();

        when(userDAOImpl.searchForUser(userId)).thenReturn(users.get(0));

        assertNotNull(userServiceImpl.searchForUser(userId));

        userId = "Ranjan@12";
        when(userDAOImpl.searchForUser(userId)).thenReturn(null);

        assertNull(userServiceImpl.searchForUser(userId));
    }

    @After
    public void closeAll()
    {
        users = null;
    }
}
