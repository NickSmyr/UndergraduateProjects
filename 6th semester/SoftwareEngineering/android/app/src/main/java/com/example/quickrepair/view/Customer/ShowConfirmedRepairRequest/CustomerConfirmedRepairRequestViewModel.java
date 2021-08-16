package com.example.quickrepair.view.Customer.ShowConfirmedRepairRequest;

import androidx.lifecycle.ViewModel;

import com.example.quickrepair.memorydao.RepairRequestDAOMemory;
import com.example.quickrepair.view.Technician.ShowConfirmedRepairRequest.TechnicianConfirmedRepairRequestPresenter;

public class CustomerConfirmedRepairRequestViewModel extends ViewModel {
    private CustomerConfirmedRepairRequestPresenter presenter;

    public CustomerConfirmedRepairRequestViewModel(){
        super();
        //create Presenter
        presenter = new CustomerConfirmedRepairRequestPresenter();
        RepairRequestDAOMemory repairRequestDAOMemory = new RepairRequestDAOMemory();
        presenter.setRepairRequestDAO(repairRequestDAOMemory);
    }

    public CustomerConfirmedRepairRequestPresenter getPresenter() {
        return presenter;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        // clear view instance to avoid leaking activity
        presenter.clearView();
    }
}
