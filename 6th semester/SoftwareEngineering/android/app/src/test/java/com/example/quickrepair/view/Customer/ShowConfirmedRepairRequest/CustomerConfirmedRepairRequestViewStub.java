package com.example.quickrepair.view.Customer.ShowConfirmedRepairRequest;

import com.example.quickrepair.view.Customer.ShowCompletedRepairRequest.CustomerCompletedRepairRequestView;

public class CustomerConfirmedRepairRequestViewStub implements CustomerConfirmedRepairRequestView {
    private static final int ERROR = 1;
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
    public void setAddress(String address) {
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

    public static int getState() {
        return state;
    }

    public int getSumOfsetter() {
        return sumOfsetter;
    }
}
