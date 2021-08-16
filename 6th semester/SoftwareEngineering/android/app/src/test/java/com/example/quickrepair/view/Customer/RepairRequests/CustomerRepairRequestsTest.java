package com.example.quickrepair.view.Customer.RepairRequests;

import com.example.quickrepair.domain.Customer;
import com.example.quickrepair.domain.RepairRequest;
import com.example.quickrepair.memorydao.MemoryInitializer;
import com.example.quickrepair.memorydao.RepairRequestDAOMemory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class CustomerRepairRequestsTest
{
    CustomerRepairRequestsViewStub view;
    CustomerRepairRequestsPresenter presenter;
    RepairRequestDAOMemory repairRequestDAOMemory;

    /**
     * Αρχικοποίηση
     */
    @Before
    public void setUp()
    {
        view = new CustomerRepairRequestsViewStub();
        presenter = new CustomerRepairRequestsPresenter();
        presenter.setView(view);
        new MemoryInitializer().prepareData();
        repairRequestDAOMemory = new RepairRequestDAOMemory();
        presenter.setRepairRequestDAO(repairRequestDAOMemory);
    }

    /**
     * Ελεγχός ότι η αναζήτηση στον Presenter για repairRequest Μη Επιβεβαιωμένα γίνεται ορθά
     */
    @Test
    public void searchForUnconfirmedRepairRequests()
    {
        ArrayList<RepairRequest> repairRequests = presenter.searchRepairRequests(1, RepairRequest.Status.UNCONFIRMED);
        Assert.assertEquals(3, repairRequests.size());
    }

    /**
     * Ελεγχός ότι η αναζήτηση στον Presenter για repairRequest Επιβεβαιωμένα γίνεται ορθά
     */
    @Test
    public void searchForConfirmedRepairRequests()
    {
        ArrayList<RepairRequest> repairRequests = presenter.searchRepairRequests(1, RepairRequest.Status.CONFIRMED);
        Assert.assertEquals(1, repairRequests.size());
    }

    /**
     * Ελεγχός ότι η αναζήτηση στον Presenter για repairRequest Ολοκληρωμένα γίνεται ορθά
     */
    @Test
    public void searchForCompletedRepairRequests()
    {
        ArrayList<RepairRequest> repairRequests = presenter.searchRepairRequests(1, RepairRequest.Status.COMPLETED);
        Assert.assertEquals(2, repairRequests.size());
    }

    /**
     * Ελεγχός ότι όταν επιλέγουμε ένα συγκεκριμένο Μη επιβεβαιωμένα repairRequest το βρίσκετε ορθά
     * και παρέχετε η πληροφορία γι' αυτό
     */
    @Test
    public void clickedParticularUnconfirmed()
    {
        RepairRequest repairRequest = repairRequestDAOMemory.findAllForCustomerByStatus(1, RepairRequest.Status.UNCONFIRMED).get(0);
        presenter.onRepairRequestSelectedUnconfirmed(repairRequest);
        Assert.assertEquals(1, view.getCurrentRepairRequestUid());
        Assert.assertEquals(1, view.getCurrentState());
    }

    /**
     * Ελεγχός ότι όταν επιλέγουμε ένα συγκεκριμένο Επιεβαιβεωμένο repairRequest το βρίσκετε ορθά
     * και παρέχετε η πληροφορία γι' αυτό
     */
    @Test
    public void clickedParticularConfirmed()
    {
        RepairRequest repairRequest = repairRequestDAOMemory.findAllForCustomerByStatus(1, RepairRequest.Status.CONFIRMED).get(0);
        presenter.onRepairRequestSelectedConfirmed(repairRequest);
        Assert.assertEquals(3, view.getCurrentRepairRequestUid());
        Assert.assertEquals(2, view.getCurrentState());
    }

    /**
     * Ελεγχός ότι όταν επιλέγουμε ένα συγκεκριμένο Ολοκληρωμένο repairRequest το βρίσκετε ορθά
     * και παρέχετε η πληροφορία γι' αυτό
     */
    @Test
    public void clickedParticularCompleted()
    {
        RepairRequest repairRequest = repairRequestDAOMemory.findAllForCustomerByStatus(1, RepairRequest.Status.COMPLETED).get(0);
        presenter.onRepairRequestSelectedCompleted(repairRequest);
        Assert.assertEquals(5, view.getCurrentRepairRequestUid());
        Assert.assertEquals(3, view.getCurrentState());
    }

    /**
     * Έλεγχος ότι όταν επιλέγετε τη σελίδα τροποποίσης στοιχείων γίνεται μεταφορά σε αυτήν
     */
    @Test
    public void clickedEditPage()
    {
        view.editData();
        Assert.assertTrue(view.getPressEdit());
    }

    /**
     * Έλεγχος ότι όταν επιλέγετε τη σελίδα αναζήτησης γίνεται μεταφορά σε αυτήν
     */
    @Test
    public void clickedSearch()
    {
        view.search();
        Assert.assertTrue(view.isPressSearch());
    }

    /**
     * Έλεγχος ότι δεν μπορεί να γίνει επιλογή κενού Μη επιβεβαιωμένου Αιτήματος Επισκευής
     * (Για κάλυψη πιθανότητας σφάλματος του συστήματος)
     */
    @Test
    public void testError1()
    {
        presenter.onRepairRequestSelectedUnconfirmed(null);
        Assert.assertTrue(view.isError());
    }

    /**
     * Έλεγχος ότι δεν μπορεί να γίνει επιλογή κενού Επιβεβαιωμένου Αιτήματος Επισκευής
     * (Για κάλυψη πιθανότητας σφάλματος του συστήματος)
     */
    @Test
    public void testError2()
    {
        presenter.onRepairRequestSelectedConfirmed(null);
        Assert.assertTrue(view.isError());
    }

    /**
     * Έλεγχος ότι δεν μπορεί να γίνει επιλογή κενού Ολοκληρωμένου Αιτήματος Επισκευής
     * (Για κάλυψη πιθανότητας σφάλματος του συστήματος)
     */
    @Test
    public void testError3()
    {
        presenter.onRepairRequestSelectedCompleted(null);
        Assert.assertTrue(view.isError());
    }
}
