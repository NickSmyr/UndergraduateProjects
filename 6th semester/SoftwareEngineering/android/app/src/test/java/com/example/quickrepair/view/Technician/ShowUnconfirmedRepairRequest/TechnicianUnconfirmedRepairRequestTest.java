package com.example.quickrepair.view.Technician.ShowUnconfirmedRepairRequest;

import com.example.quickrepair.dao.RepairRequestDAO;
import com.example.quickrepair.domain.RepairRequest;
import com.example.quickrepair.memorydao.MemoryInitializer;
import com.example.quickrepair.memorydao.RepairRequestDAOMemory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TechnicianUnconfirmedRepairRequestTest {
    TechnicianUnconfirmedRepairRequestViewStub view;
    TechnicianUnconfirmedRepairRequestPresenter presenter;
    RepairRequestDAO repairRequestDAO;
    private static int repairRequestID;

    /**
     * Αρχικοποίηση
     */
    @Before
    public void setUp(){
        view = new TechnicianUnconfirmedRepairRequestViewStub();
        presenter = new TechnicianUnconfirmedRepairRequestPresenter();
        presenter.setView(view);
        new MemoryInitializer().prepareData();
        repairRequestDAO = new RepairRequestDAOMemory();
        presenter.setRepairRequestDAOMemory(repairRequestDAO);
        repairRequestID = 1;
        presenter.searchRepairRequestData(repairRequestID);
    }

    /**
     * Έλεγχος ότι έγινε η απαραίτητη προβολή όλων των δεδομένων
     */
    @Test
    public void checkSetters(){
        Assert.assertEquals(6, view.getSumOfsetter());
    }

    /**
     * Έλεγχος ότι έγινε ορθά η απόρριψη ενός Αιτήματος Επισκευής
     * και ότι μεταφερθήκαμε στο αποτέλεσμα της απόρριψης
     */
    @Test
    public void testReject(){
        presenter.setReject();
        Assert.assertEquals(1, view.getState());
        Assert.assertEquals(RepairRequest.Status.REJECTED, repairRequestDAO.find(repairRequestID).getStatus());
    }

    /**
     * Έλεγχος ότι έγινε ορθά η επιβεβαίωση ενός Αιτήματος Επισκευής
     * και ότι μεταφερθήκαμε στο αποτέλεσμα της επιβεβαίωσης
     */
    @Test
    public void testConfirmOk(){
        presenter.setConfirm("1");
        Assert.assertEquals(2, view.getState());
        Assert.assertEquals(RepairRequest.Status.CONFIRMED, repairRequestDAO.find(repairRequestID).getStatus());
    }

    /**
     * Πρέπει να εισαχθεί αριθμός ώς ποσότητα
     * 'Ελεγχος ότι προβλήθηκε λάθος
     */
    @Test
    public void testError(){
        presenter.setConfirm("Hello");
        Assert.assertEquals(3, view.getState());
    }

    /**
     * Έλεγχος οτι δε γίνεται αναζήτηση με μηδενικό αναγνωριστικό
     * (Για κάλυψη πιθανότητας σφάλματος του συστήματος)
     */
    @Test
    public void testSearchErrorZero(){
        presenter.searchRepairRequestData(0);
        Assert.assertEquals(3, view.getState());
    }

    /**
     * Έλεγχος οτι δε γίνεται αναζήτηση με μη υπαρκτό αναγνωριστικό
     * (Για κάλυψη πιθανότητας σφάλματος του συστήματος)
     */
    @Test
    public void testSearchErrorNull(){
        presenter.searchRepairRequestData(100);
        Assert.assertEquals(3, view.getState());
    }

}
