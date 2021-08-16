package com.example.quickrepair.memorydao;

import com.example.quickrepair.dao.RepairRequestDAO;
import com.example.quickrepair.domain.RepairRequest;

import java.util.ArrayList;
import java.util.List;

public class RepairRequestDAOMemory implements RepairRequestDAO {
    protected static ArrayList<RepairRequest> entities = new ArrayList<RepairRequest>();

    /**
     * Delete an RepairRequest
     * @param entity RepairRequest
     */
    @Override
    public void delete(RepairRequest entity)
    {
        entities.remove(entity);
    }

    /**
     * Find all RepairRequest
     * @return RepairRequest
     */
    @Override
    public List<RepairRequest> findAll()
    {
        ArrayList<RepairRequest> result = new ArrayList<RepairRequest>();
        result.addAll(entities);
        return result;
    }

    /**
     * Save a RepairRequest
     * @param entity RepairRequest
     */
    @Override
    public void save(RepairRequest entity)
    {
        entities.add(entity);
    }

    /**
     * Find the RepairRequest with the given id
     * @param RepairRequestID RepairRequest's id
     * @return RepairRequest or null
     */
    @Override
    public RepairRequest find(int RepairRequestID)
    {
        for(RepairRequest repairRequest : entities)
            if(repairRequest.getUid() == RepairRequestID)
                return repairRequest;
        return null;
    }

    /**
     * Return all repair request for a Technician
     * @param technicianID
     * @param status
     * @return RepairRequests for the particular Technician with the given status
     * @technicianID Technician's id
     * @status RepairRequest's status
     */
    @Override
    public ArrayList<RepairRequest> findAllForTechnicianByStatus(int technicianID, RepairRequest.Status status)
    {
        ArrayList<RepairRequest> result = new ArrayList<RepairRequest>();
        for(RepairRequest repairRequest : entities) {
            if (repairRequest.getJob().getTechnician().getUid() == technicianID && repairRequest.getStatus() == status) {
                result.add(repairRequest);
            }
        }
        return result;
    }

    /**
     * Return all repair request for a Customer
     * @param customerID
     * @param status
     * @return RepairRequests for the particular Consumer with the given status
     * @customerID Consumer's id
     * @status RepairRequest's status
     */
    @Override
    public ArrayList<RepairRequest> findAllForCustomerByStatus(int customerID, RepairRequest.Status status)
    {
        ArrayList<RepairRequest> result = new ArrayList<RepairRequest>();
        for(RepairRequest repairRequest : entities)
            if(repairRequest.getCustomer().getUid() == customerID && repairRequest.getStatus()==status)
                result.add(repairRequest);
        return result;
    }

    /**
     * Return the next id for an RepairRequest.
     * @return RepairRequest's id
     */
    @Override
    public int nextId()
    {
        return (entities.size() > 0 ? entities.get(entities.size()-1).getUid()+1 : 1);
    }
}
