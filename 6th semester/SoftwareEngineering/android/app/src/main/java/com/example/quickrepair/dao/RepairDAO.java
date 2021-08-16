package com.example.quickrepair.dao;


import com.example.quickrepair.domain.Repair;

import java.util.List;

public interface RepairDAO {
    /**
     * Delete an Repair
     * @param entity Repair
     */
    void delete(Repair entity);

    /**
     * Find all Repairs
     * @return Repairs
     */
    List<Repair> findAll();

    /**
     * Save a Repair
     * @param entity Repair
     */
    void save(Repair entity);

    /**
     * Find the Repair with the given id
     * @param repairID Repair's id
     * @return Repair or null
     */
    Repair find(int repairID);

    /**
     * Return the next id for an Repair.
     * @return Repair's id
     */
    int nextId();
}
