package com.example.quickrepair.view.Customer.ShowConfirmedRepairRequest;

import com.example.quickrepair.dao.RepairRequestDAO;
import com.example.quickrepair.memorydao.MemoryInitializer;
import com.example.quickrepair.memorydao.RepairRequestDAOMemory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CustomerConfirmedRepairRequestTest {

    CustomerConfirmedRepairRequestViewStub view;
    CustomerConfirmedRepairRequestPresenter presenter;

    private RepairRequestDAO repairRequestDAO;

    /**
     * Αρχικοποίηση
     */
    @Before
    public void setUp(){
        view = new CustomerConfirmedRepairRequestViewStub();
        presenter = new CustomerConfirmedRepairRequestPresenter();
        presenter.setView(view);
        new MemoryInitializer().prepareData();
        repairRequestDAO = new RepairRequestDAOMemory();
        presenter.setRepairRequestDAO((RepairRequestDAOMemory) repairRequestDAO);
        presenter.searchRepairRequestData(3);

    }

    /**
     * Έλεγχος στην αναζητηση για Ολοκληρωμένο Μη πληρωμένο ραντεβού
     * γίνεται ορθά η εμφάνιση των απαραίτητων στοιχείων
     */
    @Test
    public void checkSetters(){
        Assert.assertEquals(6, view.getSumOfsetter());
    }

    /**
     * Έλεγχος οτι δε γίνεται αναζήτηση με μηδενικό αναγνωριστικό
     * (Για κάλυψη πιθανότητας σφάλματος του συστήματος)
     */
    @Test
    public void checkSearchErrorZero(){
        presenter.searchRepairRequestData(0);
        Assert.assertEquals(1, view.getState());
    }

    /**
     * Έλεγχος οτι δε γίνεται αναζήτηση με μη υπαρκτό αναγνωριστικό
     * (Για κάλυψη πιθανότητας σφάλματος του συστήματος)
     */
    @Test
    public void checkSearchError(){
        presenter.searchRepairRequestData(100);
        Assert.assertEquals(1, view.getState());
    }

}
