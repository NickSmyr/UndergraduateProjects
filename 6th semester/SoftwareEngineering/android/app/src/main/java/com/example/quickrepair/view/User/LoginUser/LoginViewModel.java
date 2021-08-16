package com.example.quickrepair.view.User.LoginUser;

import com.example.quickrepair.dao.CustomerDAO;
import com.example.quickrepair.dao.TechnicianDAO;
import com.example.quickrepair.memorydao.CustomerDAOMemory;
import com.example.quickrepair.memorydao.TechnicianDAOMemory;
import com.example.quickrepair.view.Base.BaseViewModel;

public class LoginViewModel extends BaseViewModel<LoginPresenter>
{
    /**
     * Create and initialize the presenter.
     *
     * @return The initialized presenter.
     */
    @Override
    protected LoginPresenter createPresenter()
    {
        CustomerDAO customerDAO = new CustomerDAOMemory();
        TechnicianDAO technicianDAO = new TechnicianDAOMemory();

        LoginPresenter presenter = new LoginPresenter();
        presenter.setCustomerDAO(customerDAO);
        presenter.setTechnicianDAO(technicianDAO);

        return presenter;
    }
}
