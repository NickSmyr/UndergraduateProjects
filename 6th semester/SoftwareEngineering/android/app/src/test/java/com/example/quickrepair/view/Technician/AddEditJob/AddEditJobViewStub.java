package com.example.quickrepair.view.Technician.AddEditJob;

import com.example.quickrepair.domain.Job;

import java.util.ArrayList;
import java.util.List;

public class AddEditJobViewStub implements AddEditJobView
{
    private String errorTitle;
    private String errorMessage;

    private ArrayList<Job> selectedJobs;
    private ArrayList<String> jobs;

    @Override
    public void setJobTypeList(List<String> areaList, String defaultName)
    {
        jobs = new ArrayList<>(areaList);
        jobs.add(0, defaultName);
    }

    @Override
    public void setJobList(List<Job> selectedJobs)
    {
        this.selectedJobs = new ArrayList<>(selectedJobs);
    }

    @Override
    public void showErrorMessage(String title, String message)
    {
        errorTitle = title;
        errorMessage = message;
    }

    public String getErrorTitle()
    {
        return errorTitle;
    }

    public String getErrorMessage()
    {
        return errorMessage;
    }

    public ArrayList<Job> getSelectedJobs()
    {
        return selectedJobs;
    }

    public ArrayList<String> getJobs()
    {
        return jobs;
    }
}
