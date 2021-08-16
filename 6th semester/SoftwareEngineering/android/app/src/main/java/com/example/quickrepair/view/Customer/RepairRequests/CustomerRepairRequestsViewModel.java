package com.example.quickrepair.view.Customer.RepairRequests;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModel;

import com.example.quickrepair.memorydao.RepairRequestDAOMemory;

public class CustomerRepairRequestsViewModel extends ViewModel
{
    private CustomerRepairRequestsPresenter presenter;

    public CustomerRepairRequestsViewModel()
    {
        super();
        //create Presenter
        presenter = new CustomerRepairRequestsPresenter();

        RepairRequestDAOMemory repairRequestDAO = new RepairRequestDAOMemory();
        presenter.setRepairRequestDAO(repairRequestDAO);
    }

    public CustomerRepairRequestsPresenter getPresenter()
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
