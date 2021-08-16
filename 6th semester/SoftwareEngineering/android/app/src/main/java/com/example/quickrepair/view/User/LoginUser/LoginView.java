package com.example.quickrepair.view.User.LoginUser;

import com.example.quickrepair.view.Base.BaseView;

public interface LoginView extends BaseView
{
    /**
     * Get the username of the user trying to log in.
     *
     * @return The username of the user trying to log in.
     */
    String getUsername();

    /**
     * Get the password of the user trying to log in.
     *
     * @return The password of the user trying to log in.
     */
    String getPassword();

    /**
     * Navigate a valid customer to the customer home page.
     *
     * @param id The customer's id.
     */
    void OnLoginCustomerSuccess(Integer id);

    /**
     * Navigate a valid technician to the technician home page.
     *
     * @param id The technician's id.
     */
    void OnLoginTechnicianSuccess(Integer id);
}
