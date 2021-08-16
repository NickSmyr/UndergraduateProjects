package com.example.quickrepair.view.Technician.AddEditJob;

import com.example.quickrepair.dao.TechnicianDAO;
import com.example.quickrepair.memorydao.TechnicianDAOMemory;
import com.example.quickrepair.view.Base.BaseViewModel;

public class AddEditJobViewModel extends BaseViewModel<AddEditJobPresenter>
{
    /**
     * Create and initialize the presenter.
     *
     * @return The initialized presenter.
     */
    @Override
    protected AddEditJobPresenter createPresenter()
    {
        TechnicianDAO technicianDAO = new TechnicianDAOMemory();

        AddEditJobPresenter presenter = new AddEditJobPresenter();
        presenter.setTechnicianDAO(technicianDAO);

        return presenter;
    }
}
