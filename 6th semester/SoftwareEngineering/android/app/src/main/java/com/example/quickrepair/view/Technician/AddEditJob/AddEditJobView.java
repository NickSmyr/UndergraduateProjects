package com.example.quickrepair.view.Technician.AddEditJob;

import com.example.quickrepair.domain.Job;
import com.example.quickrepair.view.Base.BaseView;

import java.util.List;

public interface AddEditJobView extends BaseView
{
    /**
     * Populate the list holding all available job types.
     *
     * @param jobTypeList The list of job types.
     * @param defaultName A default job type placeholder.
     */
    void setJobTypeList(List<String> jobTypeList, String defaultName);

    /**
     * Populate the list holding the technician's job types.
     *
     * @param selectedJobs The list of the technician's job types.
     */
    void setJobList(List<Job> selectedJobs);
}
