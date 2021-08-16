package com.example.quickrepair.view.Customer.ShowUnconfirmedRepairRequest;

import androidx.lifecycle.ViewModel;

import com.example.quickrepair.memorydao.RepairRequestDAOMemory;

public class CustomerUnconfirmedRepairRequestViewModel extends ViewModel {
    private CustomerUnconfirmedRepairRequestPresenter presenter;

    public CustomerUnconfirmedRepairRequestViewModel(){
        super();
        //create Presenter
        presenter = new CustomerUnconfirmedRepairRequestPresenter();
        RepairRequestDAOMemory repairRequestDAOMemory = new RepairRequestDAOMemory();
        presenter.setRepairRequestDAO(repairRequestDAOMemory);
    }

    public CustomerUnconfirmedRepairRequestPresenter getPresenter() {
        return presenter;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        // clear view instance to avoid leaking activity
        presenter.clearView();
    }
}
