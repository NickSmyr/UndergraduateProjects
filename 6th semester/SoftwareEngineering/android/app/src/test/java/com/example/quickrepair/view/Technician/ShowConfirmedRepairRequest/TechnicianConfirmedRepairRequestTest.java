package com.example.quickrepair.view.Technician.ShowConfirmedRepairRequest;

import com.example.quickrepair.dao.RepairRequestDAO;
import com.example.quickrepair.domain.RepairRequest;
import com.example.quickrepair.memorydao.MemoryInitializer;
import com.example.quickrepair.memorydao.RepairRequestDAOMemory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TechnicianConfirmedRepairRequestTest {

    TechnicianConfirmedRepairRequestViewStub view;
    TechnicianConfirmedRepairRequestPresenter presenter;
    private static int repairRequestID;
    private RepairRequestDAO repairRequestDAO;

    /**
     * Αρχικοποίηση
     */
    @Before
    public void setUp(){
        view = new TechnicianConfirmedRepairRequestViewStub();
        presenter = new TechnicianConfirmedRepairRequestPresenter();
        presenter.setView(view);
        new MemoryInitializer().prepareData();
        repairRequestDAO = new RepairRequestDAOMemory();
        presenter.setRepairRequestDAO((RepairRequestDAOMemory) repairRequestDAO);
        repairRequestID = 3;
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
     * Πρέπει να εισαχθεί αριθμός ώς ποσότητα
     */
    @Test
    public void testError(){
        presenter.setCompleted("Hello");
        Assert.assertEquals(2, view.getState());
    }

    /**
     * Έλεγχος ότι ένας αριθμός εισήχθη σωστά ως ποσότητα,
     * έλεγχος ότι όντως ολοκληρώθηκε το ραντεβού
     * και ότι έγινε μεταφορά ορθά στην κατάσταση στην οποία πρέπει να βρίσκεται το σύστημα μετά την ολοκλήρωση
     */
    @Test
    public void testCompleted(){
        presenter.setCompleted("2");
        Assert.assertEquals(1, view.getState());
        Assert.assertEquals(RepairRequest.Status.COMPLETED, repairRequestDAO.find(repairRequestID).getStatus());
    }

    /**
     * Έλεγχος οτι δε γίνεται αναζήτηση με μηδενικό αναγνωριστικό
     * (Για κάλυψη πιθανότητας σφάλματος του συστήματος)
     */
    @Test
    public void testSearchErrorZero(){
        presenter.searchRepairRequestData(0);
        Assert.assertEquals(2, view.getState());
    }

    /**
     * Έλεγχος οτι δε γίνεται αναζήτηση με μη υπαρκτό αναγνωριστικό
     * (Για κάλυψη πιθανότητας σφάλματος του συστήματος)
     */
    @Test
    public void testSearchError(){
        presenter.searchRepairRequestData(100);
        Assert.assertEquals(2, view.getState());
    }
}
