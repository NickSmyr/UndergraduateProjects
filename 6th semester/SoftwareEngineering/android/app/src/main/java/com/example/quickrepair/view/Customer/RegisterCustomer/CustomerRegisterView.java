package com.example.quickrepair.view.Customer.RegisterCustomer;

import com.example.quickrepair.view.Base.BaseView;

public interface CustomerRegisterView extends BaseView
{
    /**
     * Set the name of the user trying to register or edit.
     *
     * @param name The name of the customer.
     */
    void setName(String name);

    /**
     * Set the surname of the user trying to register or edit.
     *
     * @param surname The surname of the customer.
     */
    void setSurname(String surname);

    /**
     * Set the phone number of the user trying to register or edit.
     *
     * @param phoneNumber The phone number of the customer.
     */
    void setPhoneNumber(String phoneNumber);

    /**
     * Set the bank account number of the user trying to register or edit.
     *
     * @param accountNumber The bank account number of the customer.
     */
    void setBankAccountNumber(String accountNumber);

    /**
     * Set the email of the user trying to register or edit.
     *
     * @param email The email of the customer.
     */
    void setEmail(String email);

    /**
     * Set the username of the user trying to register or edit.
     *
     * @param username The username of the customer.
     */
    void setUsername(String username);

    /**
     * Set the password of the user trying to register or edit.
     *
     * @param password The password of the customer.
     */
    void setPassword(String password);

    /**
     * Get the name of the customer trying to register or edit.
     *
     * @return The name of the customer trying to register or edit.
     */
    String getName();

    /**
     * Get the surname of the customer trying to register or edit.
     *
     * @return The surname of the customer trying to register or edit.
     */
    String getSurname();

    /**
     * Get the phone number of the customer trying to register or edit.
     *
     * @return The phone number of the customer trying to register or edit.
     */
    String getPhoneNumber();

    /**
     * Get the bank account number of the customer trying to register or edit.
     *
     * @return The bank account number of the customer trying to register or edit.
     */
    String getBankAccountNumber();

    /**
     * Get the email of the customer trying to register or edit.
     *
     * @return The email of the customer trying to register or edit.
     */
    String getEmail();

    /**
     * Get the username of the customer trying to register or edit.
     *
     * @return The username of the customer trying to register or edit.
     */
    String getUsername();

    /**
     * Get the password of the customer trying to register or edit.
     *
     * @return The password of the customer trying to register or edit.
     */
    String getPassword();

    /**
     * Navigate a valid customer to the customer home page.
     *
     * @param id The customer's id.
     */
    void onSuccessfulRegister(Integer id);
}
