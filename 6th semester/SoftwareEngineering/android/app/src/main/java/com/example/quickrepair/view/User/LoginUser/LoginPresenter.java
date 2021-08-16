package com.example.quickrepair.view.User.LoginUser;

import com.example.quickrepair.dao.CustomerDAO;
import com.example.quickrepair.dao.TechnicianDAO;
import com.example.quickrepair.domain.Customer;
import com.example.quickrepair.domain.Technician;
import com.example.quickrepair.view.Base.BasePresenter;

public class LoginPresenter extends BasePresenter<LoginView>
{
    private CustomerDAO customerDAO;
    private TechnicianDAO technicianDAO;

    /**
     * Set the customer DAO for the presenter.
     *
     * @param customerDAO The customer DAO.
     */
    public void setCustomerDAO(CustomerDAO customerDAO)
    {
        this.customerDAO = customerDAO;
    }

    /**
     * Set the technician DAO for the presenter.
     *
     * @param technicianDAO The technician DAO.
     */
    public void setTechnicianDAO(TechnicianDAO technicianDAO)
    {
        this.technicianDAO = technicianDAO;
    }

    /**
     * Try to log in the user with the given credentials.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     */
    public void login(String username, String password)
    {
        LoginState state = LoginState.NO_ACCOUNT;
        Integer id = null;

        for (Customer customer : customerDAO.findAll())
        {
            if (customer.getUsername().equals(username))
            {
                if (customer.getPassword().equals(password))
                {
                    state = LoginState.VALID_CUSTOMER;
                    id = customer.getUid();
                }
                else
                {
                    state = LoginState.INVALID;
                }

                break;
            }
        }

        if (state == LoginState.NO_ACCOUNT)
        {
            for (Technician technician : technicianDAO.findAll())
            {
                if (technician.getUsername().equals(username))
                {
                    if (technician.getPassword().equals(password))
                    {
                        state = LoginState.VALID_TECHNICIAN;
                        id = technician.getUid();
                    }
                    else
                    {
                        state = LoginState.INVALID;
                    }

                    break;
                }
            }
        }

        switch (state)
        {
            case VALID_CUSTOMER:
                view.OnLoginCustomerSuccess(id);
                break;
            case VALID_TECHNICIAN:
                view.OnLoginTechnicianSuccess(id);
                break;
            default:
                view.showErrorMessage("Invalid credentials", "Invalid username or password.");
                break;
        }
    }

    private enum LoginState
    {
        NO_ACCOUNT,
        INVALID,
        VALID_CUSTOMER,
        VALID_TECHNICIAN
    }
}
