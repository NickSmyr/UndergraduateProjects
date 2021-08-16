package com.example.quickrepair.dao;

import android.app.DownloadManager;

import com.example.quickrepair.domain.Address;
import com.example.quickrepair.domain.Customer;
import com.example.quickrepair.domain.Evaluation;
import com.example.quickrepair.domain.Job;
import com.example.quickrepair.domain.JobType;
import com.example.quickrepair.domain.MeasurementUnit;
import com.example.quickrepair.domain.PaymentType;
import com.example.quickrepair.domain.Repair;
import com.example.quickrepair.domain.RepairRequest;
import com.example.quickrepair.domain.Specialty;
import com.example.quickrepair.domain.Technician;
import com.example.quickrepair.domain.User;

import java.util.Calendar;
import java.util.GregorianCalendar;

public abstract class Initializer {

    //Technician
    public static final int HLEKTOLOGOS_GIANNHS_ID = 1;
    public static final int HLEKTOLOGOS_PANAGIOTIS_ID = 2;

    //JobTypes
    public static final int ALLAGH_GRAMMHS_ETHERNET_ID = 2;
    public static final int DOMHMENH_KALODIOSH_ID = 3;

    //Jobs
    public static final int ALLAGH_GRAMMHS_ETHERNET_APO_GIANNH_ID = 2;
    public static final int DOMHMENH_KALODIOSH_APO_GIANNH_ID = 3;

    public static final int ALLAGH_GRAMMHS_ETHERNET_APO_PANAGIOTI_ID = 5;
    public static final int DOMHMENH_KALODIOSH_APO_PANAGIOTI_ID = 6;

    //Customer
    public static final int CONSUMER_ANNA_ID = 1;

    //RepairRequest
    public static final int AITHMA_GIA_ALLAGH_GRAMMHS_ETHERNET_APO_ANNA_SE_GIANNH_UNCOMFIRMED_ID = 1;
    public static final int AITHMA_GIA_DOMHMENH_ETHERNET_APO_ANNA_SE_GIANNH_UNCOMFIRMED_ID = 2;

    public static final int AITHMA_GIA_ALLAGH_GRAMMHS_ETHERNET_APO_ANNA_SE_GIANNH_CONFIRMED_ID = 3;
    public static final int AITHMA_GIA_DOMHMENH_ETHERNET_APO_ANNA_SE_GIANNH_CONFIMER_ID = 4;

    public static final int AITHMA_GIA_ALLAGH_GRAMMHS_ETHERNET_APO_ANNA_SE_GIANNH_COMPLETED_ID = 5;
    public static final int AITHMA_GIA_DOMHMENH_ETHERNET_APO_ANNA_SE_GIANNH_COMPLETED_ID = 6;

    /**
     * Delete data
     */
    protected abstract void eraseData();


    /**
     * Insert test data.
     */
    public void prepareData()
    {
        // delete data
        eraseData();


        SpecialtyDAO specialtyDAO = getSpecialtyDAO();

        Specialty specialtyTmp;
        specialtyTmp = new Specialty("Ηλεκτρολόγος");
        specialtyTmp.setUid(specialtyDAO.nextId());
        specialtyDAO.save(specialtyTmp);

        specialtyTmp = new Specialty("Υδραυλικός");
        specialtyTmp.setUid(specialtyDAO.nextId());
        specialtyDAO.save(specialtyTmp);

        specialtyTmp = new Specialty("Τεχνικός Υπολογιστών");
        specialtyTmp.setUid(specialtyDAO.nextId());
        specialtyDAO.save(specialtyTmp);


        JobTypeDAO jobTypeDAO = getJobTypeDAO();
        JobType jobTypeTmp;

        jobTypeTmp = new JobType("Αλλαγή λάμπας", getSpecialtyDAO().find(1), MeasurementUnit.NONE);
        jobTypeTmp.setUid(jobTypeDAO.nextId());
        jobTypeDAO.save(jobTypeTmp);

        jobTypeTmp = new JobType("Αλλαγή γραμμής ethernet", getSpecialtyDAO().find(1), MeasurementUnit.NONE);
        jobTypeTmp.setUid(jobTypeDAO.nextId());
        jobTypeDAO.save(jobTypeTmp);

        jobTypeTmp = new JobType("Δομημένη καλωδίωση", getSpecialtyDAO().find(1), MeasurementUnit.METER);
        jobTypeTmp.setUid(jobTypeDAO.nextId());
        jobTypeDAO.save(jobTypeTmp);

        jobTypeTmp = new JobType("Αλλαγή πρίζας/διακόπτη", getSpecialtyDAO().find(1), MeasurementUnit.NONE);
        jobTypeTmp.setUid(jobTypeDAO.nextId());
        jobTypeDAO.save(jobTypeTmp);

        jobTypeTmp = new JobType("Επισκευή σε καζανάκι", getSpecialtyDAO().find(2), MeasurementUnit.NONE);
        jobTypeTmp.setUid(jobTypeDAO.nextId());
        jobTypeDAO.save(jobTypeTmp);

        jobTypeTmp = new JobType("Επισκευή για μικροβλάβη", getSpecialtyDAO().find(2), MeasurementUnit.NONE);
        jobTypeTmp.setUid(jobTypeDAO.nextId());
        jobTypeDAO.save(jobTypeTmp);

        jobTypeTmp = new JobType("Αποφράξεις", getSpecialtyDAO().find(2), MeasurementUnit.NONE);
        jobTypeTmp.setUid(jobTypeDAO.nextId());
        jobTypeDAO.save(jobTypeTmp);

        jobTypeTmp = new JobType("Αναβάθμηση Υπολογιστή", getSpecialtyDAO().find(3), MeasurementUnit.NONE);
        jobTypeTmp.setUid(jobTypeDAO.nextId());
        jobTypeDAO.save(jobTypeTmp);

        jobTypeTmp = new JobType("Επανεγκατάσταση λογισμικού", getSpecialtyDAO().find(3), MeasurementUnit.NONE);
        jobTypeTmp.setUid(jobTypeDAO.nextId());
        jobTypeDAO.save(jobTypeTmp);

        jobTypeTmp = new JobType("Έλεγχος για διάγνωση προβλήματος", getSpecialtyDAO().find(3), MeasurementUnit.NONE);
        jobTypeTmp.setUid(jobTypeDAO.nextId());
        jobTypeDAO.save(jobTypeTmp);

        TechnicianDAO technicianDAO = getTechnicianDAO();

        Technician technicianTmp;
        technicianTmp = new Technician("ΓΙΑΝΝΗΣ", "ΑΓΓΕΛΪΔΗΣ", "1010101010", "aggelidisgiannis@repair.gr", "0034560101011234567890","aggelidis","123", getSpecialtyDAO().find(1),"2122772");
        technicianTmp.setNotificationMethod(User.NotificationMethod.EMAIL);
        technicianTmp.setUid(technicianDAO.nextId());
        technicianTmp.addArea("Agia Varvara");
        technicianTmp.addArea("Alimos");
        technicianTmp.addArea("Argos");
        technicianTmp.addArea("Argyroupoli");
        technicianTmp.setAvailableOnDay(Calendar.SUNDAY, 0, 0);
        technicianTmp.setAvailableOnDay(Calendar.MONDAY, 8, 16);
        technicianTmp.setAvailableOnDay(Calendar.TUESDAY, 8, 16);
        technicianTmp.setAvailableOnDay(Calendar.WEDNESDAY, 9, 17);
        technicianTmp.setAvailableOnDay(Calendar.THURSDAY, 0, 0);
        technicianTmp.setAvailableOnDay(Calendar.FRIDAY, 8, 16);
        technicianTmp.setAvailableOnDay(Calendar.SATURDAY, 0, 0);
        technicianDAO.save(technicianTmp);

        technicianTmp = new Technician("ΠΑΝΑΓΙΩΤΗΣ", "ΖΑΧΟΣ", "1010101010", "zaxosohlktrologos@repair.gr", "0034560101011234567890","zaxos","123", getSpecialtyDAO().find(1),"212272");
        technicianTmp.setNotificationMethod(User.NotificationMethod.SMS);
        technicianTmp.setUid(technicianDAO.nextId());
        technicianTmp.addArea("Acharnes");
        technicianTmp.addArea("Alimos");
        technicianTmp.addArea("Argos");
        technicianTmp.addArea("Argyroupoli");
        technicianTmp.setAvailableOnDay(Calendar.SUNDAY, 0, 0);
        technicianTmp.setAvailableOnDay(Calendar.MONDAY, 8, 16);
        technicianTmp.setAvailableOnDay(Calendar.TUESDAY, 8, 16);
        technicianTmp.setAvailableOnDay(Calendar.WEDNESDAY, 9, 17);
        technicianTmp.setAvailableOnDay(Calendar.THURSDAY, 0, 0);
        technicianTmp.setAvailableOnDay(Calendar.FRIDAY, 8, 16);
        technicianTmp.setAvailableOnDay(Calendar.SATURDAY, 0, 0);
        technicianDAO.save(technicianTmp);

        technicianTmp = new Technician("ΔΗΜΗΤΡΗΣ", "ΔΑΜΑΣΚΗΝΟΣ", "1010101010", "damaskhnos@repair.gr", "0034560101011234567890","damaskhnos","123", getSpecialtyDAO().find(2),"210072");
        technicianTmp.setNotificationMethod(User.NotificationMethod.EMAIL);
        technicianTmp.setUid(technicianDAO.nextId());
        technicianTmp.addArea("Amaliada");
        technicianTmp.addArea("Alimos");
        technicianTmp.addArea("Argos");
        technicianTmp.addArea("Argyroupoli");
        technicianTmp.setAvailableOnDay(Calendar.SUNDAY, 0, 0);
        technicianTmp.setAvailableOnDay(Calendar.MONDAY, 8, 16);
        technicianTmp.setAvailableOnDay(Calendar.TUESDAY, 8, 16);
        technicianTmp.setAvailableOnDay(Calendar.WEDNESDAY, 9, 17);
        technicianTmp.setAvailableOnDay(Calendar.THURSDAY, 0, 0);
        technicianTmp.setAvailableOnDay(Calendar.FRIDAY, 8, 16);
        technicianTmp.setAvailableOnDay(Calendar.SATURDAY, 0, 0);
        technicianDAO.save(technicianTmp);

        technicianTmp = new Technician("ΗΛΙΑΣ", "ΑΡΓΥΡΙΑΔΗΣ", "7500830008", "hlias_argiriadis@repair.gr", "0034560101011234567890","argyriadis","123", getSpecialtyDAO().find(2),"200072");
        technicianTmp.setNotificationMethod(User.NotificationMethod.EMAIL);
        technicianTmp.setUid(technicianDAO.nextId());
        technicianTmp.addArea("Acharnes");
        technicianTmp.addArea("Alimos");
        technicianTmp.addArea("Argos");
        technicianTmp.addArea("Argyroupoli");
        technicianTmp.setAvailableOnDay(Calendar.SUNDAY, 0, 0);
        technicianTmp.setAvailableOnDay(Calendar.MONDAY, 8, 16);
        technicianTmp.setAvailableOnDay(Calendar.TUESDAY, 8, 16);
        technicianTmp.setAvailableOnDay(Calendar.WEDNESDAY, 9, 17);
        technicianTmp.setAvailableOnDay(Calendar.THURSDAY, 0, 0);
        technicianTmp.setAvailableOnDay(Calendar.FRIDAY, 8, 16);
        technicianTmp.setAvailableOnDay(Calendar.SATURDAY, 0, 0);
        technicianDAO.save(technicianTmp);

        technicianTmp = new Technician("ΝΙΚΟΣ", "ΣΜΥΡΝΙΟΥΔΗΣ", "7599030003", "smyrnioudis@repair.gr", "0034560101011234567890","smyrnioudis","123", getSpecialtyDAO().find(3),"211002");
        technicianTmp.setNotificationMethod(User.NotificationMethod.SMS);
        technicianTmp.setUid(technicianDAO.nextId());
        technicianTmp.addArea("Artemida");
        technicianTmp.addArea("Aspropyrgos");
        technicianTmp.addArea("Athens");
        technicianTmp.addArea("Argyroupoli");
        technicianTmp.setAvailableOnDay(Calendar.SUNDAY, 0, 0);
        technicianTmp.setAvailableOnDay(Calendar.MONDAY, 8, 16);
        technicianTmp.setAvailableOnDay(Calendar.TUESDAY, 8, 16);
        technicianTmp.setAvailableOnDay(Calendar.WEDNESDAY, 9, 17);
        technicianTmp.setAvailableOnDay(Calendar.THURSDAY, 0, 0);
        technicianTmp.setAvailableOnDay(Calendar.FRIDAY, 8, 16);
        technicianTmp.setAvailableOnDay(Calendar.SATURDAY, 0, 0);
        technicianDAO.save(technicianTmp);

        technicianTmp = new Technician("ΞΑΝΘΟΣ", "ΞΑΝΘΟΠΟΥΛΟΣ", "7599030903", "xanthopoulos@repair.gr", "0034560101011234567890","xanthopoulos","123", getSpecialtyDAO().find(3),"211202");
        technicianTmp.setNotificationMethod(User.NotificationMethod.SMS);
        technicianTmp.setUid(technicianDAO.nextId());
        technicianTmp.addArea("Artemida");
        technicianTmp.addArea("Aspropyrgos");
        technicianTmp.addArea("Athens");
        technicianTmp.addArea("Argyroupoli");
        technicianTmp.setAvailableOnDay(Calendar.SUNDAY, 0, 0);
        technicianTmp.setAvailableOnDay(Calendar.MONDAY, 8, 16);
        technicianTmp.setAvailableOnDay(Calendar.TUESDAY, 8, 16);
        technicianTmp.setAvailableOnDay(Calendar.WEDNESDAY, 9, 17);
        technicianTmp.setAvailableOnDay(Calendar.THURSDAY, 0, 0);
        technicianTmp.setAvailableOnDay(Calendar.FRIDAY, 8, 16);
        technicianTmp.setAvailableOnDay(Calendar.SATURDAY, 0, 0);
        technicianDAO.save(technicianTmp);

        JobDAO jobDAO = getJobDAO();

        Job jobTmp;
        jobTmp = technicianDAO.find(1).addJob(jobTypeDAO.find(1),13 );
        jobTmp.setUid(jobDAO.nextId());
        jobDAO.save(jobTmp);

        jobTmp = technicianDAO.find(1).addJob(jobTypeDAO.find(2),130 );
        jobTmp.setUid(jobDAO.nextId());
        jobDAO.save(jobTmp);

        jobTmp = technicianDAO.find(1).addJob(jobTypeDAO.find(3),130 );
        jobTmp.setUid(jobDAO.nextId());
        jobDAO.save(jobTmp);

        jobTmp = technicianDAO.find(2).addJob(jobTypeDAO.find(1),14 );
        jobTmp.setUid(jobDAO.nextId());
        jobDAO.save(jobTmp);

        jobTmp = technicianDAO.find(2).addJob(jobTypeDAO.find(2),100 );
        jobTmp.setUid(jobDAO.nextId());
        jobDAO.save(jobTmp);

        jobTmp = technicianDAO.find(2).addJob(jobTypeDAO.find(3),110);
        jobTmp.setUid(jobDAO.nextId());
        jobDAO.save(jobTmp);



        CustomerDAO customerDAO = getCustomerDAO();

        Customer customerTmp;
        customerTmp = new Customer("ΑΝΝΑ", "ΑΡΓΥΡΑΚΗ","0000000001","anna@repair.gr","0034560101011234567890","ann","123");
        customerTmp.setUid(customerDAO.nextId());
        customerDAO.save(customerTmp);

        customerTmp = new Customer("ΚΑΤΕΡΙΝΑ", "ΠΑΠΑΔΟΠΟΛΟΥ","0000004001","katerina@repair.gr","0034560101011234567890","katerina1","123");
        customerTmp.setUid(customerDAO.nextId());
        customerDAO.save(customerTmp);

        customerTmp = new Customer("ΓΙΩΡΓΟΣ", "ΤΣΙΜΙΤΣΕΛΗΣ","0002004001","tsimitselis@repair.gr","0034560101011234567890","tsimitselis","123");
        customerTmp.setUid(customerDAO.nextId());
        customerDAO.save(customerTmp);

        Address address1 = new Address("Δεκελείας","23");
        Address address2 = new Address("Αργυρουπόλεως","100");

        GregorianCalendar gregorianCalendar0 = new GregorianCalendar(2020,1,10,10,0);
        GregorianCalendar gregorianCalendar1 = new GregorianCalendar(2020,1,12,7,0);

        GregorianCalendar gregorianCalendar2 = new GregorianCalendar(2020,11,12,8,0);
        GregorianCalendar gregorianCalendar3 = new GregorianCalendar(2020,11,12,9,0);
        GregorianCalendar gregorianCalendar4 = new GregorianCalendar(2020,11,12,10,0);


        RepairRequestDAO repairRequestDAO = getRepairRequestDAO();

        RepairRequest repairRequest;

        //uncormirmed
        repairRequest = customerDAO.find(1).requestRepair(gregorianCalendar1, gregorianCalendar2, jobDAO.find(2),"Έχω 3 χαλασμένες λάμπες. Είναι επίγον", address1);
        repairRequest.setUid(repairRequestDAO.nextId());
        repairRequestDAO.save(repairRequest);

        repairRequest = customerDAO.find(1).requestRepair(gregorianCalendar1, gregorianCalendar3, getJobDAO().find(3),"Είναι επίγον, επιβεβαιώστε το", address1);
        repairRequest.setUid(repairRequestDAO.nextId());
        repairRequestDAO.save(repairRequest);

        //confirm
        repairRequest = customerDAO.find(1).requestRepair(gregorianCalendar1, gregorianCalendar3,getJobDAO().find(2),"Είναι επίγον!!", address2);
        repairRequest.setUid(repairRequestDAO.nextId());
        repairRequest.confirm(30);
        repairRequestDAO.save(repairRequest);

        repairRequest = customerDAO.find(2).requestRepair(gregorianCalendar1, gregorianCalendar4,getJobDAO().find(3),"Είναι επίγον!!!", address2);
        repairRequest.setUid(repairRequestDAO.nextId());
        repairRequest.confirm(20);
        repairRequestDAO.save(repairRequest);


        //completed, with a payment
        repairRequest = customerDAO.find(1).requestRepair(gregorianCalendar0, gregorianCalendar0,getJobDAO().find(2),"Είναι επίγον!!!!!!", address2);
        repairRequest.setUid(repairRequestDAO.nextId());
        repairRequestDAO.save(repairRequest);

        repairRequest = customerDAO.find(2).requestRepair(gregorianCalendar0, gregorianCalendar0,getJobDAO().find(3),"Είναι επίγον!!!!!!", address2);
        repairRequest.setUid(repairRequestDAO.nextId());
        repairRequestDAO.save(repairRequest);

        //completed, without a payment
        repairRequest = customerDAO.find(1).requestRepair(gregorianCalendar0, gregorianCalendar0,getJobDAO().find(2),"Είναι επίγον!!!!!!!!", address1);
        repairRequest.setUid(repairRequestDAO.nextId());
        repairRequestDAO.save(repairRequest);

        repairRequest = customerDAO.find(3).requestRepair(gregorianCalendar0, gregorianCalendar0,getJobDAO().find(2),"Είναι επίγον!!!!!!!!", address1);
        repairRequest.setUid(repairRequestDAO.nextId());
        repairRequestDAO.save(repairRequest);

        //rejected
        repairRequest = customerDAO.find(2).requestRepair(gregorianCalendar0, gregorianCalendar0,getJobDAO().find(2),"Είναι επίγον!!!", address1);
        repairRequest.setUid(repairRequestDAO.nextId());
        repairRequestDAO.save(repairRequest);
        repairRequestDAO.find(9).reject();

        //uncormirmed another technician
        repairRequest = customerDAO.find(1).requestRepair(gregorianCalendar1, gregorianCalendar2, jobDAO.find(4),"Έχω 6 χαλασμένες λάμπες. Είναι επίγον", address1);
        repairRequest.setUid(repairRequestDAO.nextId());
        repairRequestDAO.save(repairRequest);


        RepairDAO repairDAO = getRepairDAO();

        Repair repair;
        repairRequestDAO.find(5).confirm(30);
        repair = repairRequestDAO.find(5).complete(1);
        repair.pay(gregorianCalendar4, PaymentType.CARD);
        repair.setUid(repairDAO.nextId());
        repairDAO.save(repair);

        repairRequestDAO.find(6).confirm(20);
        repair = repairRequestDAO.find(6).complete(10);
        repair.pay(gregorianCalendar4, PaymentType.CARD);
        repair.setUid(repairDAO.nextId());
        repairDAO.save(repair);

        //without payment
        repairRequestDAO.find(7).confirm(20);
        repair = repairRequestDAO.find(7).complete(10);
        repair.setUid(repairDAO.nextId());
        repairDAO.save(repair);

        repairRequestDAO.find(8).confirm(20);
        repair = repairRequestDAO.find(8).complete(10);
        repair.setUid(repairDAO.nextId());
        repairDAO.save(repair);


        EvaluationDAO evaluationDAO = getEvaluationDAO();

        Evaluation evaluation;
        evaluation = repairDAO.find(1).evaluate("Πολύ καλή δουλειά!", "Πολύ καλή δουλειά", 5);
        evaluation = repairDAO.find(2).evaluate("Μέτρια δουλειά!", "Μέτρια δουλειά", 3);

        //evaluation = repairDAO.find(3).evaluate("Καλή δουλειά!", "Καλή δουλειά", 4);
        //evaluation = repairDAO.find(4).evaluate("Καλή δουλειά!", "Καλή δουλειά", 5);

    }

    /**
     * Return Technicians' DAO
     * @return Technicians' DAO
     */
    public abstract TechnicianDAO getTechnicianDAO();


    /**
     * Return Specialties' DAO
     * @return Specialties' DAO
     */
    public abstract SpecialtyDAO getSpecialtyDAO();

    /**
     * Return Repair Requests' DAO
     * @return Repair Requests' DAO
     */
    public abstract RepairRequestDAO getRepairRequestDAO();

    /**
     * Return Repairs' DAO
     * @return Repairs' DAO
     */
    public abstract RepairDAO getRepairDAO();

    /**
     * Return Payments' DAO
     * @return Payments' DAO
     */
    public abstract PaymentDAO getPaymentDAO();

    /**
     * Reutrn JobTypes' DAO
     * @return JobTypes' DAO
     */
    public abstract JobTypeDAO getJobTypeDAO();

    /**
     * Reutrn Jobs' DAO
     * @return Jobs' DAO
     */
    public abstract JobDAO getJobDAO();

    /**
     * Return Evaluations' DAO
     * @return Evaluations' DAO
     */
    public abstract EvaluationDAO getEvaluationDAO();

    /**
     * Return Customers' DAO
     * @return Customers' DAO
     */
    public abstract CustomerDAO getCustomerDAO();

    /**
     * Return Areas' DAO
     * @return Areas' DAO
     */
    public abstract AreaDAO getAreaDAO();

}
