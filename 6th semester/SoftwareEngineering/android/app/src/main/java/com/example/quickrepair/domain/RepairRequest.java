package com.example.quickrepair.domain;


import java.util.GregorianCalendar;
import java.util.Objects;

public class RepairRequest implements Comparable<RepairRequest>
{
    public enum Status
    {
        REJECTED,
        CONFIRMED,
        UNCONFIRMED,
        COMPLETED
    }

    private int uid;
    private Customer customer;
    private Job job;
    private GregorianCalendar creationDate;
    private GregorianCalendar conductionDate;
    private Address address;

    private Status status;

    private String commentsFromCustomer;
    private int estimatedDuration;

    private Repair repair;

    //constructors

    /**
     * Default constructor.
     */
    public RepairRequest()
    {
        status = Status.UNCONFIRMED;
    }

    /*
     * Constructor when the Repair Request initialize from Costumer
     */
    public RepairRequest(Customer customer, Job job, GregorianCalendar creationDate, GregorianCalendar conductionDate, Address address, String commentsFromCustomer)
    {
        setCustomer(customer);
        setJob(job);
        setCreationDate(creationDate);
        setConductionDate(conductionDate);
        setAddress(address);
        setCommentsFromCustomer(commentsFromCustomer);
        status = Status.UNCONFIRMED;
    }

    /**
     * set conduction date
     * @param conductionDate conduction date
     */
    public void setConductionDate(GregorianCalendar conductionDate)
    {
        if (conductionDate == null)
        {
            throw new IllegalArgumentException("Null conductionDate");
        }

        this.conductionDate = conductionDate;
    }

    /**
     *  set creation date
     * @param creationDate creation date
     */
    public void setCreationDate(GregorianCalendar creationDate)
    {
        if (creationDate == null)
        {
            throw new IllegalArgumentException("Null creationDate");
        }

        this.creationDate = creationDate;
    }

    /**
     * set address
     * @param address repairRequest's address
     */
    public void setAddress(Address address)
    {
        if (address == null)
        {
            throw new IllegalArgumentException("Null address");
        }

        this.address = address;
    }

    /**
     * set customer
     * @param customer repair request's Customer
     */
    public void setCustomer(Customer customer)
    {
        if (customer == null)
        {
            throw new IllegalArgumentException("Null customer");
        }

        this.customer = customer;
    }

    /**
     * set repair
     * @param repair Repair for this RepairRequest
     */
    public void setRepair(Repair repair)
    {
        if (repair == null)
        {
            throw new IllegalArgumentException("Null repair");
        }

        this.repair = repair;
    }

    /**
     * set job
     * @param job Job for this RepairRequest
     */
    public void setJob(Job job)
    {
        if (job == null)
        {
            throw new IllegalArgumentException("Null job");
        }

        this.job = job;
    }

    /**
     * set comments from customer
     * @param commentsFromCustomer Customer's comments
     */
    public void setCommentsFromCustomer(String commentsFromCustomer)
    {
        if (commentsFromCustomer == null) throw new NullPointerException("Null commentsFromCustomer");

        this.commentsFromCustomer = commentsFromCustomer;
    }

    /**
     * set estimated duration
     * @param estimatedDuration estimated duration for repair
     */
    public void setEstimatedDuration(int estimatedDuration)
    {
        if (estimatedDuration <= 0) throw new NumberFormatException("Duration can not be negative or zero.");

        this.estimatedDuration = estimatedDuration;
    }

    /**
     * Get the repair request status.
     *
     * @return The repair request status.
     */
    public Status getStatus()
    {
        return status;
    }

    /**
     * Get the repair request UID.
     *
     * @return The repair request UID.
     */
    public int getUid()
    {
        return uid;
    }

    /**
     * Set the repair request UID.
     *
     * @param uid The repair request UID.
     */
    public void setUid(int uid)
    {
        this.uid = uid;
    }

    /**
     * get creation date
     * @return creationDate
     */
    public GregorianCalendar getCreationDate()
    {
        return (GregorianCalendar) creationDate.clone();
    }

    /**
     * get conduction date
     * @return conductionDate
     */
    public GregorianCalendar getConductionDate()
    {
        return (GregorianCalendar) conductionDate.clone();
    }

    /**
     * get address
     * @return address
     */
    public Address getAddress()
    {
        return address;
    }

    /**
     * return repair
     * @return Repair
     */
    public Repair getRepair()
    {
        return repair;
    }

    /**
     * return customer
     * @return Customer
     */
    public Customer getCustomer()
    {
        return customer;
    }

    /**
     * return comments from customer
     * @return Customer's comments
     */
    public String getCommentsFromCustomer()
    {
        return commentsFromCustomer;
    }

    /**
     * get duration
     * @return estimatedDuration
     */
    public int getEstimatedDuration()
    {
        return estimatedDuration;
    }

    /**
     * return job
     * @return Job
     */
    public Job getJob()
    {
        return job;
    }

    /**
     * check if a repair request is confirmed
     * @return true if a repair request is confirmed, else false
     */
    public boolean isConfirmed()
    {
        return this.status == Status.CONFIRMED || this.status == Status.COMPLETED;
    }

    /**
     * check if a repair request is completed
     * @return true if a repair request is completed, else false
     */
    public boolean isCompleted()
    {
        return this.status == Status.COMPLETED;
    }

    /**
     * check if a repair request is unconfirmed
     * @return true if a repair request is unconfirmed, else false
     */
    public boolean isUnconfirmed()
    {
        return this.status == Status.UNCONFIRMED;
    }

    /**
     * check if a repair request is rejected
     * @return true if a repair request is rejected, else false
     */
    public boolean isRejected()
    {
        return this.status == Status.REJECTED;
    }

    /**
     * Technician can confirm a repair request and add estimated duration
     * @param estimatedDuration how long will last this repair request
     */
    public void confirm(int estimatedDuration)
    {
        if (this.status == Status.CONFIRMED)
            throw new IllegalStateException("Repair request is already confirmed.");

        this.status = Status.CONFIRMED;
        setEstimatedDuration(estimatedDuration);
    }

    /**
     * Technician can reject a repair request
     */
    public void reject()
    {
        if (this.status == Status.REJECTED)
            throw new IllegalStateException("Repair request is already rejected.");

        this.status = Status.REJECTED;
    }

    /**
     * set RepairRequest as completed and create Repair for this RepairRequest
     * @param quantity Repair's quantity
     * @return  Repair for this RepairRequest
     */
    public Repair complete(double quantity)
    {
        if (this.status == Status.UNCONFIRMED)
            throw new IllegalStateException("Repair request is not confirmed.");

        if (isCompleted()) throw new IllegalStateException("Repair request is already completed.");
        this.status = Status.COMPLETED;
        Repair repair = new Repair(this, quantity);
        setRepair(repair);

        return repair;
    }

    @Override
    public int compareTo(RepairRequest o)
    {
        return conductionDate.compareTo(o.conductionDate);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RepairRequest that = (RepairRequest) o;
        return estimatedDuration == that.estimatedDuration &&
                Objects.equals(customer, that.customer) &&
                Objects.equals(job, that.job) &&
                Objects.equals(creationDate, that.creationDate) &&
                Objects.equals(conductionDate, that.conductionDate) &&
                Objects.equals(address, that.address) &&
                status == that.status &&
                Objects.equals(commentsFromCustomer, that.commentsFromCustomer) &&
                Objects.equals(repair, that.repair);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(creationDate, conductionDate, address, status, commentsFromCustomer, estimatedDuration);
    }
}
