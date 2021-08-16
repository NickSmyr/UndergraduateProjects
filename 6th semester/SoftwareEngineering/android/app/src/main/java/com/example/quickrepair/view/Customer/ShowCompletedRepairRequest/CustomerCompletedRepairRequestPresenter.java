package com.example.quickrepair.view.Customer.ShowCompletedRepairRequest;

import com.example.quickrepair.dao.RepairRequestDAO;
import com.example.quickrepair.domain.Evaluation;
import com.example.quickrepair.domain.PaymentType;
import com.example.quickrepair.domain.RepairRequest;
import com.example.quickrepair.util.Utilities;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class CustomerCompletedRepairRequestPresenter {
    private RepairRequestDAO repairRequestDAO;
    private CustomerCompletedRepairRequestView view;
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
        view.setTechnicianName("From: " + "\n" + repairRequest.getCustomer().getUsername());
        view.setAddress("Address: " + "\n" + repairRequest.getAddress().toString());
        view.setComments("Comments: " + "\n" + repairRequest.getCommentsFromCustomer());
        view.setConductionDate("Date: " + "\n" + Utilities.getToString(repairRequest.getConductionDate()));
        view.setEstimatedDuration("Estimated Duration: " + "\n" + repairRequest.getEstimatedDuration());

        view.setCost("Cost: " + "\n" + repairRequest.getRepair().calculateCost());

        if(repairRequest.getRepair().getPayment() != null ) {
            Evaluation evaluation= repairRequest.getRepair().getEvaluation();
            if(evaluation!=null) {
                view.setEvaluationData("Evaluation's title:" + "\n" + evaluation.getTitle(),
                        "Evaluation's comments: " + "\n" + evaluation.getComment(),
                        "Evaluation's rate: "+evaluation.getRate());
            }
        }else{
            view.setPayAndEvaluationFields();
            view.setPayListener();
        }
    }

    /**
     * Response to customer payment and evaluation
     * @param title evaluation's title
     * @param comments evaluation's comments
     * @param rate evaluation's rate
     */
    public void payAndEvaluate(String title, String comments, int rate){
        if(title != null && title.length() > 0 && comments != null && comments.length() > 0 ) {
            // pay
            GregorianCalendar now = (GregorianCalendar) Calendar.getInstance();
            repairRequest.getRepair().pay(now, PaymentType.CARD);
            //evaluate
            repairRequest.getRepair().evaluate(title ,comments, rate);
            //done
            view.donePayAndEvaluate();
        }else{
            view.showError("Please enter an evaluation");
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
     * @param view The CustomerCompletedRepairRequestView
     */
    public void setView(CustomerCompletedRepairRequestView view) {
        this.view = view;
    }

    /**
     * clear view
     */
    public void clearView() {
        this.view = null;
    }

}
