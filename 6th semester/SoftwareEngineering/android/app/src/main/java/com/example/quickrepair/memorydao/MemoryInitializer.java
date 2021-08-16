package com.example.quickrepair.memorydao;

import com.example.quickrepair.dao.AreaDAO;
import com.example.quickrepair.dao.CustomerDAO;
import com.example.quickrepair.dao.EvaluationDAO;
import com.example.quickrepair.dao.Initializer;
import com.example.quickrepair.dao.JobDAO;
import com.example.quickrepair.dao.JobTypeDAO;
import com.example.quickrepair.dao.PaymentDAO;
import com.example.quickrepair.dao.RepairDAO;
import com.example.quickrepair.dao.RepairRequestDAO;
import com.example.quickrepair.dao.SpecialtyDAO;
import com.example.quickrepair.dao.TechnicianDAO;
import com.example.quickrepair.domain.Customer;
import com.example.quickrepair.domain.Evaluation;
import com.example.quickrepair.domain.Job;
import com.example.quickrepair.domain.JobType;
import com.example.quickrepair.domain.Payment;
import com.example.quickrepair.domain.Repair;
import com.example.quickrepair.domain.RepairRequest;
import com.example.quickrepair.domain.Specialty;
import com.example.quickrepair.domain.Technician;

public class MemoryInitializer extends Initializer {

    /**
     * Delete data
     */
    @Override
    protected void eraseData() {
        for(Customer customer : getCustomerDAO().findAll()) {
            getCustomerDAO().delete(customer);
        }
        for(Evaluation evaluation : getEvaluationDAO().findAll()) {
            getEvaluationDAO().delete(evaluation);
        }
        for(Job job : getJobDAO().findAll()) {
            getJobDAO().delete(job);
        }
        for(JobType jobType : getJobTypeDAO().findAll()) {
            getJobTypeDAO().delete(jobType);
        }
        for(Payment payment : getPaymentDAO().findAll()) {
            getPaymentDAO().delete(payment);
        }
        for(Repair repair : getRepairDAO().findAll()) {
            getRepairDAO().delete(repair);
        }
        for(RepairRequest repairRequest : getRepairRequestDAO().findAll()) {
            getRepairRequestDAO().delete(repairRequest);
        }
        for(Specialty specialty : getSpecialtyDAO().findAll()) {
            getSpecialtyDAO().delete(specialty);
        }
        for(Technician technician : getTechnicianDAO().findAll()) {
            getTechnicianDAO().delete(technician);
        }
    }

    /**
     * Return Technicians' DAO
     *
     * @return Technicians' DAO
     */
    @Override
    public TechnicianDAO getTechnicianDAO() {
        return new TechnicianDAOMemory();
    }

    /**
     * Return Specialties' DAO
     *
     * @return Specialties' DAO
     */
    @Override
    public SpecialtyDAO getSpecialtyDAO() {
        return new SpecialtyDAOMemory();
    }

    /**
     * Return Repair Requests' DAO
     *
     * @return Repair Requests' DAO
     */
    @Override
    public RepairRequestDAO getRepairRequestDAO() {
        return new RepairRequestDAOMemory();
    }

    /**
     * Return Repairs' DAO
     *
     * @return Repairs' DAO
     */
    @Override
    public RepairDAO getRepairDAO() {
        return new RepairDAOMemory();
    }

    /**
     * Return Payments' DAO
     *
     * @return Payments' DAO
     */
    @Override
    public PaymentDAO getPaymentDAO() {
        return new PaymentDAOMemory();
    }

    /**
     * Reutrn JobTypes' DAO
     *
     * @return JobTypes' DAO
     */
    @Override
    public JobTypeDAO getJobTypeDAO() {
        return new JobTypeDAOMemory();
    }

    /**
     * Reutrn Jobs' DAO
     *
     * @return Jobs' DAO
     */
    @Override
    public JobDAO getJobDAO() {
        return new JobDAOMemory();
    }

    /**
     * Return Evaluations' DAO
     *
     * @return Evaluations' DAO
     */
    @Override
    public EvaluationDAO getEvaluationDAO() {
        return new EvaluationDAOMemory();
    }

    /**
     * Return Customers' DAO
     *
     * @return Customers' DAO
     */
    @Override
    public CustomerDAO getCustomerDAO() {
        return new CustomerDAOMemory();
    }

    /**
     * Return Areas' DAO
     *
     * @return Areas' DAO
     */
    @Override
    public AreaDAO getAreaDAO() {
        return new AreaDAOMemory();
    }
}
