package com.example.quickrepair.domain;

import com.example.quickrepair.util.Utilities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Technician extends User
{
    private String AFM;
    private Set<Job> jobs = new HashSet<>();
    private Specialty specialty;
    private HashSet<String> areas = new HashSet<>();
    private Schedule schedule;

    public Technician()
    {
        schedule = new Schedule();
    }

    public Technician(String name, String surname, String phoneNumber, String email, String bankAccount, String username, String password, Specialty specialty, String AFM)
    {
        super(name, surname, phoneNumber, email, bankAccount, username, password);
        setSpecialty(specialty);
        setAFM(AFM);
        schedule = new Schedule();
    }

    //SETTERS

    /**
     * Set's this technician's specialty. All the current jobs are discarded
     *
     * @param specialty Technician's specialty
     */
    public void setSpecialty(Specialty specialty)
    {
        if (specialty == null) throw new NullPointerException("Speciality can not be null.");

        for (Job job : jobs)
        {
            job.getJobType().removeJob(job);
        }
        jobs.clear();

        this.specialty = specialty;
    }

    /**
     * Sets the technician personal info performing the necessary checks
     */
    public void setTechnicianInfo(String name, String surname, String phoneNumber, String email, String bankAccount, String username)
    {
        setUserInfo(name, surname, phoneNumber, email, bankAccount, username);
    }

    /**
     * Sets the technicians schedule performing the necessary checks.
     *
     * @param schedule The schedule of the technician.
     */
    public void setSchedule(Integer[][] schedule)
    {
        if (schedule == null)
        {
            throw new NullPointerException("Null schedule");
        }

        if (schedule.length != 7)
        {
            throw new IllegalArgumentException("The schedule must have 7 entries");
        }

        for (int i = 0; i < 7; i++)
        {
            if (schedule[i] == null || schedule[i].length != 2)
            {
                throw new IllegalArgumentException("Every entry must have 2 calendars.");
            }

            if (schedule[i][0] == null || schedule[i][1] == null)
            {
                throw new NullPointerException("null schedule entries on " + i);
            }

            if (schedule[i][0] > schedule[i][1])
            {
                throw new IllegalArgumentException("Starting hour can not be after ending hour.");
            }
        }

        for (int i = 0; i < 7; i++)
        {
            this.schedule.setSchedule(Calendar.SUNDAY + i, schedule[i][0], schedule[i][1]);
        }
    }

    /**
     * get Schedule for the given day
     *
     * @param day the day
     * @return Schedule for this day
     */
    public Schedule.ScheduleEntry getSchedule(int day)
    {
        return schedule.getSchedule(day);
    }

    /**
     * set AFM
     *
     * @param AFM The AFM
     */
    public void setAFM(String AFM)
    {
        if (AFM == null) throw new NullPointerException("null AFM");
        this.AFM = AFM;
    }

    /**
     * return all jobs
     *
     * @return all Jobs
     */
    public Set<Job> getJobs()
    {
        return jobs;
    }

    /**
     * return specialty
     *
     * @return The Specialty
     */
    public Specialty getSpecialty()
    {
        return specialty;
    }

    /**
     * return areas that this technician works
     *
     * @return Areas
     */
    public HashSet<String> getAreas()
    {
        return areas;
    }

    /**
     * return AFM
     *
     * @return The AFM
     */
    public String getAFM()
    {
        return AFM;
    }

    /**
     * Adds a job to the technician's list of jobs
     * Also updates the association references
     */
    public Job addJob(JobType jobType, double price)
    {
        if (jobType == null) throw new NullPointerException("JobType can not be null.");

        if (!jobType.getSpecialty().equals(getSpecialty()))
            throw new IllegalArgumentException("A technician can only offer jobs from his specialty.");

        if (price <= 0) throw new IllegalArgumentException();

        for (Job job : jobs)
        {
            if (job.getJobType().equals(jobType))
            {
                throw new IllegalArgumentException("Only one instance of each job is allowed.");
            }
        }

        Job job = new Job(this, jobType, price);
        jobs.add(job);
        jobType.addJob(job);

        return job;
    }

    /**
     * Removes a job from the technicians list of job
     * Also updates the list of jobs that offer the job's jobtype
     */
    public void removeJob(Job job)
    {
        if (job == null) throw new NullPointerException();
        if (!job.getTechnician().equals(this)) throw new IllegalArgumentException();

        getJobs().remove(job);
        job.getJobType().removeJob(job);
    }

    /**
     * Adds an area to the list of this technicians areas
     *
     * @param area the area to be added
     */
    public void addArea(String area)
    {
        if (area == null) throw new NullPointerException();

        areas.add(area);
    }

    /**
     * Removes an area from the list of this technicians areas
     *
     * @param area the area to be removed
     */
    public void removeArea(String area)
    {
        if (area == null) throw new NullPointerException();

        areas.remove(area);
    }

    /**
     * Checks if the technician serves the given area
     *
     * @param area the area we are testing
     * @return true if the technician serves the specified area
     */
    public boolean servesArea(String area)
    {
        if (area == null) throw new NullPointerException();

        return getAreas().contains(area);
    }

    public ArrayList<ArrayList<GregorianCalendar>> getAvailableHourRanges(GregorianCalendar date)
    {
        int dayOfWeek = date.get(Calendar.DAY_OF_WEEK);

        if (!isDayAvailable(dayOfWeek))
        {
            return null;
        }

        int start = schedule.getSchedule(dayOfWeek).getStartingHour();
        int end = schedule.getSchedule(dayOfWeek).getEndingHour();

        GregorianCalendar newDate = Utilities.getYearMonthDay(date);
        ArrayList<RepairRequest> repairRequests = new ArrayList<>();

        for (Job job : jobs)
        {
            for (RepairRequest repairRequest : job.getRepairRequests())
            {
                if (Utilities.getYearMonthDay(repairRequest.getConductionDate()).compareTo(newDate) == 0 && repairRequest.isConfirmed())
                {
                    repairRequests.add(repairRequest);
                }
            }
        }

        ArrayList<ArrayList<GregorianCalendar>> gaps = new ArrayList<>();

        if (repairRequests.isEmpty())
        {
            gaps.add(createGap(date, start, 0, end, 0));
        }
        else
        {
            //create gaps
            Collections.sort(repairRequests);

            //first gap
            RepairRequest firstRepairRequest = repairRequests.get(0);
            GregorianCalendar firstRepairReqCalendar = (GregorianCalendar) firstRepairRequest.getConductionDate().clone();

            int hour = firstRepairReqCalendar.get(Calendar.HOUR_OF_DAY);
            int min = firstRepairReqCalendar.get(Calendar.MINUTE);

            if (hour != start || min != 0)
            {
                gaps.add(createGap(date, start, 0, hour, min));
            }

            RepairRequest beforeRepairRequest = firstRepairRequest;
            GregorianCalendar beforeRepairReqCalendar = firstRepairReqCalendar;

            for (int i = 1; i < repairRequests.size(); i++)
            {
                //next step
                RepairRequest nextRepairRequest = repairRequests.get(i);
                GregorianCalendar nextRepairReqCalendar = (GregorianCalendar) nextRepairRequest.getConductionDate().clone();
                beforeRepairReqCalendar.add(Calendar.MINUTE, beforeRepairRequest.getEstimatedDuration());

                if (beforeRepairReqCalendar.before(nextRepairReqCalendar))
                {
                    int hourStart = beforeRepairReqCalendar.get(Calendar.HOUR_OF_DAY);
                    int minuteStart = beforeRepairReqCalendar.get(Calendar.MINUTE);

                    int hourEnd = nextRepairReqCalendar.get(Calendar.HOUR_OF_DAY);
                    int minuteEnd = nextRepairReqCalendar.get(Calendar.MINUTE);

                    gaps.add(createGap(date, hourStart, minuteStart, hourEnd, minuteEnd));
                }

                //else no gap between these repairRequests
                beforeRepairRequest = nextRepairRequest;
                beforeRepairReqCalendar = nextRepairReqCalendar;
            }

            beforeRepairReqCalendar.add(Calendar.MINUTE, beforeRepairRequest.getEstimatedDuration());

            if (beforeRepairReqCalendar.get(Calendar.HOUR_OF_DAY) < end)
            {
                int hourStart = beforeRepairReqCalendar.get(Calendar.HOUR_OF_DAY);
                int minuteStart = beforeRepairReqCalendar.get(Calendar.MINUTE);

                gaps.add(createGap(date, hourStart, minuteStart, end, 0));
            }
        }

        return gaps;
    }

    private ArrayList<GregorianCalendar> createGap(GregorianCalendar date, int hourStart, int minuteStart, int hourEnd, int minuteEnd)
    {
        ArrayList<GregorianCalendar> gap = new ArrayList<>();

        GregorianCalendar startCal = new GregorianCalendar(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH), hourStart, minuteStart);

        GregorianCalendar endCal;
        if (hourEnd == 24)
        {
            endCal = new GregorianCalendar(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH), hourEnd - 1, 59);
        }
        else
        {
            endCal = new GregorianCalendar(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH), hourEnd, minuteEnd);
        }

        gap.add(startCal);
        gap.add(endCal);

        return gap;
    }


    /**
     * Marks this technician as available the given day of the week from @param hourStart until
     *
     * @param hourEnd   Ideal
     *                  Example usage :  setAvailableOnDay(Calendar.MONDAY , 15 , 20)  to mark a technician available
     *                  on monday from 15:00 to 20:00
     *                  <p>
     *                  Current
     *                  Example usage :  setAvailableOnDay(Calendar.0 , 15 , 20)  to mark a technician available
     *                  * on monday from 15:00 to 20:00
     * @param day       0 for Monday - 6 for Sunday
     * @param hourStart
     * @param hourEnd
     */
    public void setAvailableOnDay(int day, int hourStart, int hourEnd)
    {
        schedule.setSchedule(day, hourStart, hourEnd);
    }


    /**
     * Checks if the technician is available on the given day of the week and hour of the week
     * without checking if the technician has a repair scheduled at that time
     *
     * @return true if the technician is available
     */
    public boolean isNormallyAvailable(int day, int hourOfDay)
    {
        if (!isDayAvailable(day)) return false;

        if (hourOfDay < 0 || hourOfDay > 24)
            throw new IllegalArgumentException("Hour must be between zero and twenty four inclusive");

        return schedule.getSchedule(day).getStartingHour() <= hourOfDay && schedule.getSchedule(day).getEndingHour() >= hourOfDay;
    }


    /**
     * Checks if the technician is available on the given day of the week
     *
     * @param day The day to check availability.
     * @return true if the technician is available the given day.
     */
    public boolean isDayAvailable(int day)
    {
        return schedule.getSchedule(day).getStartingHour() != schedule.getSchedule(day).getEndingHour();
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Technician that = (Technician) o;
        return Objects.equals(AFM, that.AFM) &&
                Objects.equals(jobs, that.jobs) &&
                Objects.equals(specialty, that.specialty) &&
                Objects.equals(areas, that.areas);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(super.hashCode(), AFM, specialty, areas);
    }
}
