package com.example.quickrepair.view.Technician.ShowCompletedRepairRequest;

public class TechnicianCompletedRepairRequestViewStub implements TechnicianCompletedRepairRequestView {
    private static final int ERROR = 1;
    private static int state;

    private int sumOfsetter = 0;
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
    public void setEstimatedDuration(String estimatedDuration) {
        sumOfsetter++;
    }

    @Override
    public void setCost(String cost) {
        sumOfsetter++;
    }
    public int getSumOfsetter() {
        return sumOfsetter;
    }
    public static int getState() {
        return state;
    }
}
