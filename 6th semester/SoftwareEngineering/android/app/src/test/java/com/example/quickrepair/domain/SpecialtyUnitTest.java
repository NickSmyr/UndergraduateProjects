package com.example.quickrepair.domain;

import com.example.quickrepair.domain.JobType;
import com.example.quickrepair.domain.MeasurementUnit;
import com.example.quickrepair.domain.Specialty;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;

public class SpecialtyUnitTest
{
    Specialty specialty;

    @Before
    public void setUp()
    {
        specialty = new Specialty();
    }

    @Test(expected = NullPointerException.class)
    public void setNameNull()
    {
        specialty.setName(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setNameSize()
    {
        specialty.setName("");
    }

    @Test
    public void setNameOk()
    {
        specialty.setName("Electrician");
        Assert.assertEquals("Electrician", specialty.getName());
    }

    @Test(expected = NullPointerException.class)
    public void addJobTypeNull()
    {
        specialty.addJobType(null);
    }

    @Test
    public void okUid()
    {
        specialty.setUid(100);
        Assert.assertEquals(100, specialty.getUid());
    }

    @Test
    public void addJobTypeΟk()
    {
        specialty.addJobType(new JobType());
        Assert.assertEquals(1, specialty.getJobTypes().size());
    }

    @Test
    public void equalsTest()
    {
        Assert.assertEquals(false, specialty.equals(null));
        Assert.assertEquals(true, specialty.equals(specialty));
        Assert.assertEquals(false, specialty.equals(new Integer(5)));
        Assert.assertEquals(true, specialty.equals(new Specialty()));
        Assert.assertEquals(false, specialty.equals(new Specialty("Electrician")));
    }

    @Test
    public void hashcodeTest()
    {
        specialty.setName("Electrician");
        Specialty s1 = new Specialty("Electrician");

        Assert.assertTrue(specialty.equals(s1) && s1.equals(specialty));
        Assert.assertTrue(specialty.hashCode() == s1.hashCode());
    }
}
