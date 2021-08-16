package com.example.quickrepair.view.Customer.RegisterCustomer;

import com.example.quickrepair.dao.CustomerDAO;
import com.example.quickrepair.dao.TechnicianDAO;
import com.example.quickrepair.memorydao.CustomerDAOMemory;
import com.example.quickrepair.memorydao.TechnicianDAOMemory;
import com.example.quickrepair.view.Base.BaseViewModel;

public class CustomerRegisterViewModel extends BaseViewModel<CustomerRegisterPresenter>
{
    /**
     * Create and initialize the presenter.
     *
     * @return The initialized presenter.
     */
    @Override
    protected CustomerRegisterPresenter createPresenter()
    {
        CustomerDAO customerDAO = new CustomerDAOMemory();
        TechnicianDAO technicianDAO = new TechnicianDAOMemory();

        CustomerRegisterPresenter presenter = new CustomerRegisterPresenter();
        presenter.setCustomerDAO(customerDAO);
        presenter.setTechnicianDAO(technicianDAO);

        return presenter;
    }
}
