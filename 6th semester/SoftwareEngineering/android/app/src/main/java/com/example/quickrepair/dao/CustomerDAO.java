package com.example.quickrepair.dao;

import com.example.quickrepair.domain.Customer;
import com.example.quickrepair.domain.RepairRequest;

import java.util.List;
import java.util.Set;

public interface CustomerDAO {
    /**
     * Delete a customer
     * @param entity Customer
     */
    void delete(Customer entity);

    /**
     * Save a customer
     * @param entity Customer
     */
    void save(Customer entity);

    /**
     * Find a customer by id
     * @param customerID customer's id
     * @return Customer or null
     */
    Customer find(int customerID);

    /**
     * Returns all customers
     * @return all Costumers
     */
    List<Customer> findAll();


    /**
     * Return the next id for a Customer.
     * @return Customer's id
     */
    int nextId();
}
