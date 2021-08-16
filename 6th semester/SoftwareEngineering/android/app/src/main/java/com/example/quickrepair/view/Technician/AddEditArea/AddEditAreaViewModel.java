package com.example.quickrepair.view.Technician.AddEditArea;

import com.example.quickrepair.dao.AreaDAO;
import com.example.quickrepair.dao.TechnicianDAO;
import com.example.quickrepair.memorydao.AreaDAOMemory;
import com.example.quickrepair.memorydao.TechnicianDAOMemory;
import com.example.quickrepair.view.Base.BaseViewModel;

public class AddEditAreaViewModel extends BaseViewModel<AddEditAreaPresenter>
{
    /**
     * Create and initialize the presenter.
     *
     * @return The initialized presenter.
     */
    @Override
    protected AddEditAreaPresenter createPresenter()
    {
        TechnicianDAO technicianDAO = new TechnicianDAOMemory();
        AreaDAO areaDAO = new AreaDAOMemory();

        AddEditAreaPresenter presenter = new AddEditAreaPresenter();
        presenter.setTechnicianDAO(technicianDAO);
        presenter.setAreaDAO(areaDAO);

        return presenter;
    }
}
