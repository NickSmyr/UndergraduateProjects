package com.example.quickrepair.view.SearchTechnicians;

import androidx.lifecycle.ViewModel;

import com.example.quickrepair.dao.Initializer;
import com.example.quickrepair.memorydao.AreaDAOMemory;
import com.example.quickrepair.memorydao.JobTypeDAOMemory;
import com.example.quickrepair.memorydao.MemoryInitializer;
import com.example.quickrepair.memorydao.SpecialtyDAOMemory;
import com.example.quickrepair.memorydao.TechnicianDAOMemory;

public class SearchTechniciansViewModel extends ViewModel {

    SearchTechniciansPresenter presenter;

    public SearchTechniciansViewModel() {
        presenter = new SearchTechniciansPresenter(new TechnicianDAOMemory(), new SpecialtyDAOMemory()
        , new JobTypeDAOMemory(), new AreaDAOMemory());

    }


    public SearchTechniciansPresenter getPresenter() {
        return presenter;
    }

}
