package com.example.quickrepair.view.Technician.AddEditSchedule;

import com.example.quickrepair.dao.TechnicianDAO;
import com.example.quickrepair.memorydao.TechnicianDAOMemory;
import com.example.quickrepair.view.Base.BaseViewModel;

public class AddEditScheduleViewModel extends BaseViewModel<AddEditSchedulePresenter>
{
    /**
     * Create and initialize the presenter.
     *
     * @return The initialized presenter.
     */
    @Override
    protected AddEditSchedulePresenter createPresenter()
    {
        TechnicianDAO technicianDAO = new TechnicianDAOMemory();

        AddEditSchedulePresenter presenter = new AddEditSchedulePresenter();
        presenter.setTechnicianDAO(technicianDAO);

        return presenter;
    }
}
