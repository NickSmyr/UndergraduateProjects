package com.example.quickrepair.view.Technician.RegisterTechnician;

import com.example.quickrepair.dao.CustomerDAO;
import com.example.quickrepair.dao.SpecialtyDAO;
import com.example.quickrepair.dao.TechnicianDAO;
import com.example.quickrepair.memorydao.CustomerDAOMemory;
import com.example.quickrepair.memorydao.SpecialtyDAOMemory;
import com.example.quickrepair.memorydao.TechnicianDAOMemory;
import com.example.quickrepair.view.Base.BaseViewModel;

public class TechnicianRegisterViewModel extends BaseViewModel<TechnicianRegisterPresenter>
{
    /**
     * Create and initialize the presenter.
     *
     * @return The initialized presenter.
     */
    @Override
    protected TechnicianRegisterPresenter createPresenter()
    {
        CustomerDAO customerDAO = new CustomerDAOMemory();
        TechnicianDAO technicianDAO = new TechnicianDAOMemory();
        SpecialtyDAO specialtyDAO = new SpecialtyDAOMemory();

        TechnicianRegisterPresenter presenter = new TechnicianRegisterPresenter();
        presenter.setCustomerDAO(customerDAO);
        presenter.setTechnicianDAO(technicianDAO);
        presenter.setSpecialtyDAO(specialtyDAO);

        return presenter;
    }
}
