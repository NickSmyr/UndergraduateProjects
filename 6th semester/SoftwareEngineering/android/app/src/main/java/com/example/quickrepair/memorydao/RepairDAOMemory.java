package com.example.quickrepair.memorydao;

import com.example.quickrepair.dao.RepairDAO;
import com.example.quickrepair.domain.Repair;

import java.util.ArrayList;
import java.util.List;

public class RepairDAOMemory implements RepairDAO {
    protected static ArrayList<Repair> entities = new ArrayList<Repair>();

    /**
     * Delete an Repair
     * @param entity Repair
     */
    @Override
    public void delete(Repair entity)
    {
        entities.remove(entity);
    }

    /**
     * Find all Repairs
     * @return Repairs
     */
    @Override
    public List<Repair> findAll()
    {
        ArrayList<Repair> result = new ArrayList<Repair>();
        result.addAll(entities);
        return result;
    }

    /**
     * Save a Repair
     * @param entity Repair
     */
    @Override
    public void save(Repair entity)
    {
        entities.add(entity);
    }

    /**
     * Find the Repair with the given id
     * @param repairID Repair's id
     * @return Repair or null
     */
    @Override
    public Repair find(int repairID)
    {
        for(Repair repair : entities)
            if(repair.getUid() == repairID)
                return repair;
        return null;
    }

    /**
     * Return the next id for an Repair.
     * @return Repair's id
     */
    @Override
    public int nextId()
    {
        return (entities.size() > 0 ? entities.get(entities.size()-1).getUid()+1 : 1);
    }
}
