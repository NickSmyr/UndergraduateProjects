package com.example.quickrepair.view.Technician.AddEditArea;

import java.util.ArrayList;
import java.util.List;

public class AddEditAreaViewStub implements AddEditAreaView
{
    private String errorTitle;
    private String errorMessage;

    private ArrayList<String> areaList;
    private ArrayList<String> selectedArea;

    @Override
    public void setAreaList(List<String> areaList, String defaultName)
    {
        this.areaList = new ArrayList<>(areaList);
        this.areaList.add(0, defaultName);
    }

    @Override
    public void setSelectedArea(List<String> selectedArea)
    {
        this.selectedArea = new ArrayList<>(selectedArea);
    }

    @Override
    public void showErrorMessage(String title, String message)
    {
        errorTitle = title;
        errorMessage = message;
    }

    public String getErrorTitle()
    {
        return errorTitle;
    }

    public String getErrorMessage()
    {
        return errorMessage;
    }

    public ArrayList<String> getAreaList()
    {
        return areaList;
    }

    public ArrayList<String> getSelectedArea()
    {
        return selectedArea;
    }
}
