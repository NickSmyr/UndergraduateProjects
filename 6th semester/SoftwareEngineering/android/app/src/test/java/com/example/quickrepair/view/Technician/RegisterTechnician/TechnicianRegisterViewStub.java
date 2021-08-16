package com.example.quickrepair.view.Technician.RegisterTechnician;

import java.util.ArrayList;
import java.util.List;

public class TechnicianRegisterViewStub implements TechnicianRegisterView
{
    private String name;
    private String surname;
    private String phoneNumber;
    private String accountNumber;
    private String email;
    private String AFM;
    private String username;
    private String password;
    private int technicianID;
    private int specialityID;
    private int notificationMethodID;
    private ArrayList<String> specialities;
    private String errorTitle;
    private String errorMessage;

    @Override
    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public void setSurname(String surname)
    {
        this.surname = surname;
    }

    @Override
    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public void setEmail(String email)
    {
        this.email = email;
    }

    @Override
    public void setAccountNumber(String accountNumber)
    {
        this.accountNumber = accountNumber;
    }

    @Override
    public void setAFM(String AFM)
    {
        this.AFM = AFM;
    }

    @Override
    public void setUsername(String username)
    {
        this.username = username;
    }

    @Override
    public void setPassword(String password)
    {
        this.password = password;
    }

    @Override
    public void setSpecialityID(int specialityID)
    {
        this.specialityID = specialityID;
    }

    /**
     * Set the notification method of the user trying to register or edit.
     *
     * @param notificationMethodID The notification method id of the technician.
     */
    @Override
    public void setNotificationMethodID(int notificationMethodID)
    {
        this.notificationMethodID = notificationMethodID;
    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public String getSurname()
    {
        return surname;
    }

    @Override
    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    @Override
    public String getEmail()
    {
        return email;
    }

    @Override
    public String getAccountNumber()
    {
        return accountNumber;
    }

    @Override
    public String getAFM()
    {
        return AFM;
    }

    @Override
    public String getUsername()
    {
        return username;
    }

    @Override
    public String getPassword()
    {
        return password;
    }

    @Override
    public Integer getSpecialityID()
    {
        return specialityID;
    }

    /**
     * Get the notification method id of the technician trying to register or edit.
     *
     * @return The notification method id of the technician trying to register or edit.
     */
    @Override
    public Integer getNotificationMethodID()
    {
        return notificationMethodID;
    }

    @Override
    public void setSpecialityList(List<String> specialityList, String defaultName)
    {
        specialities = new ArrayList<>(specialityList);
        specialities.add(0, defaultName);
    }

    /**
     * Populate the list holding all available notification methods.
     *
     * @param notificationMethods The list of notification methods.
     * @param defaultName         A default notification placeholder.
     */
    @Override
    public void setNotificationList(List<String> notificationMethods, String defaultName)
    {

    }

    @Override
    public void onSuccessfulRegister(Integer id)
    {
        technicianID = id;
    }

    @Override
    public void showErrorMessage(String title, String message)
    {
        errorTitle = title;
        errorMessage = message;
    }

    public int getTechnicianID()
    {
        return technicianID;
    }

    public ArrayList<String> getSpecialities()
    {
        return specialities;
    }

    public String getErrorTitle()
    {
        return errorTitle;
    }

    public String getErrorMessage()
    {
        return errorMessage;
    }
}
