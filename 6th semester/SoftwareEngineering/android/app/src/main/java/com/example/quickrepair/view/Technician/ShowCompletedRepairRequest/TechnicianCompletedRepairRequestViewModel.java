package com.example.quickrepair.view.Technician.ShowCompletedRepairRequest;

import androidx.lifecycle.ViewModel;

import com.example.quickrepair.memorydao.RepairRequestDAOMemory;

public class TechnicianCompletedRepairRequestViewModel extends ViewModel {
    private TechnicianCompletedRepairRequestPresenter presenter;

    public TechnicianCompletedRepairRequestViewModel(){
        super();
        //create Presenter
        presenter = new TechnicianCompletedRepairRequestPresenter();

        RepairRequestDAOMemory repairRequestDAO = new RepairRequestDAOMemory();
        presenter.setRepairRequestDAO(repairRequestDAO);
    }

    public TechnicianCompletedRepairRequestPresenter getPresenter() {
        return presenter;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        // avoid leaking activity for any reason
        presenter.clearView();
    }
}
