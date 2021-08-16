package com.example.quickrepair.memorydao;

import com.example.quickrepair.dao.JobDAO;
import com.example.quickrepair.domain.Job;

import java.util.ArrayList;
import java.util.List;

public class JobDAOMemory implements JobDAO {
    protected static ArrayList<Job> entities = new ArrayList<Job>();

    /**
     * Delete a job
     * @param entity Job
     */
    @Override
    public void delete(Job entity)
    {
        entities.remove(entity);
    }

    /**
     * Find all Jobs
     *
     * @return Jobs
     */
    @Override
    public List<Job> findAll()
    {
        ArrayList<Job> result = new ArrayList<Job>();
        result.addAll(entities);
        return result;
    }

    /**
     * Save a Job
     *
     * @param entity Job
     */
    @Override
    public void save(Job entity)
    {
        entities.add(entity);
    }

    /**
     * Find the Job with the given id
     *
     * @param jobID job's id
     * @return Job or null
     */
    @Override
    public Job find(int jobID) {
        for(Job job : entities)
            if(job.getUid() == jobID)
                return job;
        return null;
    }

    /**
     * Find all Jobs for the JobType with the given id
     *
     * @param jobTypeID JobType's id
     * @return JobType with or null
     */
    @Override
    public List<Job> findAllJobs(int jobTypeID) {
        List<Job> result = new ArrayList<Job>();
        for(Job job: entities)
            if(job.getJobType().getUid() == jobTypeID)
                result.add(job);
        return result;
    }

    /**
     * Return the next id for a Job.
     *
     * @return Job's id
     */
    @Override
    public int nextId() {
        return (entities.size() > 0 ? entities.get(entities.size()-1).getUid()+1 : 1);
    }
}
