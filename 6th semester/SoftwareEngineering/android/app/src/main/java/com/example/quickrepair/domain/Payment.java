package com.example.quickrepair.domain;

import java.util.Calendar;
import java.util.Objects;

public class Payment
{
    private int uid;
    private double cost;
    private Repair repair;
    private Calendar date;
    private PaymentType paymentType;

    /**
     * Empty Constructor
     */
    public Payment()
    {
    }

    /**
     * Payment's Constructor
     *
     * @param date        payment's date
     * @param paymentType payment's type
     */
    public Payment(Repair repair, Calendar date, PaymentType paymentType)
    {
        setRepair(repair);
        setDate(date);
        setPaymentType(paymentType);
    }

    /**
     * return payment's date
     */
    public Calendar getDate()
    {
        return date;
    }

    /**
     * set payment's date
     *
     * @param date payment's date
     */
    public void setDate(Calendar date)
    {
        if (date != null)
        {
            this.date = date;
        }
        else
        {
            throw new NullPointerException("null date");
        }
    }

    /**
     * Gte the type of the payment.
     *
     * @return The type of payment
     */
    public PaymentType getPaymentType()
    {
        return paymentType;
    }

    /**
     * set Payment's type
     *
     * @param paymentType payment's type
     */
    public void setPaymentType(PaymentType paymentType)
    {
        if (paymentType == null)
        {
            throw new NullPointerException("null paymentType");
        }
        this.paymentType = paymentType;

    }

    /**
     * Get the payment UID.
     *
     * @return The payment UID.
     */
    public int getUid()
    {
        return uid;
    }

    /**
     * Set the payment UID.
     *
     * @param uid The payment UID.
     */
    public void setUid(int uid)
    {
        this.uid = uid;
    }

    /**
     * Get the repair associated with the payment.
     *
     * @return The repair associated with the payment.
     */
    public Repair getRepair()
    {
        return repair;
    }

    /**
     * Set the repair associated with the payment.
     *
     * @param repair The repair associated with the payment.
     */
    public void setRepair(Repair repair)
    {
        if (repair == null) throw new NullPointerException("The repair can not be null.");

        this.repair = repair;
    }

    /**
     * get payment's cost
     * @return payment's cost
     */
    public double getCost()
    {
        return cost;
    }

    public void setCost(double cost)
    {
        if (cost <= 0) throw new IllegalArgumentException();

        this.cost = cost;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return Objects.equals(repair, payment.repair) && Objects.equals(date, payment.date) && paymentType == payment.paymentType;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(repair, date, paymentType);
    }
}
