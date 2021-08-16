package com.example.quickrepair.view.Customer.ShowCompletedRepairRequest;

import androidx.lifecycle.ViewModel;

import com.example.quickrepair.domain.Customer;
import com.example.quickrepair.memorydao.RepairRequestDAOMemory;

public class CustomerCompletedRepairRequestViewModel extends ViewModel {
    private CustomerCompletedRepairRequestPresenter presenter;

    public CustomerCompletedRepairRequestViewModel(){
        super();
        //create Presenter
        presenter = new CustomerCompletedRepairRequestPresenter();

        RepairRequestDAOMemory repairRequestDAO = new RepairRequestDAOMemory();
        presenter.setRepairRequestDAO(repairRequestDAO);
    }

    public CustomerCompletedRepairRequestPresenter getPresenter() {
        return presenter;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        // avoid leaking activity for any reason
        presenter.clearView();
    }
}
