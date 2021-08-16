package com.example.quickrepair.domain;
import com.example.quickrepair.domain.Job;
import com.example.quickrepair.domain.JobType;
import com.example.quickrepair.domain.MeasurementUnit;
import com.example.quickrepair.domain.Repair;
import com.example.quickrepair.domain.RepairRequest;
import com.example.quickrepair.domain.Specialty;
import com.example.quickrepair.domain.Technician;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Objects;

public class JobUnitTest
{
    private Job exampleJob;
    private Technician exampleTechnician;
    private JobType exampleJobType;

    @Before
    public void setUpTests()
    {
        Specialty specialty = new Specialty("Builder");

        exampleJobType = new JobType("Tiles", specialty, MeasurementUnit.SQR_METER);
        exampleTechnician = new Technician();
        exampleTechnician.setSpecialty(specialty);

        exampleJob = new Job(exampleTechnician, exampleJobType, 1.5);
    }

    @Test
    public void testEquals()
    {
        Job other = new Job(exampleTechnician, exampleJobType, 1.5);
        Assert.assertEquals(exampleJob, other);
        Job wrongOther1 = new Job(exampleTechnician, exampleJobType, 1.5);
        Job wrongOther2 = new Job(exampleTechnician, exampleJobType, 1.6);
        Job wrongOther3 = null;
        Assert.assertNotEquals(wrongOther1, exampleTechnician);
        Assert.assertNotEquals(wrongOther2, exampleTechnician);
        Assert.assertNotEquals(wrongOther3, exampleTechnician);
    }

    @Test
    public void testGetters()
    {
        Assert.assertEquals(exampleJob.getJobType(), exampleJobType);
        Assert.assertTrue(exampleJob.getPrice() == 1.5);
        Assert.assertEquals(exampleJob.getTechnician(), exampleTechnician);

        exampleJob.setUid(100);
        Assert.assertEquals(100, exampleJob.getUid());
    }

    @Test(expected = NullPointerException.class)
    public void nullRepairRequest()
    {
        exampleJob.addRepairRequest(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidJobTypeRepairRequest()
    {
        RepairRequest repairRequest = new RepairRequest();
        repairRequest.setJob(new Job());

        exampleJob.addRepairRequest(repairRequest);
    }

    @Test
    public void addRepairRequest()
    {
        RepairRequest repairRequest = new RepairRequest();
        repairRequest.setJob(exampleJob);

        exampleJob.addRepairRequest(repairRequest);
        Assert.assertEquals(1, exampleJob.getRepairRequests().size());

        exampleJob.removeRepairRequest(repairRequest);
        Assert.assertEquals(0, exampleJob.getRepairRequests().size());
    }

    @Test
    public void testHashCode()
    {
        Job job = new Job(exampleTechnician, exampleJobType, 1.5);

        Assert.assertTrue(exampleJob.equals(job) && job.equals(exampleJob));
        Assert.assertTrue(exampleJob.hashCode() == job.hashCode());
    }
}
