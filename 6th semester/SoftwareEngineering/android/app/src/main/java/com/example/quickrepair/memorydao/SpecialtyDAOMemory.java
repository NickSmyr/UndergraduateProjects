package com.example.quickrepair.memorydao;

import com.example.quickrepair.dao.SpecialtyDAO;
import com.example.quickrepair.domain.Specialty;

import java.util.ArrayList;
import java.util.List;

public class SpecialtyDAOMemory implements SpecialtyDAO
{
    protected static ArrayList<Specialty> entities = new ArrayList<>();

    /**
     * Delete an Specialty
     *
     * @param entity Specialty
     */
    @Override
    public void delete(Specialty entity)
    {
        entities.remove(entity);
    }

    /**
     * Find all Specialties
     *
     * @return Specialties
     */
    @Override
    public List<Specialty> findAll()
    {
        ArrayList<Specialty> result = new ArrayList<>();
        result.addAll(entities);
        return result;
    }

    /**
     * Save a Specialty
     *
     * @param entity Specialty
     */
    @Override
    public void save(Specialty entity)
    {
        entities.add(entity);
    }

    /**
     * Find the Specialty with the given id
     *
     * @param specialtyID Specialty's id
     * @return Specialty or null
     */
    @Override
    public Specialty find(int specialtyID)
    {
        for (Specialty specialty : entities)
            if (specialty.getUid() == specialtyID)
                return specialty;
        return null;
    }

    /**
     * Return the next id for a Specialty.
     *
     * @return Specialty's id
     */
    @Override
    public int nextId()
    {
        return (entities.size() > 0 ? entities.get(entities.size() - 1).getUid() + 1 : 1);
    }
}
