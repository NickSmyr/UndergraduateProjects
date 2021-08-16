package com.example.quickrepair.dao;

import com.example.quickrepair.domain.Job;
import com.example.quickrepair.domain.JobType;

import java.util.List;

public interface JobTypeDAO {

    /**
     * Delete a JobType
     * @param entity JobType
     */
    void delete(JobType entity);

    /**
     * Find all JobTypes
     * @return JobTypes
     */
    List<JobType> findAll();

    /**
     * Save a JobType
     * @param entity JobType
     */
    void save(JobType entity);

    /**
     * Find the JobType with the given id
     * @param jobTypeID JobType's id
     * @return JobType or null
     */
    JobType find(int jobTypeID);


    /**
     * Return the next id for a JobType.
     * @return jobType's id
     */
    int nextId();
}
