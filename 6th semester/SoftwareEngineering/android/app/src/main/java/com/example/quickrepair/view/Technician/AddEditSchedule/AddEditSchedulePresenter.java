package com.example.quickrepair.view.Technician.AddEditSchedule;

import com.example.quickrepair.dao.TechnicianDAO;
import com.example.quickrepair.domain.Schedule;
import com.example.quickrepair.domain.Technician;
import com.example.quickrepair.view.Base.BasePresenter;

import java.util.Calendar;

public class AddEditSchedulePresenter extends BasePresenter<AddEditScheduleView>
{
    private TechnicianDAO technicianDAO;

    private Technician technician;

    /**
     * Set the technician DAO for the presenter.
     *
     * @param technicianDAO The technician DAO.
     */
    public void setTechnicianDAO(TechnicianDAO technicianDAO)
    {
        this.technicianDAO = technicianDAO;
    }

    /**
     * Load the technician in case of edit.
     *
     * @param id The technician's id.
     */
    public void setTechnician(int id)
    {
        technician = technicianDAO.find(id);
    }

    /**
     * Initialize the view.
     */
    public void setUpDataSource()
    {
        for (int i = 0; i < 7; ++i)
        {
            Schedule.ScheduleEntry entry = technician.getSchedule(Calendar.SUNDAY + i);

            view.setDay(i, entry.getStartingHour(), entry.getEndingHour());
        }
    }

    /**
     * Apply the new schedule to the technician.
     *
     * @param schedule The new schedule of the technician.
     */
    void submit(Integer[][] schedule)
    {
        try
        {
            technician.setSchedule(schedule);
        }
        catch (IllegalArgumentException | NullPointerException e)
        {
            view.showErrorMessage("Invalid value", e.getMessage());
        }
    }
}
