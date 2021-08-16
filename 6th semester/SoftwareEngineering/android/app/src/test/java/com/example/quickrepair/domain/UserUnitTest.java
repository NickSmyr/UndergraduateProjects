package com.example.quickrepair.domain;

import com.example.quickrepair.domain.User;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserUnitTest
{

    User userToTest;

    @Before
    public void setUpTests()
    {
        userToTest = new User("nikos", "sm", "6958475635",
                "asdih@ausdh.asdh", "1234567890123456789012", "nikos", "123");
    }

    @Test
    public void gettersTest()
    {
        Assert.assertEquals("nikos", userToTest.getName());
        Assert.assertEquals("sm", userToTest.getSurname());
        Assert.assertEquals("6958475635", userToTest.getPhoneNumber());
        Assert.assertEquals("asdih@ausdh.asdh", userToTest.getEmail());
        Assert.assertEquals("1234567890123456789012", userToTest.getBankAccount());
        Assert.assertEquals("nikos", userToTest.getUsername());
        Assert.assertEquals("123", userToTest.getPassword());

    }

    @Test
    public void setCorrectUserInfo()
    {
        userToTest.setUserInfo("nikos", "sm", "6958475635",
                "asdih@ausdh.asdh", "1234567890123456789012", "nikos");
    }

    @Test(expected = IllegalArgumentException.class)
    public void setIncorrectEmail()
    {
        userToTest.setUserInfo("nikos", "sm", "6958475635",
                "asdihausdh.asdh", "1234567890123456789012", "nikos");
    }

    @Test
    public void setCorrectEmailWithNumbers()
    {
        userToTest.setUserInfo("nikos", "sm", "6958475635",
                "asdihausdh1999@asdd.asdh", "1234567890123456789012", "nikos");
    }

    @Test(expected = IllegalArgumentException.class)
    public void setIncorrectPhoneNumber()
    {
        userToTest.setUserInfo("nikos", "sm", ";DROP TABLE USERS",
                "asdihausdh.asdh", "mybankaccount", "nikos");
    }

    @Test
    public void uidTest()
    {
        userToTest.setUid(5);
        Assert.assertEquals(5, userToTest.getUid());
    }

    @Test
    public void memberMethods()
    {
        User u1 = new User("nikos", "sm", "6958475635",
                "asdih@ausdh.asdh", "1234567890123456789012", "nikos", "123");

        Assert.assertNotEquals(userToTest, null);
        Assert.assertEquals(userToTest, userToTest);
        Assert.assertNotEquals(userToTest, "test");

        Assert.assertTrue(userToTest.equals(u1) && u1.equals(userToTest));
        Assert.assertTrue(userToTest.hashCode() == u1.hashCode());

        u1 = new User("nikos", "sma", "6958475635",
                "asdih@ausdh.asdh", "1234567890123456789012", "nikos", "123");

        Assert.assertNotEquals(userToTest, u1);
    }
}
