package com.example.quickrepair.dao;


import com.example.quickrepair.domain.RepairRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface RepairRequestDAO {
    /**
     * Delete an RepairRequest
     * @param entity RepairRequest
     */
    void delete(RepairRequest entity);

    /**
     * Find all RepairRequest
     * @return RepairRequest
     */
    List<RepairRequest> findAll();

    /**
     * Save a RepairRequest
     * @param entity RepairRequest
     */
    void save(RepairRequest entity);

    /**
     * Find the RepairRequest with the given id
     * @param RepairRequestID RepairRequest's id
     * @return RepairRequest or null
     */
    RepairRequest find(int RepairRequestID);

    /**
     * Return all repair request for a Technician
     * @technicianID Technician's id
     * @status RepairRequest's status
     * @return RepairRequests for the particular Technician with the given status
     */
    ArrayList<RepairRequest> findAllForTechnicianByStatus(int technicianID, RepairRequest.Status status);

    /**
     * Return all repair request for a Customer
     * @customerID Consumer's id
     * @status RepairRequest's status
     * @return RepairRequests for the particular Consumer with the given status
     */
    ArrayList<RepairRequest> findAllForCustomerByStatus(int customerID, RepairRequest.Status status);

    /**
     * Return the next id for an RepairRequest.
     * @return RepairRequest's id
     */
    int nextId();
}
