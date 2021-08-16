package com.example.quickrepair.view.RequestRepair;

import android.widget.ArrayAdapter;

import com.example.quickrepair.dao.CustomerDAO;
import com.example.quickrepair.dao.JobTypeDAO;
import com.example.quickrepair.dao.RepairRequestDAO;
import com.example.quickrepair.dao.TechnicianDAO;
import com.example.quickrepair.domain.Address;
import com.example.quickrepair.domain.Customer;
import com.example.quickrepair.domain.Job;
import com.example.quickrepair.domain.JobType;
import com.example.quickrepair.domain.RepairRequest;
import com.example.quickrepair.domain.Technician;
import com.example.quickrepair.memorydao.TechnicianDAOMemory;
import com.example.quickrepair.util.Utilities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class RequestRepairPresenter
{
    private RequestRepairView view;
    private TechnicianDAO technicianDAO;
    private JobTypeDAO jobTypeDAO;
    private CustomerDAO customerDAO;
    private RepairRequestDAO repairRequestDAO;

    private Technician selectedTechnician;
    private Customer loggedInUser;

    private int year;
    private int month;
    private int day;

    private int hourOfDay = -1;
    private int minutes = -1;

    private JobType selectedJobType;

    public RequestRepairPresenter(TechnicianDAO technicianDAO, JobTypeDAO jobTypeDAO, CustomerDAO customerDAO, RepairRequestDAO repairRequestDAO)
    {
        this.technicianDAO = technicianDAO;
        this.jobTypeDAO = jobTypeDAO;
        this.customerDAO = customerDAO;
        this.repairRequestDAO = repairRequestDAO;
    }

    /**
     * Sets the view for the presenter
     * @param view the view
     */
    public void setView(RequestRepairView view)
    {
        this.view = view;
    }

    /**
     * Sets the technician id for whom the repair request will take place
     * @param technicianId the technician's id
     */
    public void setTechnicianId(int technicianId)
    {
        selectedTechnician = technicianDAO.find(technicianId);

        if (selectedTechnician == null)
        {
            view.showError("Something went wrong. The technician could not be found.");
        }
    }

    /**
     * Sets the jobType Id the customer requests a repair for
     * @param jobTypeId the id of the jobtype
     */
    public void setJobTypeId(int jobTypeId)
    {
        selectedJobType = jobTypeDAO.find(jobTypeId);
    }

    /**
     * Sets the current user logged in. To request a repair the user must be a customer
     * @param userId The Id of the logged in customer
     */
    public void setLoggedInUser(int userId)
    {
        loggedInUser = customerDAO.find(userId);
    }

    /**
     * Sets the date the repair request will take place
     * @param day the day
     * @param year  the year
     * @param month the month
     */
    public void setDate(int year, int month, int day)
    {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    //Inputs from user during activity
    private String selectedComments = "";

    /**
     * Sets the comments from the customer to the repairRequest
     * @param comments  the comments from the customer
     */
    public void setComments(String comments)
    {
        if (comments != null) this.selectedComments = comments;
    }

    private Address selectedAddress = null;

    /**
     * Sets the address for the repair Request
     * @param  address the address in the form <i>StreetName</i> , <i>Number</i>
     */
    public void setAddress(String address)
    {
        try
        {
            String[] tokens = address.split(",");
            Integer.parseInt(tokens[1].trim());
            selectedAddress = new Address(tokens[0].trim(), tokens[1].trim());
        }
        catch (Exception e)
        {
            view.showError("Please enter an address in the correct form e.g. your_address_name , number ");
        }
    }

    /**
     * Sets the time for the repair request
     * @param hourOfDay  the hour of the day [0 ,23]
     * @param minutes the minutes [0, 59]
     */
    public void setTime(int hourOfDay, int minutes)
    {
        if (hourOfDay < 24 && hourOfDay >= 0 && minutes < 60 && minutes >= 0)
        {
            if (!timeInRange(hourOfDay, minutes, selectedTechnician, year, month, day))
            {
                view.showError("The technician is not available on that time. Please enter an alternate time");
                return;
            }

            this.hourOfDay = hourOfDay;
            this.minutes = minutes;
        }
        else
        {
            view.showError("Please enter a time between 0:00 and 23:59");
        }
    }

    /**
     * Method called when the view is started
     */
    public void onStart()
    {
        view.setJobTypeName(selectedJobType.getName());
        view.setTechnicianName(selectedTechnician.getName());
        view.setTechnicianPhoneNumber(selectedTechnician.getPhoneNumber());
        List<String> timesAvailableForView = createListFromHourRanges(selectedTechnician);
        view.showTimesAvailable(timesAvailableForView);
    }

    /**
     * Called when the user confirms the repair request
     */
    public void requestRepair()
    {
        GregorianCalendar dateNow = (GregorianCalendar) Calendar.getInstance();
        GregorianCalendar date;

        if (hourOfDay == -1 || minutes == -1)
        {
            return;
        }

        date = new GregorianCalendar(year, month, day, hourOfDay, minutes);
        Job job = getJobFromJobType(selectedTechnician, selectedJobType);
        String comments = selectedComments;

        if (selectedAddress == null)
        {
            return;
        }

        Address address = selectedAddress;
        RepairRequest result = loggedInUser.requestRepair(dateNow, date, job, comments, address);
        result.setUid(repairRequestDAO.nextId());
        repairRequestDAO.save(result);

        view.submit();
    }

    /**
     * returns the job the techcnician has for the job type
     * @param technician the technician
     * @param jobType the job type of the returned job
     * @return the job of the jobtype for the technician
     */
    private Job getJobFromJobType(Technician technician, JobType jobType)
    {
        for (Job job : technician.getJobs())
        {
            if (job.getJobType().getUid() == jobType.getUid())
            {
                return job;
            }
        }
        return null;
    }

    /**
     * Returns true if the input time is between the range of the calendars (taking into consideration
     * only the hourofday and minutes)
     * @param hourOfDay the hour of the day
     * @param minutes the minutes
     * @param  start the gregorian calendar containing the start hour and minutes
     * @param  end the gregorian calendar containing the end hour and minutes
     */
    private boolean timeInRange(int hourOfDay, int minutes, GregorianCalendar start, GregorianCalendar end)
    {
        int year = start.get(Calendar.YEAR);
        int month = start.get(Calendar.MONTH);
        int dayOfMonth = start.get(Calendar.DAY_OF_MONTH);

        GregorianCalendar target = new GregorianCalendar(year, month, dayOfMonth, hourOfDay, minutes);
        //Using gregorianCalendar's built in compare to test if the target is between the range
        // of the calendars

        return target.compareTo(end) < 0 && target.compareTo(start) >= 0;

    }

    /**
     * Checks if the technician is available on the certain time
     * @param hourOfDay the hour of day
     * @param minutes the minutes
     * @param technician the technician to check if he is available
     * @param year the year
     * @param month the month
     * @param dayOfMonth the day of the month
     * @return true if the technician is available on the given date and time
     */
    private boolean timeInRange(int hourOfDay, int minutes, Technician technician, int year, int month, int dayOfMonth)
    {
        if (technician == null) return false;

        ArrayList<ArrayList<GregorianCalendar>> hourRanges = technician.getAvailableHourRanges(new GregorianCalendar(year, month, dayOfMonth, 2, 2));
        for (ArrayList<GregorianCalendar> list : hourRanges)
        {
            if (timeInRange(hourOfDay, minutes, list.get(0), list.get(1)))
            {
                return true;
            }
        }

        return false;
    }

    /**
     * Creates a list to print from the hour ranges of the technician
     * @param technician the technician
     * @return a list containing string representations of the technicians hour ranges
     */
    List<String> createListFromHourRanges(Technician technician)
    {
        ArrayList<ArrayList<GregorianCalendar>> hourRanges = technician.getAvailableHourRanges(new GregorianCalendar(year, month, day, 2, 2));
        List<String> timesAvailableForView = new ArrayList<>();
        for (ArrayList<GregorianCalendar> calendars : hourRanges)
        {
            GregorianCalendar start = calendars.get(0);
            GregorianCalendar end = calendars.get(1);
            int startHour = start.get(Calendar.HOUR_OF_DAY);
            int startMinutes = start.get(Calendar.MINUTE);
            int endHour = end.get(Calendar.HOUR_OF_DAY);
            int endMinutes = end.get(Calendar.MINUTE);
            String result = startHour + ":" + startMinutes + " - " + endHour + ":" + endMinutes;

            start.add(Calendar.DAY_OF_MONTH, 1);
            int difference = start.compareTo(end);
            if (difference >= 0 && endHour == 0)
            {
                //This means the end gap was set to 24 so the calendar was rolled forward a day
                result = startHour + ":" + startMinutes + " - 23:59";
            }
            timesAvailableForView.add(result);
        }
        return timesAvailableForView;
    }
}
