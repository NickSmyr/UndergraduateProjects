package com.example.quickrepair.view.Technician.ShowCompletedRepairRequest;

import com.example.quickrepair.dao.RepairRequestDAO;
import com.example.quickrepair.memorydao.MemoryInitializer;
import com.example.quickrepair.memorydao.RepairRequestDAOMemory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TechnicianCompletedRepairRequestTest {
    TechnicianCompletedRepairRequestViewStub view;
    TechnicianCompletedRepairRequestPresenter presenter;
    private static int repairRequestID;
    private RepairRequestDAO repairRequestDAO;

    /**
     * Αρχικοποίηση
     */
    @Before
    public void setUp(){
        view = new TechnicianCompletedRepairRequestViewStub();
        presenter = new TechnicianCompletedRepairRequestPresenter();
        presenter.setView(view);
        new MemoryInitializer().prepareData();
        repairRequestDAO = new RepairRequestDAOMemory();
        presenter.setRepairRequestDAO((RepairRequestDAOMemory) repairRequestDAO);
        repairRequestID = 7;
        presenter.searchRepairRequestData(repairRequestID);
    }

    /**
     * Έλεγχος ότι έγινε η απαραίτητη προβολή όλων των δεδομένων
     */
    @Test
    public void checkSetters(){
        Assert.assertEquals(7, view.getSumOfsetter());
    }

    /**
     * Δεν γίνεται να πραγματοποιηθεί αναζήτηση σε μηδενικό id
     * Προβολή Μηνύματος λάθους
     * (Για κάλυψη πιθανότητας σφάλματος του συστήματος)
     */
    @Test
    public void checkSearchErrorZero(){
        presenter.searchRepairRequestData(0);
        Assert.assertEquals(1, view.getState());
    }

    /**
     * Δεν γίνεται να πραγματοποιηθεί αναζήτηση σε id που δεν υπάρχει
     * Προβολή Μηνύματος λάθους
     * (Για κάλυψη πιθανότητας σφάλματος του συστήματος)
     */
    @Test
    public void checkSearchError(){
        presenter.searchRepairRequestData(100);
        Assert.assertEquals(1, view.getState());
    }

}
