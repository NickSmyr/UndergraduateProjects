package com.example.quickrepair.memorydao;

import com.example.quickrepair.dao.CustomerDAO;
import com.example.quickrepair.domain.Customer;
import com.example.quickrepair.domain.RepairRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CustomerDAOMemory implements CustomerDAO {
    protected static ArrayList<Customer> entities = new ArrayList<Customer>();

    /**
     * Delete a customer
     * @param entity Customer
     */
    @Override
    public void delete(Customer entity)
    {
        entities.remove(entity);
    }

    /**
     * Save a customer
     * @param entity Customer
     */
    @Override
    public void save(Customer entity)
    {
        entities.add(entity);
    }

    /**
     * Find a customer by id
     * @param customerID customer's id
     * @return Customer or null
     */
    @Override
    public Customer find(int customerID)
    {
        for(Customer customer : entities)
            if(customer.getUid() == customerID)
                return customer;
        return null;
    }

    /**
     * Returns all customers
     * @return all Costumers
     */
    @Override
    public List<Customer> findAll()
    {
        ArrayList<Customer> result = new ArrayList<Customer>();
        result.addAll(entities);
        return result;
    }

    /**
     * Return the next id for a Customer.
     * @return Customer's id
     */
    @Override
    public int nextId()
    {
        return (entities.size() > 0 ? entities.get(entities.size()-1).getUid()+1 : 1);
    }
}
