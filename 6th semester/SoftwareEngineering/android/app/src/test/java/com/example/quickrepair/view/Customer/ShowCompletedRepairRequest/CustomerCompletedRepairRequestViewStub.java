package com.example.quickrepair.view.Customer.ShowCompletedRepairRequest;

public class CustomerCompletedRepairRequestViewStub implements CustomerCompletedRepairRequestView {
    private static final int ERROR = 1;
    private static final int NOT_PAID = 2;
    private static final int PAID = 3;
    private static final int PAY = 4;
    private static int state;

    private int sumOfsetter;
    @Override
    public void showError(String message) {
        state = ERROR;
    }

    @Override
    public void setJob(String job) {
        sumOfsetter++;
    }

    @Override
    public void setTechnicianName(String technicianName) {
        sumOfsetter++;
    }

    @Override
    public void setAddress(String Address) {
        sumOfsetter++;
    }

    @Override
    public void setComments(String comments) {
        sumOfsetter++;
    }

    @Override
    public void setConductionDate(String conductionDate) {
        sumOfsetter++;
    }

    @Override
    public void setEstimatedDuration(String estimatedDuration) {
        sumOfsetter++;
    }

    @Override
    public void setCost(String cost) {
        sumOfsetter++;
    }

    @Override
    public void setEvaluationData(String title, String comments, String rate) {
        state = PAID;
        sumOfsetter++;
    }

    @Override
    public void setPayAndEvaluationFields() {
        sumOfsetter++;
    }

    @Override
    public void setPayListener() {
        state = NOT_PAID;
        sumOfsetter++;
    }

    @Override
    public void donePayAndEvaluate() {
        state = PAY;
    }
    public int getSumOfsetter(){
        return sumOfsetter;
    }

    public static int getState() {
        return state;
    }

    public static void setState(int state) {
        CustomerCompletedRepairRequestViewStub.state = state;
    }
}
