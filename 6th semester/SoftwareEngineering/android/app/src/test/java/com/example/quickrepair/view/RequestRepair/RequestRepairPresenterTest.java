package com.example.quickrepair.view.RequestRepair;

import android.widget.ListView;

import com.example.quickrepair.dao.CustomerDAO;
import com.example.quickrepair.dao.Initializer;
import com.example.quickrepair.dao.JobTypeDAO;
import com.example.quickrepair.dao.RepairRequestDAO;
import com.example.quickrepair.dao.TechnicianDAO;
import com.example.quickrepair.domain.Customer;
import com.example.quickrepair.domain.Job;
import com.example.quickrepair.domain.Repair;
import com.example.quickrepair.domain.RepairRequest;
import com.example.quickrepair.domain.Technician;
import com.example.quickrepair.memorydao.MemoryInitializer;
import com.example.quickrepair.view.SearchTechnicians.SearchTechniciansPresenter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class RequestRepairPresenterTest {
    RequestRepairPresenter presenter;
    RequestRepairViewStub stub;
    TechnicianDAO technicianDAO;
    JobTypeDAO jobTypeDAO;
    CustomerDAO customerDAO;
    RepairRequestDAO repairRequestDAO;

    /**
     * Sets up the tests by creating a presenter with some preset values
     */
    @Before
    public void setUp(){
        Initializer initializer = new MemoryInitializer();
        initializer.prepareData();
        technicianDAO =  initializer.getTechnicianDAO();
        jobTypeDAO =  initializer.getJobTypeDAO();
        customerDAO = initializer.getCustomerDAO();
        repairRequestDAO = initializer.getRepairRequestDAO();
        presenter = new RequestRepairPresenter(technicianDAO , jobTypeDAO , customerDAO , repairRequestDAO);
        stub = new RequestRepairViewStub();
        stub.setPresenter(presenter);
        presenter.setView(stub);
        presenter.setLoggedInUser(customerDAO.findAll().get(0).getUid());
        //presenter.setDate(2020 , 11 ,12 );
        presenter.setDate(2010 , 10 ,2 );
        presenter.setTechnicianId(technicianDAO.findAll().get(0).getUid());
        ArrayList<Job> jobs = new ArrayList<>(technicianDAO.findAll().get(0).getJobs());
        presenter.setJobTypeId(jobs.get(0).getJobType().getUid());


    }

    /**
     * Tests  the presenter by simulating an execution where the user inputs correct values
     */
    @Test
    public void normalExecution(){
        presenter.onStart();
        presenter.setAddress("Papagou , 23");
        presenter.setTime(15 , 0);
        presenter.setComments("newrepairRequestFromTest");
        presenter.requestRepair();
        //Assert that no error has been thrown
        Assert.assertNull(stub.errorMessage);
        //Assert that a a repair request has been added to the technicians list of repair requests
        Technician targetTechnician = technicianDAO.find(1);
        List<RepairRequest> allRepaiRequests = new ArrayList<>();
        for ( Job job : targetTechnician.getJobs()){
            allRepaiRequests.addAll(job.getRepairRequests());
        }
        boolean found = false;
        for(RepairRequest rr : allRepaiRequests){
            if(rr.getCommentsFromCustomer().equals("newrepairRequestFromTest")){
                found = true;
            }
        }
        Assert.assertTrue(found);
        //Assert that the repair request has also been added to the dao
        allRepaiRequests = repairRequestDAO.findAll();
        found = false;
        for(RepairRequest rr : allRepaiRequests){
            if(rr.getCommentsFromCustomer().equals("newrepairRequestFromTest")){
                found = true;
            }
        }
        Assert.assertTrue(found);
    }

    /**
     * Tests the case where a user inputs a badly formatted address
     */
    @Test
    public void invalidAddress(){
        presenter.onStart();
        presenter.setAddress("Papagou  23");
        presenter.setTime(15 , 0);
        presenter.setComments("newrepairRequestFromTest");
        presenter.requestRepair();
        Assert.assertNotNull(stub.errorMessage);
    }

    /**
     * Tests the case where the user inputs a time which is not valid
     */
    @Test
    public void invalidTime(){
        presenter.onStart();
        presenter.setAddress("Papagou ,  23");
        presenter.setTime(1515 , 1515);
        presenter.setComments("newrepairRequestFromTest");
        presenter.requestRepair();
        Assert.assertNotNull(stub.errorMessage);
    }
    /**
     * Tests the case where the user inputs a time which is not within the available hour ranges
     * of the technician
     */
    @Test
    public void timeNotWithinGap(){
        presenter.onStart();
        presenter.setDate(2020 , 11 ,11 );
        presenter.setAddress("Papagou ,  23");
        presenter.setTime(17 , 10);
        presenter.setComments("newrepairRequestFromTest");
        presenter.requestRepair();
        Assert.assertNotNull(stub.errorMessage);
    }


}
