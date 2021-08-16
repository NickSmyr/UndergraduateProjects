package com.example.quickrepair.view.RequestRepair;

import java.util.List;

public class RequestRepairViewStub implements RequestRepairView {
    private RequestRepairPresenter presenter;
    public void setPresenter(RequestRepairPresenter presenter){
        this.presenter = presenter;
    }
    @Override
    public void setTechnicianPhoneNumber(String text) {
        System.out.println("phonenumber " + text);
    }

    String technicianName;
    @Override
    public void setTechnicianName(String text) {
        System.out.println("techname " + text);
        technicianName = text;
    }

    String jobTypeName;
    @Override
    public void setJobTypeName(String text) {
        System.out.println("jobtypoename " + text);
        jobTypeName = text;
    }

    List<String> availableHours;
    @Override
    public void showTimesAvailable(List<String> availableHours) {
        System.out.println("javas " + availableHours);
        this.availableHours = availableHours;
    }

    String errorMessage = null;
    @Override
    public void showError(String error) {
        System.out.println("javas error " + error);
        errorMessage = error;
    }
    boolean exited = false;
    @Override
    public void submit() {
        exited = true;
    }

}
