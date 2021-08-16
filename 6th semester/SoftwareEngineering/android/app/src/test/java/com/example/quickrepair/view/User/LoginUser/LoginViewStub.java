package com.example.quickrepair.view.User.LoginUser;

public class LoginViewStub implements LoginView
{
    private int errorCount = 0;
    private String username = "aggelidis";
    private String password = "123";
    private int customerID = 0;
    private int technicianID = 0;

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
    public void showErrorMessage(String title, String message)
    {
        ++errorCount;
    }

    @Override
    public void OnLoginCustomerSuccess(Integer id)
    {
        customerID = id;
    }

    @Override
    public void OnLoginTechnicianSuccess(Integer id)
    {
        technicianID = id;
    }

    public int getErrorCount()
    {
        return errorCount;
    }

    public int getCustomerID()
    {
        return customerID;
    }

    public int getTechnicianID()
    {
        return technicianID;
    }

    public void setTechnicianID(int technicianID)
    {
        this.technicianID = technicianID;
    }
}
