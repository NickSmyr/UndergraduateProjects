package com.example.quickrepair.dao;


import com.example.quickrepair.domain.Payment;

import java.util.List;

public interface PaymentDAO {

    /**
     * Delete a Payment
     * @param entity Payment
     */
    public void delete(Payment entity);

    /**
     * Find all Payments
     * @return Payments
     */
    List<Payment> findAll();

    /**
     * Save a Payment
     * @param entity Payment
     */
    void save(Payment entity);

    /**
     * Find the Payment with the given id
     * @param paymentID Payment's id
     * @return Payment or null
     */
    Payment find(int paymentID);

    /**
     * Return the next id for a Payment.
     * @return jobType's id
     */
    int nextId();
}
