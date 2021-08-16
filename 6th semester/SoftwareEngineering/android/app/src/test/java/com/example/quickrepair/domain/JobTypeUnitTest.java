package com.example.quickrepair.domain;

import com.example.quickrepair.domain.Job;
import com.example.quickrepair.domain.JobType;
import com.example.quickrepair.domain.MeasurementUnit;
import com.example.quickrepair.domain.Specialty;
import com.example.quickrepair.domain.Technician;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class JobTypeUnitTest
{
    private Job exampleJob;
    private Specialty exampleSpecialty;
    private JobType exampleJobType;

    @Before
    public void setUpTests()
    {

        exampleSpecialty = new Specialty("Builder");
        exampleJobType = new JobType("Tiles", exampleSpecialty, MeasurementUnit.SQR_METER);
        Technician exampleTechnician = new Technician();
        exampleJob = new Job(exampleTechnician, exampleJobType, 1.5);
    }

    @Test
    public void addJobsTest()
    {
        exampleJobType.addJob(exampleJob);
        Assert.assertTrue(exampleJobType.getJobs().contains(exampleJob));

        exampleJobType.removeJob(exampleJob);
        Assert.assertFalse(exampleJobType.getJobs().contains(exampleJob));
    }

    @Test
    public void testGetters()
    {
        Assert.assertEquals(exampleJobType.getSpecialty(), exampleSpecialty);
        Assert.assertEquals(MeasurementUnit.SQR_METER, exampleJobType.getMeasurementUnit());

        exampleJobType.setUid(100);
        Assert.assertEquals(100, exampleJobType.getUid());

        Assert.assertEquals("Tiles", exampleJobType.getName());
    }

    @Test
    public void equalsTest()
    {
        Assert.assertNotEquals(null, exampleJobType);
        Assert.assertEquals(exampleJobType, exampleJobType);
        Assert.assertNotEquals(exampleJobType, exampleJob);

        JobType other = new JobType("Breaker", new Specialty("Electrician"), MeasurementUnit.NONE);
        Assert.assertNotEquals(other, exampleJobType);

        other = new JobType("Tiles", exampleSpecialty, MeasurementUnit.SQR_METER);
        Assert.assertEquals(other, exampleJobType);
    }

    @Test
    public void testHashCode()
    {
        JobType other = new JobType("Tiles", exampleSpecialty, MeasurementUnit.SQR_METER);

        Assert.assertTrue(exampleJobType.equals(other) && other.equals(exampleJobType));
        Assert.assertTrue(exampleJobType.hashCode() == other.hashCode());
    }
}
