package com.example.quickrepair.view.Technician.RepairRequests;

import androidx.lifecycle.ViewModel;

import com.example.quickrepair.dao.RepairRequestDAO;
import com.example.quickrepair.memorydao.RepairRequestDAOMemory;

public class TechnicianRepairRequestsViewModel extends ViewModel
{
    private TechnicianRepairRequestsPresenter presenter;

    public TechnicianRepairRequestsViewModel()
    {
        super();
        //create Presenter
        presenter = new TechnicianRepairRequestsPresenter();

        RepairRequestDAOMemory repairRequestDAO = new RepairRequestDAOMemory();
        presenter.setRepairRequestDAO(repairRequestDAO);
    }

    public TechnicianRepairRequestsPresenter getPresenter()
    {
        return presenter;
    }

    @Override
    protected void onCleared()
    {
        super.onCleared();
        // avoid leaking activity for any reason
        presenter.clearView();
    }
}