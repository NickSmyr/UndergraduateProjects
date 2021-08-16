package com.example.quickrepair.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Specialty
{
    private int uid;
    private String name;

    private Set<JobType> jobTypes = new HashSet<>();

    /**
     * Empty Constructor
     */
    public Specialty()
    {
    }

    /**
     * Specialty's Constructor
     * @param name Specialty's name
     */
    public Specialty(String name)
    {
        setName(name);
    }

    /**
     * return specialty's name
     * @return Specialty's name
     */
    public String getName()
    {
        return name;
    }

    /**
     * return job types for this specialty
     * @return
     */
    public Set<JobType> getJobTypes()
    {
        return new HashSet<>(jobTypes);
    }

    /**
     * set specialty's name
     * @param name specialty's name
     */
    public void setName(String name)
    {
        if (name == null) throw new NullPointerException("Speciality name can not be null.");
        if (name.isEmpty()) throw new IllegalArgumentException("Speciality name can not be empty.");

        this.name = name;
    }

    /**
     * Get the speciality UID.
     *
     * @return The speciality UID.
     */
    public int getUid()
    {
        return uid;
    }

    /**
     * Set the speciality UID.
     *
     * @param uid The speciality UID.
     */
    public void setUid(int uid)
    {
        this.uid = uid;
    }

    public void addJobType(JobType jobType)
    {
        if (jobType == null) throw new NullPointerException("Job type can not be null.");

        jobTypes.add(jobType);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Specialty specialty = (Specialty) o;
        return Objects.equals(name, specialty.name);// && Objects.equals(jobTypes, specialty.jobTypes);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(name);
    }
}
