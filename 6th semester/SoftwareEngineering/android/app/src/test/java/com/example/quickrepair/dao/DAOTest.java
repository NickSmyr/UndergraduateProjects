package com.example.quickrepair.dao;


import com.example.quickrepair.domain.Customer;
import com.example.quickrepair.domain.Job;
import com.example.quickrepair.domain.JobType;
import com.example.quickrepair.domain.RepairRequest;
import com.example.quickrepair.domain.Specialty;
import com.example.quickrepair.domain.Technician;
import com.example.quickrepair.memorydao.AreaDAOMemory;
import com.example.quickrepair.memorydao.CustomerDAOMemory;
import com.example.quickrepair.memorydao.EvaluationDAOMemory;
import com.example.quickrepair.memorydao.JobDAOMemory;
import com.example.quickrepair.memorydao.JobTypeDAOMemory;
import com.example.quickrepair.memorydao.MemoryInitializer;
import com.example.quickrepair.memorydao.PaymentDAOMemory;
import com.example.quickrepair.memorydao.RepairDAOMemory;
import com.example.quickrepair.memorydao.RepairRequestDAOMemory;
import com.example.quickrepair.memorydao.SpecialtyDAOMemory;
import com.example.quickrepair.memorydao.TechnicianDAOMemory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class DAOTest {
    private AreaDAO areaDAO;
    private CustomerDAO customerDAO;
    private EvaluationDAO evaluationDAO;
    private JobDAO jobDAO;
    private JobTypeDAO jobTypeDAO;
    private PaymentDAO paymentDAO;
    private RepairDAO repairDAO;
    private RepairRequestDAO repairRequestDAO;
    private SpecialtyDAO specialtyDAO;
    private TechnicianDAO technicianDAO;

    @Before
    public void setUp() {
        Initializer initializer = new MemoryInitializer();
        initializer.prepareData();

        areaDAO = new AreaDAOMemory();
        customerDAO = new CustomerDAOMemory();
        evaluationDAO = new EvaluationDAOMemory();
        jobDAO = new JobDAOMemory();
        jobTypeDAO = new JobTypeDAOMemory();
        paymentDAO = new PaymentDAOMemory();
        repairDAO = new RepairDAOMemory();
        repairRequestDAO = new RepairRequestDAOMemory();
        specialtyDAO = new SpecialtyDAOMemory();
        technicianDAO = new TechnicianDAOMemory();
    }

    @Test
    public void areasSizeCheck() {
        Assert.assertEquals(21, areaDAO.getAreas().size());
    }

    @Test
    public void findCustomer() {
        Assert.assertNotNull(customerDAO.find(1));
    }

    @Test
    public void findNonExistingCustomer() {
        Customer customer = customerDAO.find(4711);
        Assert.assertNull(customer);
    }

    @Test
    public void findParticularCustomer() {
        Customer customer = customerDAO.find(1);
        Assert.assertEquals("ΑΝΝΑ", customer.getName());
    }

    @Test
    public void findParticularTechnician() {
        Technician technician = technicianDAO.find(1);
        Assert.assertEquals("ΓΙΑΝΝΗΣ", technician.getName());
    }
    @Test
    public void findParticularTechnicianRepairRequestUnconfirmed() {
        ArrayList<RepairRequest> repairRequests = repairRequestDAO.findAllForTechnicianByStatus(1, RepairRequest.Status.UNCONFIRMED);
        Assert.assertEquals(2, repairRequests.size());
    }

    @Test
    public void findParticularTechnicianRepairRequestConfirmed() {
        ArrayList<RepairRequest> repairRequests = repairRequestDAO.findAllForTechnicianByStatus(1, RepairRequest.Status.CONFIRMED);
        Assert.assertEquals(2, repairRequests.size());
    }

    @Test
    public void findParticularTechnicianRepairRequestCompleted() {
        ArrayList<RepairRequest> repairRequests = repairRequestDAO.findAllForTechnicianByStatus(1, RepairRequest.Status.COMPLETED);
        Assert.assertEquals(4, repairRequests.size());
    }

    @Test
    public void findParticularTechnicianRepairRequestRejected() {
        ArrayList<RepairRequest> repairRequests = repairRequestDAO.findAllForTechnicianByStatus(1, RepairRequest.Status.REJECTED);
        Assert.assertEquals(1, repairRequests.size());
    }

    @Test
    public void getParticularJobType(){
        JobType jobType = jobTypeDAO.find(2);
        Assert.assertEquals("Αλλαγή γραμμής ethernet", jobType.getName());
    }

    @Test
    public void getParticularJob(){
        Job job = jobDAO.find(2);
        Assert.assertEquals("ΓΙΑΝΝΗΣ", job.getTechnician().getName());
        Assert.assertEquals("aggelidis", job.getTechnician().getUsername());
    }

    @Test
    public void getAllTechnicians(){
        List<Technician> technicians = technicianDAO.findAll();
        Assert.assertEquals(6, technicians.size());
    }


}
