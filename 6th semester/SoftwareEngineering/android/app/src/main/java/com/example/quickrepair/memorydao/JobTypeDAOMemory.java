package com.example.quickrepair.memorydao;

import com.example.quickrepair.dao.JobTypeDAO;
import com.example.quickrepair.domain.Job;
import com.example.quickrepair.domain.JobType;

import java.util.ArrayList;
import java.util.List;

public class JobTypeDAOMemory implements JobTypeDAO {
    protected static ArrayList<JobType> entities = new ArrayList<JobType>();
    /**
     * Delete a JobType
     *
     * @param entity JobType
     */
    @Override
    public void delete(JobType entity) {
        entities.remove(entity);
    }

    /**
     * Find all JobTypes
     *
     * @return JobTypes
     */
    @Override
    public List<JobType> findAll() {
        ArrayList<JobType> result = new ArrayList<JobType>();
        result.addAll(entities);
        return result;
    }

    /**
     * Save a JobType
     *
     * @param entity JobType
     */
    @Override
    public void save(JobType entity)
    {
        entities.add(entity);
    }

    /**
     * Find the JobType with the given id
     *
     * @param jobTypeID JobType's id
     * @return JobType or null
     */
    @Override
    public JobType find(int jobTypeID)
    {
        for(JobType jobType : entities)
        {
            if(jobType.getUid() == jobTypeID)
            {
                return jobType;
            }
        }
        return null;
    }

    /**
     * Return the next id for a JobType.
     *
     * @return jobType's id
     */
    @Override
    public int nextId() {
        return (entities.size() > 0 ? entities.get(entities.size()-1).getUid()+1 : 1);
    }
}
