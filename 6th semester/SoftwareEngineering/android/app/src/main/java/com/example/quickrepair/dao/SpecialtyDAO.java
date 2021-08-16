package com.example.quickrepair.dao;

import com.example.quickrepair.domain.Specialty;

import java.util.List;

public interface SpecialtyDAO {

    /**
     * Delete an Specialty
     * @param entity Specialty
     */
    void delete(Specialty entity);

    /**
     * Find all Specialties
     * @return Specialties
     */
    List<Specialty> findAll();

    /**
     * Save a Specialty
     * @param entity Specialty
     */
    void save(Specialty entity);

    /**
     * Find the Specialty with the given id
     * @param specialtyID Specialty's id
     * @return Specialty or null
     */
    Specialty find(int specialtyID);

    /**
     * Return the next id for a Specialty.
     * @return Specialty's id
     */
    int nextId();
}
