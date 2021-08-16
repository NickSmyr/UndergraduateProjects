package com.example.quickrepair.view.Technician.RepairRequests;

import com.example.quickrepair.dao.RepairRequestDAO;
import com.example.quickrepair.domain.RepairRequest;
import com.example.quickrepair.memorydao.RepairRequestDAOMemory;

import java.util.ArrayList;


public class TechnicianRepairRequestsPresenter
{
    private TechnicianRepairRequestsView view;
    private RepairRequestDAO repairRequestDAO;
    private ArrayList<RepairRequest> repairRequests;

    public TechnicianRepairRequestsPresenter()
    {
    }

    /**
     * Responds to the technician selecting search repair requests given customer ID and status
     * @param technicianID given technicianID
     * @param status given repairRequest's status
     */
    public ArrayList<RepairRequest> searchRepairRequests(int technicianID, RepairRequest.Status status)
    {
        if (technicianID != 0 && status != null)
        {
            //get repair requests from DAO
            repairRequests = this.repairRequestDAO.findAllForTechnicianByStatus(technicianID, status);
            return repairRequests;
        }

        return null;
    }

    /**
     * Responds to the customer selecting a unconfirmed repair request
     * @param repairRequest  repairRequest
     */
    public void onRepairRequestSelectedUnconfirmed(RepairRequest repairRequest)
    {
        if (repairRequest == null || repairRequest.getUid() == 0)
        {
            view.showError("Something went wrong");
        }
        else
        {
            view.returnRepairRequestUnconfirmed(repairRequest.getUid());
        }
    }

    /**
     * Responds to the technician selecting a confirmed repair request
     * @param repairRequest  repairRequest
     */
    public void onRepairRequestSelectedConfirmed(RepairRequest repairRequest)
    {
        if (repairRequest == null || repairRequest.getUid() == 0)
        {
            view.showError("Something went wrong");
        }
        else
        {
            view.returnRepairRequestConfirmed(repairRequest.getUid());
        }
    }

    /**
     * Responds to the technician selecting a completed repair request
     * @param repairRequest repairRequest
     */
    public void onRepairRequestSelectedCompleted(RepairRequest repairRequest)
    {
        if (repairRequest == null || repairRequest.getUid() == 0)
        {
            view.showError("Something went wrong");
        }
        else
        {
            view.returnRepairRequestCompleted(repairRequest.getUid());
        }
    }

    /**
     * Responds to the customer clicking edit data
     */
    public void onEditDataPage()
    {
        view.editData();
    }

    /**
     * set repair request DAO
     * @param repairRequestDAO The RepairRequestDAO
     */
    public void setRepairRequestDAO(RepairRequestDAO repairRequestDAO)
    {
        this.repairRequestDAO = repairRequestDAO;
    }

    /**
     * set view
     * @param view The CustomerRepairRequestsView
     */
    public void setView(TechnicianRepairRequestsView view)
    {
        this.view = view;
    }

    /**
     * clear view
     */
    public void clearView()
    {
        this.view = null;
    }

}
