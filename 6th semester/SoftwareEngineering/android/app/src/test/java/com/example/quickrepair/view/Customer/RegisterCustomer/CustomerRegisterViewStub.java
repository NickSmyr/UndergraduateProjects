package com.example.quickrepair.view.Customer.RegisterCustomer;

public class CustomerRegisterViewStub implements CustomerRegisterView
{
    private String name;
    private String surname;
    private String phoneNumber;
    private String accountNumber;
    private String email;
    private String username;
    private String password;
    private int customerID;
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
    public void setBankAccountNumber(String accountNumber)
    {
        this.accountNumber = accountNumber;
    }

    @Override
    public void setEmail(String email)
    {
        this.email = email;
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
    public String getBankAccountNumber()
    {
        return accountNumber;
    }

    @Override
    public String getEmail()
    {
        return email;
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
    public void onSuccessfulRegister(Integer id)
    {
        customerID = id;
    }

    @Override
    public void showErrorMessage(String title, String message)
    {
        errorTitle = title;
        errorMessage = message;
    }

    public String getErrorTitle()
    {
        return errorTitle;
    }

    public String getErrorMessage()
    {
        return errorMessage;
    }

    public int getCustomerID()
    {
        return customerID;
    }
}
