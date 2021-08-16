package com.example.quickrepair.memorydao;

import com.example.quickrepair.dao.PaymentDAO;
import com.example.quickrepair.domain.Payment;

import java.util.ArrayList;
import java.util.List;

public class PaymentDAOMemory implements PaymentDAO {
    protected static ArrayList<Payment> entities = new ArrayList<Payment>();

    /**
     * Delete a Payment
     * @param entity Payment
     */
    @Override
    public void delete(Payment entity)
    {
        entities.remove(entity);
    }

    /**
     * Find all Payments
     * @return Payments
     */
    @Override
    public List<Payment> findAll()
    {
        ArrayList<Payment> result = new ArrayList<Payment>();
        result.addAll(entities);
        return result;
    }

    /**
     * Save a Payment
     * @param entity Payment
     */
    @Override
    public void save(Payment entity)
    {
        entities.add(entity);
    }

    /**
     * Find the Payment with the given id
     * @param paymentID Payment's id
     * @return Payment or null
     */
    @Override
    public Payment find(int paymentID)
    {
        for(Payment payment : entities)
            if(payment.getUid() == paymentID)
                return payment;
        return null;
    }

    /**
     * Return the next id for a Payment.
     * @return jobType's id
     */
    @Override
    public int nextId()
    {
        return (entities.size() > 0 ? entities.get(entities.size()-1).getUid()+1 : 1);
    }
}
