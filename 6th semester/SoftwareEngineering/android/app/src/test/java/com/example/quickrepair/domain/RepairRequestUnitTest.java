package com.example.quickrepair.domain;

import android.os.health.SystemHealthManager;
import android.util.ArraySet;

import com.example.quickrepair.domain.Address;
import com.example.quickrepair.domain.Customer;
import com.example.quickrepair.domain.Job;
import com.example.quickrepair.domain.JobType;
import com.example.quickrepair.domain.MeasurementUnit;
import com.example.quickrepair.domain.Repair;
import com.example.quickrepair.domain.RepairRequest;
import com.example.quickrepair.domain.Specialty;
import com.example.quickrepair.domain.Technician;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

public class RepairRequestUnitTest
{
    private RepairRequest repairRequest;
    private Customer customer;

    @Before
    public void setUp()
    {
        Specialty specialty = new Specialty();
        GregorianCalendar standardDate = new GregorianCalendar(2018, 1, 1, 1, 0);
        Address exampleAddress = new Address("ath", "15");

        Technician technician = new Technician();
        technician.setSpecialty(specialty);

        JobType jobType = new JobType("Wiring", specialty, MeasurementUnit.METER);
        Job job = new Job(technician, jobType, 12);
        customer = new Customer();

        repairRequest = new RepairRequest(customer, job, standardDate, standardDate, exampleAddress, "comments");
    }

    @Test
    public void testExampleAddress()
    {
        Address address = new Address("Patission", "12");
        repairRequest.setAddress(address);
        Assert.assertEquals(address, repairRequest.getAddress());
    }

    @Test(expected = NullPointerException.class)
    public void nullComment()
    {
        repairRequest.setCommentsFromCustomer(null);
    }

    @Test
    public void okUid()
    {
        repairRequest.setUid(100);
        Assert.assertEquals(100, repairRequest.getUid());
    }

    @Test
    public void testComments()
    {
        String comments = "comments..";
        repairRequest.setCommentsFromCustomer(comments);
        Assert.assertEquals(comments, repairRequest.getCommentsFromCustomer());
    }

    @Test
    public void testCreationDates()
    {
        GregorianCalendar date = new GregorianCalendar(2019, 2, 2, 2, 2);
        repairRequest.setCreationDate(date);
        Assert.assertEquals(date, repairRequest.getCreationDate());
    }

    @Test
    public void testConductionDates()
    {
        GregorianCalendar date = new GregorianCalendar(2019, 2, 2, 2, 2);
        repairRequest.setConductionDate(date);
        Assert.assertEquals(date, repairRequest.getConductionDate());
    }

    @Test
    public void setRepair()
    {
        Repair repair = new Repair(repairRequest, 12);
        repairRequest.setRepair(repair);
        Assert.assertEquals(repair, repairRequest.getRepair());
    }

    @Test(expected = NumberFormatException.class)
    public void invalidDuration()
    {
        repairRequest.setEstimatedDuration(0);
    }

    @Test
    public void getterTest()
    {
        Assert.assertEquals(customer, repairRequest.getCustomer());

        repairRequest.setEstimatedDuration(100);
        Assert.assertEquals(100, repairRequest.getEstimatedDuration());
    }

    @Test
    public void compareToEqualsHourAndMin()
    {
        RepairRequest repairRequest1 = new RepairRequest();
        RepairRequest repairRequest2 = new RepairRequest();
        GregorianCalendar c1 = new GregorianCalendar(2018, 1, 1, 1, 0);
        GregorianCalendar c2 = new GregorianCalendar(2018, 1, 1, 1, 0);
        repairRequest1.setConductionDate(c1);
        repairRequest2.setConductionDate(c2);
        Assert.assertEquals(0, repairRequest1.compareTo(repairRequest2));
    }

    @Test
    public void compareToThisBeforeThatHour()
    {
        RepairRequest repairRequest1 = new RepairRequest();
        RepairRequest repairRequest2 = new RepairRequest();
        GregorianCalendar c1 = new GregorianCalendar(2018, 1, 1, 1, 0);
        GregorianCalendar c2 = new GregorianCalendar(2018, 1, 1, 2, 0);
        repairRequest1.setConductionDate(c1);
        repairRequest2.setConductionDate(c2);
        Assert.assertEquals(-1, repairRequest1.compareTo(repairRequest2));
    }

    @Test
    public void compareToThisBeforeThatMin()
    {
        RepairRequest repairRequest1 = new RepairRequest();
        RepairRequest repairRequest2 = new RepairRequest();
        GregorianCalendar c1 = new GregorianCalendar(2018, 1, 1, 1, 0);
        GregorianCalendar c2 = new GregorianCalendar(2018, 1, 1, 1, 1);
        repairRequest1.setConductionDate(c1);
        repairRequest2.setConductionDate(c2);
        Assert.assertEquals(-1, repairRequest1.compareTo(repairRequest2));
    }

    @Test
    public void compareToThisAfterThatHour()
    {
        RepairRequest repairRequest1 = new RepairRequest();
        RepairRequest repairRequest2 = new RepairRequest();
        GregorianCalendar c1 = new GregorianCalendar(2018, 1, 1, 2, 0);
        GregorianCalendar c2 = new GregorianCalendar(2018, 1, 1, 1, 0);
        repairRequest1.setConductionDate(c1);
        repairRequest2.setConductionDate(c2);
        Assert.assertEquals(1, repairRequest1.compareTo(repairRequest2));
    }

    @Test
    public void compareToThisAfterThatMin()
    {
        RepairRequest repairRequest1 = new RepairRequest();
        RepairRequest repairRequest2 = new RepairRequest();
        GregorianCalendar c1 = new GregorianCalendar(2018, 1, 1, 1, 1);
        GregorianCalendar c2 = new GregorianCalendar(2018, 1, 1, 1, 0);
        repairRequest1.setConductionDate(c1);
        repairRequest2.setConductionDate(c2);
        Assert.assertEquals(1, repairRequest1.compareTo(repairRequest2));
    }

    @Test
    public void sortedArrayOk()
    {
        ArrayList<RepairRequest> repairRequests = new ArrayList<>();
        RepairRequest repairRequest1 = new RepairRequest();
        RepairRequest repairRequest2 = new RepairRequest();
        GregorianCalendar c1 = new GregorianCalendar(2018, 1, 1, 2, 0);
        GregorianCalendar c2 = new GregorianCalendar(2018, 1, 1, 1, 0);
        repairRequest1.setConductionDate(c1);
        repairRequest2.setConductionDate(c2);

        repairRequests.add(repairRequest1);
        repairRequests.add(repairRequest2);

        Collections.sort(repairRequests);

        Assert.assertEquals(repairRequest2, repairRequests.get(0));
        Assert.assertEquals(repairRequest1, repairRequests.get(1));
    }

    @Test
    public void sortedArrayOk2()
    {
        ArrayList<RepairRequest> repairRequests = new ArrayList<>();
        RepairRequest repairRequest1 = new RepairRequest();
        RepairRequest repairRequest2 = new RepairRequest();
        GregorianCalendar c1 = new GregorianCalendar(2018, 1, 1, 1, 0);
        GregorianCalendar c2 = new GregorianCalendar(2018, 1, 1, 2, 0);
        repairRequest1.setConductionDate(c1);
        repairRequest2.setConductionDate(c2);

        repairRequests.add(repairRequest1);
        repairRequests.add(repairRequest2);

        Collections.sort(repairRequests);

        Assert.assertEquals(repairRequest1, repairRequests.get(0));
        Assert.assertEquals(repairRequest2, repairRequests.get(1));
    }

    @Test
    public void isCompletedFalse()
    {
        Assert.assertFalse(repairRequest.isCompleted());
    }

    @Test
    public void isCompletedTrue()
    {
        repairRequest.confirm(30);
        repairRequest.complete(2);
        Assert.assertTrue(repairRequest.isCompleted());
    }

    @Test(expected = IllegalStateException.class)
    public void completeAlreadyCompleted()
    {
        repairRequest.complete(2);
    }

    @Test
    public void isConfirmedFalse()
    {
        Assert.assertFalse(repairRequest.isConfirmed());
    }

    @Test
    public void isConfirmedTrue()
    {
        repairRequest.confirm(12);
        Assert.assertTrue(repairRequest.isConfirmed());
    }

    @Test
    public void isUnconfirmedTest()
    {
        Assert.assertTrue(repairRequest.isUnconfirmed());
    }

    @Test
    public void isRejectTest()
    {
        repairRequest.reject();
        Assert.assertTrue(repairRequest.isRejected());
    }

    @Test(expected = IllegalStateException.class)
    public void confirmX2()
    {
        repairRequest.confirm(12);
        repairRequest.confirm(12);
    }

    @Test
    public void rejectTrue()
    {
        repairRequest.reject();
        Assert.assertFalse(repairRequest.isConfirmed());
    }

    @Test(expected = IllegalStateException.class)
    public void rejectX2()
    {
        repairRequest.reject();
        repairRequest.reject();
    }

    @Test(expected = IllegalArgumentException.class)
    public void setConductionDateNull()
    {
        repairRequest.setConductionDate(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setCreationDateNull()
    {
        repairRequest.setCreationDate(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setAddressNull()
    {
        repairRequest.setAddress(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setCustomerNull()
    {
        repairRequest.setCustomer(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setRepairNull()
    {
        repairRequest.setRepair(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setJobNull()
    {
        repairRequest.setJob(null);
    }

    @Test
    public void equalsTest()
    {
        Specialty specialty = new Specialty();
        GregorianCalendar standardDate = new GregorianCalendar(2018, 1, 1, 1, 0);
        Address exampleAddress = new Address("ath", "15");

        Technician technician = new Technician();
        technician.setSpecialty(specialty);

        JobType jobType = new JobType("Wiring", specialty, MeasurementUnit.METER);
        Job job = new Job(technician, jobType, 12);

        RepairRequest r1 = new RepairRequest(customer, job, standardDate, standardDate, exampleAddress, "comments");

        System.out.println(standardDate.equals(repairRequest.getCreationDate()));
        System.out.println(job.getTechnician().equals(repairRequest.getJob().getTechnician()));

        Assert.assertNotEquals(null, repairRequest);
        Assert.assertEquals(repairRequest, repairRequest);
        Assert.assertNotEquals(repairRequest, "10");
        Assert.assertEquals(repairRequest, r1);

        r1 = new RepairRequest(customer, job, standardDate, standardDate, exampleAddress, "comment");
        Assert.assertNotEquals(repairRequest, r1);
    }

    @Test
    public void hashcodeTest()
    {
        Specialty specialty = new Specialty();
        GregorianCalendar standardDate = new GregorianCalendar(2018, 1, 1, 1, 0);
        Address exampleAddress = new Address("ath", "15");

        Technician technician = new Technician();
        technician.setSpecialty(specialty);

        JobType jobType = new JobType("Wiring", specialty, MeasurementUnit.METER);
        Job job = new Job(technician, jobType, 12);

        RepairRequest r1 = new RepairRequest(customer, job, standardDate, standardDate, exampleAddress, "comments");

        Assert.assertTrue(repairRequest.equals(r1) && r1.equals(repairRequest));
        Assert.assertTrue(repairRequest.hashCode() == r1.hashCode());
    }
}