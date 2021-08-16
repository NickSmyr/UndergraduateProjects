package com.example.quickrepair.view.Technician.ShowConfirmedRepairRequest;

public class TechnicianConfirmedRepairRequestViewStub implements   TechnicianConfirmedRepairRequestView{
    private static final int COMPLETED = 1;
    private static final int ERROR = 2;
    private static int state;

    private int sumOfsetter = 0;
    @Override
    public void showError(String message) {
        state = ERROR;
    }

    @Override
    public void complete() {
        state = COMPLETED;
    }

    @Override
    public void setJob(String job) {
        sumOfsetter++;
    }

    @Override
    public void setConsumerName(String consumerName) {
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
    public void setButtonListeners() {
        sumOfsetter++;
    }

    public int getSumOfsetter() {
        return sumOfsetter;
    }
    public static int getState() {
        return state;
    }
}
