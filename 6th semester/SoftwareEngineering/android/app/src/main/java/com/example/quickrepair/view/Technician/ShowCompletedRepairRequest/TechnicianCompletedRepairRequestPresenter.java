package com.example.quickrepair.view.Technician.ShowCompletedRepairRequest;

import com.example.quickrepair.dao.RepairRequestDAO;
import com.example.quickrepair.domain.Repair;
import com.example.quickrepair.domain.RepairRequest;
import com.example.quickrepair.memorydao.RepairRequestDAOMemory;
import com.example.quickrepair.util.Utilities;

public class TechnicianCompletedRepairRequestPresenter {

    private RepairRequestDAO repairRequestDAO;
    private TechnicianCompletedRepairRequestView view;
    private RepairRequest repairRequest;


    /**
     * search data for the repair request with the given id and show them
     * @param repairRequestId The repairRequest id
     */
    public void searchRepairRequestData(int repairRequestId){
        if(repairRequestId == 0){
            view.showError("Something went wrong");
            return;
        }

        this.repairRequest = repairRequestDAO.find(repairRequestId);

        if(this.repairRequest == null){
            view.showError("Something went wrong");
            return;
        }
        view.setJob(repairRequest.getJob().getJobType().getName());
        view.setConsumerName("From: " + "\n" + repairRequest.getCustomer().getUsername());
        view.setAddress("Address: " + "\n" + repairRequest.getAddress().toString());
        view.setComments("Comments: " + "\n" + repairRequest.getCommentsFromCustomer());
        view.setConductionDate("Date: " + "\n" + Utilities.getToString(repairRequest.getConductionDate()));
        view.setEstimatedDuration("Estimated Duration: " + "\n" + repairRequest.getEstimatedDuration());
        if(repairRequest.getRepair().getPayment() != null) {
            view.setCost("Cost: " + "\n" + repairRequest.getRepair().getPayment().getCost());
        }else{
            view.setCost("Cost: " + "\n" +" - ");
        }
    }

    /**
     * set repairRequestDAO
     * @param repairRequestDAO The RepairRequestDAO
     */
    public void setRepairRequestDAO(RepairRequestDAO repairRequestDAO) {
        this.repairRequestDAO = repairRequestDAO;
    }

    /**
     * set view
     * @param view The TechnicianCompletedRepairRequestView
     */
    public void setView(TechnicianCompletedRepairRequestView view) {
        this.view = view;
    }

    /**
     * clear view
     */
    public void clearView() {
        this.view = null;
    }

}
