package com.example.quickrepair.view.Technician.AddEditJob;

import com.example.quickrepair.dao.TechnicianDAO;
import com.example.quickrepair.domain.Job;
import com.example.quickrepair.domain.JobType;
import com.example.quickrepair.domain.Technician;
import com.example.quickrepair.view.Base.BasePresenter;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Objects;

public class AddEditJobPresenter extends BasePresenter<AddEditJobView>
{
    private TechnicianDAO technicianDAO;

    private Technician technician;
    private ArrayList<Job> jobs;
    private ArrayList<JobType> jobTypes;

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
     * Load the technician in case of edit.
     *
     * @param id The technician's id.
     */
    public void setTechnician(Integer id)
    {
        technician = technicianDAO.find(id);
        jobTypes = new ArrayList<>(technician.getSpecialty().getJobTypes());
    }

    /**
     * Initialize the view.
     */
    public void setUpDataSource()
    {
        ArrayList<String> jobTypeNames = new ArrayList<>();

        for (JobType jobType : technician.getSpecialty().getJobTypes())
        {
            jobTypeNames.add(jobType.getName());
        }
        view.setJobTypeList(jobTypeNames, "Επιλέξτε εργασία");

        jobs = new ArrayList<>(technician.getJobs());
        view.setJobList(jobs);
    }

    /**
     * Add a new job to the technician.
     *
     * @param jobTypeID the job type id.
     * @param price The price offer for the job.
     */
    void addJob(Integer jobTypeID, String price)
    {
        if (jobTypeID == 0)
        {
            view.showErrorMessage("No job selected", "You have to add a valid job.");
            return;
        }

        double priceConverted;
        try
        {
            NumberFormat format = NumberFormat.getInstance();
            priceConverted = Objects.requireNonNull(format.parse(price)).doubleValue();
        }
        catch (ParseException e)
        {
            view.showErrorMessage("Invalid price value", "Please enter a valid price value");
            return;
        }

        if (priceConverted <= 0)
        {
            view.showErrorMessage("Invalid price value", "Price can not be zero or negative.");
            return;
        }

        JobType type = jobTypes.get(jobTypeID - 1);
        Job job;

        try
        {
            job = technician.addJob(type, priceConverted);
        }
        catch (IllegalArgumentException e)
        {
            view.showErrorMessage("Invalid value.", e.getMessage());
            return;
        }

        jobs.add(job);
        view.setJobList(jobs);
    }

    /**
     * Remove a job from the technician.
     *
     * @param jobPosition the job position in the list of jobs of the technician.
     */
    void removeJob(int jobPosition)
    {
        technician.removeJob(jobs.remove(jobPosition));
        view.setJobList(jobs);
    }
}
