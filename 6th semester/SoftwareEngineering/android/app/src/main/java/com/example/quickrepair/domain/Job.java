package com.example.quickrepair.domain;

import java.util.ArrayList;
import java.util.Objects;

public class Job
{
    private int uid;
    private Technician technician;
    private JobType jobType;
    private ArrayList<RepairRequest> repairRequests;

    private double price;

    /**
     * Default constructor.
     */
    public Job()
    {
        repairRequests = new ArrayList<>();
    }

    /**
     * Construct a job.
     *
     * @param technician The technician offering the job.
     * @param jobType The type of the job.
     * @param price The price of the job.
     */
    public Job(Technician technician, JobType jobType, double price)
    {
        this();
        setTechnician(technician);
        setJobType(jobType);
        setPrice(price);
    }

    /**
     * Get the technician offering the job.
     *
     * @return The technician offering the job.
     */
    public Technician getTechnician()
    {
        return technician;
    }

    /**
     * Set the technician offering the job.
     *
     * @param technician The technician offering
     */
    public void setTechnician(Technician technician)
    {
        if (technician == null) throw new NullPointerException("Technician can not be null.");

        this.technician = technician;
    }

    /**
     * Get the type of the job.
     *
     * @return The type of the job.
     */
    public JobType getJobType()
    {
        return jobType;
    }

    /**
     * Set the type of the job.
     *
     * @param jobType The type of the job.
     */
    public void setJobType(JobType jobType)
    {
        if (jobType == null) throw new NullPointerException("JobType can not be null.");

        this.jobType = jobType;
    }

    /**
     * Get the price of the job.
     *
     * @return The price of the job.
     */
    public double getPrice()
    {
        return price;
    }

    /**
     * Set the price for the job.
     *
     * @param price The price of the job.
     */
    public void setPrice(double price)
    {
        if (price < 0) throw new NumberFormatException("Price can not be negative.");
        if (price == 0) throw new NumberFormatException("Price can not be zero.");

        this.price = price;
    }

    /**
     * Get the job UID.
     *
     * @return The UID.
     */
    public int getUid()
    {
        return uid;
    }

    /**
     * Set the job UID.
     *
     * @param uid The evaluation's UID.
     */
    public void setUid(int uid)
    {
        this.uid = uid;
    }

    /**
     * add a new repair request
     * @param repairRequest repair request that will be added
     */
    public void addRepairRequest(RepairRequest repairRequest)
    {
        if (repairRequest == null) throw new NullPointerException();
        if (!repairRequest.getJob().equals(this)) throw new IllegalArgumentException();

        repairRequests.add(repairRequest);
    }

    /**
     * remove given repair request
     * @param repairRequest repair request that will be removed
     */
    public void removeRepairRequest(RepairRequest repairRequest)
    {
        if (repairRequest == null) throw new NullPointerException();

        repairRequests.remove(repairRequest);
    }

    /**
     * return repair requests
     * @return repair requests
     */
    public ArrayList<RepairRequest> getRepairRequests()
    {
        return repairRequests;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Job job = (Job) o;
        return Double.compare(job.price, price) == 0 && Objects.equals(technician, job.technician) && Objects.equals(jobType, job.jobType);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(technician, jobType, price);
    }
}
