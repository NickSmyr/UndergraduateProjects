package com.example.quickrepair.view.Technician.ShowUnconfirmedRepairRequest;

import com.example.quickrepair.view.Technician.ShowUnconfirmedRepairRequest.TechnicianUnconfirmedRepairRequestView;

public class TechnicianUnconfirmedRepairRequestViewStub implements TechnicianUnconfirmedRepairRequestView {

    private static final int REJECTED = 1;
    private static final int CONFIRM = 2;
    private static final int ERROR = 3;
    private static int state;

    private int sumOfsetter = 0;

    @Override
    public void reject() {
        state = REJECTED;
    }

    @Override
    public void confirm() {
        state = CONFIRM;
    }

    @Override
    public void showError(String message) {
        state = ERROR;
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
    public void setButtonsListeners() {
        sumOfsetter++;
    }

    public int getSumOfsetter() {
        return sumOfsetter;
    }

    public static int getState() {
        return state;
    }
}