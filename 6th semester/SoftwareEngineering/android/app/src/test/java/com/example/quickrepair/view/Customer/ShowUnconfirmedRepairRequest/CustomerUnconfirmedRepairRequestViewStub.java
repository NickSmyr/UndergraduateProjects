package com.example.quickrepair.view.Customer.ShowUnconfirmedRepairRequest;

public class CustomerUnconfirmedRepairRequestViewStub implements CustomerUnconfirmedRepairRequestView{
    private static int ERROR = 1;

    private int state;
    private int sumOfsetters;
    @Override
    public void showError(String message) {
        state = ERROR;
    }

    @Override
    public void setJob(String job) {
        sumOfsetters++;
    }

    @Override
    public void setTechnicianName(String technicianName) {
        sumOfsetters++;
    }

    @Override
    public void setAddress(String address) {
        sumOfsetters++;
    }

    @Override
    public void setComments(String comments) {
        sumOfsetters++;
    }

    @Override
    public void setConductionDate(String conductionDate) {
        sumOfsetters++;
    }

    public int getSumOfsetter() {
        return sumOfsetters;
    }

    public int getState() {
        return state;
    }
}
