package com.example.quickrepair.dao;

import com.example.quickrepair.domain.Job;

import java.util.List;

public interface JobDAO {

    /**
     * Delete a job
     * @param entity Job
     */
    void delete(Job entity);

    /**
     * Find all Jobs
     * @return Jobs
     */
    List<Job> findAll();

    /**
     * Save a Job
     * @param entity Job
     */
    void save(Job entity);

    /**
     * Find the Job with the given id
     * @param jobID job's id
     * @return Job or null
     */
    Job find(int jobID);

    /**
     * Find all Jobs for the JobType with the given id
     * @param jobTypeID JobType's id
     * @return JobType with or null
     */
    List<Job> findAllJobs(int jobTypeID);

    /**
     * Return the next id for a Job.
     * @return Job's id
     */
    int nextId();
}
