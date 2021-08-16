package com.example.quickrepair.view.Technician.ShowUnconfirmedRepairRequest;

import androidx.lifecycle.ViewModel;

import com.example.quickrepair.memorydao.RepairRequestDAOMemory;

public class TechnicianUnconfirmedRepairRequestViewModel extends ViewModel {
    private TechnicianUnconfirmedRepairRequestPresenter presenter;

    public TechnicianUnconfirmedRepairRequestViewModel(){
        super();
        //create Presenter
        presenter = new TechnicianUnconfirmedRepairRequestPresenter();
        RepairRequestDAOMemory repairRequestDAOMemory = new RepairRequestDAOMemory();
        presenter.setRepairRequestDAOMemory(repairRequestDAOMemory);
    }

    public TechnicianUnconfirmedRepairRequestPresenter getPresenter() {
        return presenter;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        // clear view instance to avoid leaking activity
        presenter.clearView();
    }
}
