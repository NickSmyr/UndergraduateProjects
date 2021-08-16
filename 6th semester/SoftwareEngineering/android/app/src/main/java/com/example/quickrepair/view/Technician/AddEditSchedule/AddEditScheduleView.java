package com.example.quickrepair.view.Technician.AddEditSchedule;

import com.example.quickrepair.view.Base.BaseView;

public interface AddEditScheduleView extends BaseView
{
    /**
     * Set the starting and ending hour of the given day.
     *
     * @param day The day to set the hours.
     * @param start The starting hour.
     * @param end The ending hour.
     */
    void setDay(int day, int start, int end);
}
