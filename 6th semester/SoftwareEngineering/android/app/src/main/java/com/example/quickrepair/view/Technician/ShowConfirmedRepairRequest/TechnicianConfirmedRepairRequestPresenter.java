package com.example.quickrepair.view.Technician.ShowConfirmedRepairRequest;

import android.util.Log;

import com.example.quickrepair.dao.RepairRequestDAO;
import com.example.quickrepair.domain.RepairRequest;
import com.example.quickrepair.memorydao.RepairRequestDAOMemory;
import com.example.quickrepair.util.Utilities;

public class TechnicianConfirmedRepairRequestPresenter {
        private TechnicianConfirmedRepairRequestView view;
        private RepairRequestDAO repairRequestDAO;
        private RepairRequest repairRequest;

        TechnicianConfirmedRepairRequestPresenter(){}

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

            view.setButtonListeners();
        }

    /**
     * set repair request as completed and add quantity
     * then go to page with repairRequests
     * @param quantity The quantity
     */
    public void setCompleted(String quantity) {
            try{
                int quantityInt = Integer.parseInt(quantity);
                repairRequest.complete(quantityInt);
                view.complete();
            }catch (Exception e){
                e.printStackTrace();
                view.showError("Please enter estimated duration(minutes)");
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
         * @param view The TechnicianConfirmedRepairRequestView
         */
        public void setView(TechnicianConfirmedRepairRequestView view) {
            this.view = view;
        }

        /**
         * clear view
         */
        public void clearView() {
            this.view = null;
        }
}
