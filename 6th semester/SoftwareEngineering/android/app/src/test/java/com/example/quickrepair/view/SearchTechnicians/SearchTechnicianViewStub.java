package com.example.quickrepair.view.SearchTechnicians;

import java.util.ArrayList;
import java.util.List;

public class SearchTechnicianViewStub implements SearchTechniciansView
{
    private String error;
    private ArrayList<Integer> specialtyIds;
    private ArrayList<String> specialtyNames;
    private ArrayList<Integer> jobTypeIds;
    private ArrayList<String> jobTypeNames;
    private ArrayList<String> areas;
    private ArrayList<Integer> technicianIds;
    private ArrayList<String> technicianNames;
    private ArrayList<Double> averageRatings;
    private ArrayList<Double> prices;

    private int technicianId;
    private int jobTypeId;
    private int year;
    private int month;
    private int dayOfMonth;

    private boolean toLogin = false;

    @Override
    public void setJobTypeSpinnerEnabled(boolean b)
    {

    }

    @Override
    public void showErrorMessage(String errorMessage)
    {
        this.error = errorMessage;
    }

    @Override
    public void setSpecialtiesSource(List<Integer> specialtyIds, List<String> specialtyNames)
    {
        this.specialtyIds = new ArrayList<>(specialtyIds);
        this.specialtyNames = new ArrayList<>(specialtyNames);
    }

    @Override
    public void setJobTypesSource(List<Integer> jobTypeIds, List<String> jobTypeNames)
    {
        this.jobTypeIds = new ArrayList<>(jobTypeIds);
        this.jobTypeNames = new ArrayList<>(jobTypeNames);
    }

    @Override
    public void setAreasSource(List<String> areas)
    {
        this.areas = new ArrayList<>(areas);
    }

    @Override
    public void populateTechnicianList(List<Integer> technicianIds, List<String> technicianNames, List<Double> averageRatings, List<Double> prices)
    {
        this.technicianIds = new ArrayList<>(technicianIds);
        this.technicianNames = new ArrayList<>(technicianNames);
        this.averageRatings = new ArrayList<>(averageRatings);
        this.prices = new ArrayList<>(prices);
    }

    @Override
    public void navigateToRequestRepair(int technicianId, int jobTypeId, int year, int month, int dayOfMonth)
    {
        this.technicianId = technicianId;
        this.jobTypeId = jobTypeId;
        this.year = year;
        this.month = month;
        this.dayOfMonth = dayOfMonth;
        toLogin = false;
    }

    @Override
    public void navigateToLogin(int technicianId, int jobTypeId, int year, int month, int dayOfMonth)
    {
        this.technicianId = technicianId;
        this.jobTypeId = jobTypeId;
        this.year = year;
        this.month = month;
        this.dayOfMonth = dayOfMonth;
        toLogin = true;
    }

    public String getError()
    {
        return error;
    }

    public ArrayList<Integer> getSpecialtyIds()
    {
        return specialtyIds;
    }

    public ArrayList<String> getSpecialtyNames()
    {
        return specialtyNames;
    }

    public ArrayList<Integer> getJobTypeIds()
    {
        return jobTypeIds;
    }

    public ArrayList<String> getJobTypeNames()
    {
        return jobTypeNames;
    }

    public ArrayList<String> getAreas()
    {
        return areas;
    }

    public ArrayList<Integer> getTechnicianIds()
    {
        return technicianIds;
    }

    public ArrayList<String> getTechnicianNames()
    {
        return technicianNames;
    }

    public ArrayList<Double> getAverageRatings()
    {
        return averageRatings;
    }

    public ArrayList<Double> getPrices()
    {
        return prices;
    }

    public int getTechnicianId()
    {
        return technicianId;
    }

    public int getJobTypeId()
    {
        return jobTypeId;
    }

    public int getYear()
    {
        return year;
    }

    public int getMonth()
    {
        return month;
    }

    public int getDayOfMonth()
    {
        return dayOfMonth;
    }

    public boolean isToLogin()
    {
        return toLogin;
    }
}
