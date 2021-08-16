package com.example.quickrepair.dao;


import com.example.quickrepair.domain.Customer;
import com.example.quickrepair.domain.RepairRequest;
import com.example.quickrepair.domain.Technician;

import java.util.List;
import java.util.Set;

public interface TechnicianDAO {
    /**
     * Delete a Technician
     * @param entity Technician
     */
    void delete(Technician entity);

    /**
     * Save a Technician
     * @param entity Technician
     */
    void save(Technician entity);

    /**
     * Find a Technician by id
     * @param technicianID Technician's id
     * @return Technician or null
     */
    Technician find(int technicianID);

    /**
     * Return all Technicians
     * @return Technicians
     */
    List<Technician> findAll();

    /**
     * Return the next id for a Technician.
     * @return Technician's id
     */
    int nextId();

}
