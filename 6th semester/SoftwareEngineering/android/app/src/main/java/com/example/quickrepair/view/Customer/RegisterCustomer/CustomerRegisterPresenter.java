package com.example.quickrepair.view.Customer.RegisterCustomer;

import com.example.quickrepair.dao.CustomerDAO;
import com.example.quickrepair.dao.TechnicianDAO;
import com.example.quickrepair.domain.Customer;
import com.example.quickrepair.domain.Technician;
import com.example.quickrepair.view.Base.BasePresenter;

public class CustomerRegisterPresenter extends BasePresenter<CustomerRegisterView>
{
    private CustomerDAO customerDAO;
    private TechnicianDAO technicianDAO;

    Customer customer;

    /**
     * Try to register a customer with the given information.
     *
     * @param name          The name of the customer.
     * @param surname       The surname of the customer.
     * @param phoneNumber   The phone number of the customer.
     * @param email         The email of the customer.
     * @param accountNumber The bank account number of the customer.
     * @param username      The username of the customer.
     * @param password      The password of the customer.
     */
    void registerCustomer(String name, String surname, String phoneNumber, String email, String accountNumber, String username, String password)
    {
        if (customer == null || customer.getUid() == 0)
        {

            for (Technician technician : technicianDAO.findAll())
            {
                if (technician.getUsername().equals(username))
                {
                    view.showErrorMessage("Username already exist", "This username is already in use by another user.");
                    return;
                }
            }

            for (Customer customer : customerDAO.findAll())
            {
                if (customer.getUsername().equals(username))
                {
                    view.showErrorMessage("Username already exist", "This username is already in use by another user.");
                    return;
                }
            }

            customer = new Customer();
        }

        try
        {
            customer.setUserInfo(name, surname, phoneNumber, email, accountNumber, username);
            customer.setPassword(password);
        }
        catch (Exception e)
        {
            view.showErrorMessage("Invalid value", e.getMessage());
            return;
        }

        if (customer.getUid() == 0)
        {
            customer.setUid(customerDAO.nextId());
            customerDAO.save(customer);
        }

        view.onSuccessfulRegister(customer.getUid());
    }

    /**
     * Initialize the view.
     */
    public void setUpDataSource()
    {
        if (customer != null)
        {
            view.setName(customer.getName());
            view.setSurname(customer.getSurname());
            view.setPhoneNumber(customer.getPhoneNumber());
            view.setBankAccountNumber(customer.getBankAccount());
            view.setEmail(customer.getEmail());
            view.setUsername(customer.getUsername());
            view.setPassword(customer.getPassword());
        }
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
     * Set the customer DAO for the presenter.
     *
     * @param customerDAO The customer DAO.
     */
    public void setCustomerDAO(CustomerDAO customerDAO)
    {
        this.customerDAO = customerDAO;
    }

    /**
     * Load the customer in case of edit.
     *
     * @param customerID the customer id.
     */
    public void setCustomer(int customerID)
    {
        customer = customerDAO.find(customerID);
    }
}
