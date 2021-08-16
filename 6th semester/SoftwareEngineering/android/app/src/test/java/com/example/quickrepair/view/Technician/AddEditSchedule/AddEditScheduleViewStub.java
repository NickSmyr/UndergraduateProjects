package com.example.quickrepair.view.Technician.AddEditSchedule;

public class AddEditScheduleViewStub implements AddEditScheduleView
{
    private Integer[][] schedule = new Integer[7][2];
    private String errorTitle;
    private String errorMessage;

    @Override
    public void setDay(int day, int start, int end)
    {
        schedule[day][0] = start;
        schedule[day][1] = end;
    }

    public Integer[] getDay(int day)
    {
        return schedule[day];
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
}
