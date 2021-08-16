package com.example.quickrepair.domain;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Objects;

public class User
{
    private int uid;
    private String name;
    private String surname;
    private String phoneNumber;
    private String email;
    private String bankAccount;
    private String username;
    private String password;
    private NotificationMethod notificationMethod;

    public User()
    {
        uid = 0;
    }

    public User(String name, String surname, String phoneNumber, String email, String bankAccount, String username, String password)
    {
        this();
        setUserInfo(name, surname, phoneNumber, email, bankAccount, username);
        setPassword(password);
    }

    /**
     * Sets the user's personal info performing the necessary checks
     *
     * @throws IllegalArgumentException if the input info is not correct
     */
    public void setUserInfo(String name, String surname, String phoneNumber, String email, String bankAccount, String username)
    {
        if (name == null) throw new NullPointerException("Name can not be null.");
        if (name.isEmpty()) throw new IllegalArgumentException("Name can not empty.");

        if (surname == null) throw new NullPointerException("Surname can not be null.");
        if (surname.isEmpty()) throw new IllegalArgumentException("Surname can not empty.");

        if (phoneNumber == null) throw new NullPointerException("Phone number can not be null.");
        if (!phoneNumber.matches("\\d{10}")) throw new IllegalArgumentException("Wrong phone number format. Must have exactly ten digits.");

        if (email == null) throw new NullPointerException("Email can not be null.");
        if (!email.matches("((\\w|\\d|\\.)+\\@(\\w|\\d)+\\.\\w+)"))throw new IllegalArgumentException("Wrong email address format.");

        if (bankAccount == null) throw new NullPointerException("Bank number can not be null.");
        if (bankAccount.length() != 22) throw new IllegalArgumentException("Wrong bank account format. Must have length 22.");

        if (username == null) throw new NullPointerException("Username can not be null.");
        if (username.isEmpty()) throw new IllegalArgumentException("Username can not be empty.");

        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.bankAccount = bankAccount;
        this.username = username;
    }

    public void setPassword(String password)
    {
        if (password == null) throw new NullPointerException("Password can not be null.");

        if (password.isEmpty()) throw new IllegalArgumentException("Username can not be empty.");

        this.password = password;
    }


    public String getName()
    {
        return name;
    }

    public String getSurname()
    {
        return surname;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public String getEmail()
    {
        return email;
    }

    public String getBankAccount()
    {
        return bankAccount;
    }

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    public int getUid()
    {
        return uid;
    }

    public void setUid(int uid)
    {
        this.uid = uid;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) &&
                Objects.equals(surname, user.surname) &&
                Objects.equals(phoneNumber, user.phoneNumber) &&
                Objects.equals(email, user.email) &&
                Objects.equals(bankAccount, user.bankAccount) &&
                Objects.equals(username, user.username) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode()
    {
        int result = Objects.hash(name, surname, phoneNumber, email, bankAccount, username);
        result = 31 * result + Objects.hashCode(password);
        return result;
    }

    public NotificationMethod getNotificationMethod()
    {
        return notificationMethod;
    }

    public void setNotificationMethod(NotificationMethod notificationMethod)
    {
        this.notificationMethod = notificationMethod;
    }

    public enum NotificationMethod
    {
        EMAIL,

        SMS
    }
}
