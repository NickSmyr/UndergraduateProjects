package com.example.quickrepair.memorydao;

import com.example.quickrepair.dao.TechnicianDAO;
import com.example.quickrepair.domain.Customer;
import com.example.quickrepair.domain.RepairRequest;
import com.example.quickrepair.domain.Technician;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TechnicianDAOMemory implements TechnicianDAO
{
    protected static ArrayList<Technician> entities = new ArrayList<Technician>();

    /**
     * Delete a Technician
     *
     * @param entity Technician
     */
    @Override
    public void delete(Technician entity)
    {
        entities.remove(entity);
    }

    /**
     * Save a Technician
     *
     * @param entity Technician
     */
    @Override
    public void save(Technician entity)
    {
        entities.add(entity);
    }

    /**
     * Find a Technician by id
     *
     * @param technicianID Technician's id
     * @return Technician or null
     */
    @Override
    public Technician find(int technicianID)
    {
        for (Technician technician : entities)
            if (technician.getUid() == technicianID)
                return technician;
        return null;
    }

    /**
     * Return all Technicians
     *
     * @return Technicians
     */
    @Override
    public List<Technician> findAll()
    {
        ArrayList<Technician> result = new ArrayList<Technician>();
        result.addAll(entities);
        return result;
    }

    /**
     * Return the next id for a Technician.
     *
     * @return Technician's id
     */
    @Override
    public int nextId()
    {
        return (entities.size() > 0 ? entities.get(entities.size() - 1).getUid() + 1 : 1);
    }
}
