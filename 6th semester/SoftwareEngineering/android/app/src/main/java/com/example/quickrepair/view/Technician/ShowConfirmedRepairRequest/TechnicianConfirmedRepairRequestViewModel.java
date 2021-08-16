package com.example.quickrepair.view.Technician.ShowConfirmedRepairRequest;

import androidx.lifecycle.ViewModel;

import com.example.quickrepair.memorydao.RepairRequestDAOMemory;

public class TechnicianConfirmedRepairRequestViewModel extends ViewModel {

    private TechnicianConfirmedRepairRequestPresenter presenter;

    public TechnicianConfirmedRepairRequestViewModel(){
        super();
        //create Presenter
        presenter = new TechnicianConfirmedRepairRequestPresenter();
        RepairRequestDAOMemory repairRequestDAOMemory = new RepairRequestDAOMemory();
        presenter.setRepairRequestDAO(repairRequestDAOMemory);
    }

    public TechnicianConfirmedRepairRequestPresenter getPresenter() {
        return presenter;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        // clear view instance to avoid leaking activity
        presenter.clearView();
    }

}
