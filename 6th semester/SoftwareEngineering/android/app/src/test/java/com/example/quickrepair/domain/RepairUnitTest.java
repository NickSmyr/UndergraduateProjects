package com.example.quickrepair.domain;

import com.example.quickrepair.domain.Address;
import com.example.quickrepair.domain.Customer;
import com.example.quickrepair.domain.Evaluation;
import com.example.quickrepair.domain.Job;
import com.example.quickrepair.domain.JobType;
import com.example.quickrepair.domain.MeasurementUnit;
import com.example.quickrepair.domain.Payment;
import com.example.quickrepair.domain.PaymentType;
import com.example.quickrepair.domain.Repair;
import com.example.quickrepair.domain.RepairRequest;
import com.example.quickrepair.domain.Specialty;
import com.example.quickrepair.domain.Technician;
import com.example.quickrepair.util.Utilities;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class RepairUnitTest
{
    private Job job;
    private Job job_NoUnit;
    private Repair repair;

    @Before
    public void setUpTests()
    {
        Technician technician = new Technician();
        Specialty specialty = new Specialty("Electrician");

        JobType jobType = new JobType("Wiring", specialty, MeasurementUnit.METER);
        job = new Job(technician, jobType, 15);

        jobType = new JobType("Wiring", specialty, MeasurementUnit.NONE);
        job_NoUnit = new Job(technician, jobType, 15);
        repair = new Repair();

    }

    @Test
    public void constructorTest()
    {
        RepairRequest repairRequest = new RepairRequest();
        repairRequest.setJob(job);

        repair.setRepairRequest(repairRequest);
        repair.setQuantity(10.5);

        Assert.assertEquals(repair, new Repair(repairRequest, 10.5));
    }

    @Test(expected = NullPointerException.class)
    public void nullRepairRequest()
    {
        repair.setRepairRequest(null);
    }

    @Test
    public void okRepairRequest()
    {
        RepairRequest repairRequest = new RepairRequest();
        repairRequest.setJob(job);

        repair.setRepairRequest(repairRequest);
        Assert.assertEquals(repairRequest, repair.getRepairRequest());
    }

    //Quantity Tests
    @Test(expected = IllegalArgumentException.class)
    public void repairWithZeroQuantity()
    {
        repair.setQuantity(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void decimalQuantity()
    {
        RepairRequest repairRequest = new RepairRequest();
        repairRequest.setJob(job_NoUnit);

        repair.setRepairRequest(repairRequest);
        repair.setQuantity(10.5);
    }

    @Test
    public void decimalQuantityOk()
    {
        RepairRequest repairRequest = new RepairRequest();
        repairRequest.setJob(job);

        repair.setRepairRequest(repairRequest);
        repair.setQuantity(10.5);
        Assert.assertEquals(10.5, repair.getQuantity(), 0.0001);
    }

    @Test
    public void intQuantityOk()
    {
        RepairRequest repairRequest = new RepairRequest();
        repairRequest.setJob(job_NoUnit);

        repair.setRepairRequest(repairRequest);
        repair.setQuantity(10);
        Assert.assertEquals(10, repair.getQuantity(), 0);
    }

    @Test
    public void okUid()
    {
        repair.setUid(100);
        Assert.assertEquals(100, repair.getUid());
    }

    @Test(expected = IllegalStateException.class)
    public void payTest()
    {
        Assert.assertFalse(repair.isPaid());

        RepairRequest repairRequest = new RepairRequest();
        repairRequest.setJob(job);

        repair.setRepairRequest(repairRequest);
        repair.setQuantity(10.5);

        GregorianCalendar date = new GregorianCalendar(2020, 1, 1);

        Payment payment = repair.pay(date, PaymentType.CARD);
        Assert.assertEquals(new Payment(repair, date, PaymentType.CARD), payment);
        Assert.assertEquals(157.5, payment.getCost(), 0.001);
        Assert.assertTrue(repair.isPaid());

        repair.pay(date, PaymentType.CARD);
    }

    @Test(expected = IllegalStateException.class)
    public void evaluateTest()
    {
        Assert.assertFalse(repair.isEvaluated());

        Evaluation evaluation = repair.evaluate("Title", "Comment", 3);


        Assert.assertEquals(new Evaluation(repair, "Title", "Comment", 3), evaluation);
        Assert.assertTrue(repair.isEvaluated());

        repair.evaluate("Title", "Comment", 3);
    }

    @Test
    public void equalsTest()
    {
        Assert.assertNotEquals(null, repair);
        Assert.assertEquals(repair, repair);
        Assert.assertNotEquals(repair, new Integer(10));

        Repair r1 = new Repair();
        Assert.assertEquals(repair, r1);

        r1.setRepairRequest(new RepairRequest());
        Assert.assertNotEquals(repair, r1);
    }

    @Test
    public void hashcodeTest()
    {
        Repair r1 = new Repair();

        Assert.assertTrue(repair.equals(r1) && r1.equals(repair));
        Assert.assertTrue(repair.hashCode() == r1.hashCode());
    }
}
