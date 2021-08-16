package com.example.quickrepair.domain;

import java.util.Calendar;
import java.util.Objects;

public class Repair
{
    private int uid;
    private double quantity;
    private Payment payment;
    private Evaluation evaluation;
    private RepairRequest repairRequest;

    /**
     * Default constructor
     */
    public Repair()
    {
    }

    /**
     * Repair's constructor with quantity
     * technician is going to be paid later
     *
     * @param repairRequest Repair's request
     * @param quantity Job's quantity, this parameter is going to define the final cost, the type of the job define the meaning of this parameter
     */
    public Repair(RepairRequest repairRequest, double quantity)
    {
        setRepairRequest(repairRequest);
        setQuantity(quantity);

    }

    /**
     * Get the quantity of the repair.
     *
     * @return Repair's quantity
     */
    public double getQuantity()
    {
        return quantity;
    }

    /**
     * set repair's quantity
     * only possitive and non zero quantity
     *
     * @param quantity customer's quantity
     */
    public void setQuantity(double quantity)
    {
        if (quantity <= 0) throw new IllegalArgumentException("non positive quantity");

        if (repairRequest.getJob().getJobType().getMeasurementUnit() == MeasurementUnit.NONE)
        {
            if ((int) quantity != quantity) throw new IllegalArgumentException("for fixed price the quantity must be integer");
        }

        this.quantity = quantity;
    }

    /**
     * return repair's payment
     */
    public Payment getPayment()
    {
        return payment;
    }

    /**
     * set repair's payment
     *
     * @param payment customer's payment
     */
    private void setPayment(Payment payment)
    {
        if (this.payment != null) throw new IllegalStateException("The repair already has a payment.");

        this.payment = payment;
    }

    //return repair's evaluation

    /**
     * return repair's evaluation
     */
    public Evaluation getEvaluation()
    {
        return evaluation;
    }

    /**
     * set repair's evaluation
     *
     * @param evaluation customer's evaluation
     */
    private void setEvaluation(Evaluation evaluation)
    {

        if (this.evaluation != null) throw new IllegalStateException("only one evaluation, can't reset it.");

        this.evaluation = evaluation;
    }

    /**
     * Checks if the repair has been evaluated
     *
     * @return true if it is evaluated
     */
    public boolean isEvaluated()
    {
        return getEvaluation() != null;
    }

    /**
     * Checks if the repair has been marked as completed
     *
     * @return true if it is completed
     */
    public boolean isPaid()
    {
        return getPayment() != null;
    }

    /**
     * @return repair request that was the cause of the repair
     */
    public RepairRequest getRepairRequest()
    {
        return repairRequest;
    }

    /**
     * Sets the repair request of this object
     *
     * @param repairRequest
     */
    public void setRepairRequest(RepairRequest repairRequest)
    {
        if (repairRequest == null) throw new NullPointerException("null repairRequest");

        this.repairRequest = repairRequest;
    }

    /**
     * Get the repair UID.
     *
     * @return The repair UID.
     */
    public int getUid()
    {
        return uid;
    }

    /**
     * Set the repair UID.
     *
     * @param uid The repair UID.
     */
    public void setUid(Integer uid)
    {
        this.uid = uid;
    }

    /**
     * set evaluation
     *
     * @param title   evaluation's title
     * @param comment evaluation's comment
     * @param rate    evaluation's rate
     */
    public Evaluation evaluate(String title, String comment, int rate)
    {
        Evaluation evaluation = new Evaluation(this, title, comment, rate);

        setEvaluation(evaluation);

        return evaluation;
    }

    /**
     * pay for repair
     *
     * @param date        payment's date
     * @param paymentType payment's type
     */
    public Payment pay(Calendar date, PaymentType paymentType)
    {
        Payment payment = new Payment(this, date, paymentType);
        payment.setCost(calculateCost());
        setPayment(payment);
        return payment;
    }

    /**
     * calculate cost for this repair
     * @return repair's cost
     */
    public double calculateCost(){
        return quantity * repairRequest.getJob().getPrice();
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Repair repair = (Repair) o;
        return Double.compare(repair.quantity, quantity) == 0 && Objects.equals(uid, repair.uid) && Objects.equals(payment, repair.payment) && Objects.equals(evaluation, repair.evaluation) && Objects.equals(repairRequest, repair.repairRequest);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(quantity, repairRequest);
    }
}
