package com.example.quickrepair.view.SearchTechnicians;

import android.util.Log;

import com.example.quickrepair.dao.AreaDAO;
import com.example.quickrepair.dao.CustomerDAO;
import com.example.quickrepair.dao.EvaluationDAO;
import com.example.quickrepair.dao.JobTypeDAO;
import com.example.quickrepair.dao.SpecialtyDAO;
import com.example.quickrepair.dao.TechnicianDAO;
import com.example.quickrepair.domain.Evaluation;
import com.example.quickrepair.domain.Job;
import com.example.quickrepair.domain.JobType;
import com.example.quickrepair.domain.RepairRequest;
import com.example.quickrepair.domain.Specialty;
import com.example.quickrepair.domain.Technician;
import com.example.quickrepair.memorydao.EvaluationDAOMemory;
import com.example.quickrepair.memorydao.MemoryInitializer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Presenter class that implements the search technicians use case
 */
public class SearchTechniciansPresenter
{
    private SearchTechniciansView view;
    private TechnicianDAO technicianDAO;
    private SpecialtyDAO specialtyDAO;
    private JobTypeDAO jobTypeDAO;
    private AreaDAO areaDAO;
    private int loggedInUser = 0;
    private int selectedSpecialtyId = -1;
    private double selectedMaxPrice = -1;
    private String selectedArea = null;
    private GregorianCalendar date;


    public SearchTechniciansPresenter(TechnicianDAO technicianDAO, SpecialtyDAO specialtyDAO, JobTypeDAO jobTypeDAO, AreaDAO areaDAO)
    {
        this.technicianDAO = technicianDAO;
        this.specialtyDAO = specialtyDAO;
        this.jobTypeDAO = jobTypeDAO;
        this.areaDAO = areaDAO;
    }

    /**
     * Sets the view for this presenter. Should be called immediately after construction
     * @param view the view
     */
    public void setView(SearchTechniciansView view)
    {
        this.view = view;
    }

    /**
     * Sets the user that is currently logged in
     *
     * @param userId the user Id
     */
    public void setLoggedInUser(int userId)
    {
        loggedInUser = userId;
    }

    /**
     * Starts the use case by looking through the dao and keeping all the specialties
     */
    public void onStart()
    {
        List<Specialty> specialties = specialtyDAO.findAll();
        List<Integer> specialtyIds = new ArrayList<>();
        List<String> specialtyNames = new ArrayList<>();
        for (Specialty e : specialties)
        {
            specialtyIds.add(e.getUid());
            specialtyNames.add(e.getName());
        }
        view.setSpecialtiesSource(specialtyIds, specialtyNames);
        view.setJobTypeSpinnerEnabled(false);
        view.setAreasSource(areaDAO.getAreas());
    }

    /**
     * Responds to the user selecting a specialty
     * @param specialtyId the selected specialty
     */
    public void selectSpecialty(int specialtyId)
    {
        List<JobType> jobTypes = jobTypeDAO.findAll();
        List<Integer> jobTypeIds = new ArrayList<>();
        List<String> jobTypeNames = new ArrayList<>();
        //Selecting only jobtypes of selected specialty
        for (JobType e : jobTypes)
        {
            if (e.getSpecialty().getUid() == specialtyId)
            {
                jobTypeIds.add(e.getUid());
                jobTypeNames.add(e.getName());
            }
        }
        selectedSpecialtyId = specialtyId;
        view.setJobTypesSource(jobTypeIds, jobTypeNames);
        //Setting the job type drop down as enabled so the user can choose
        view.setJobTypeSpinnerEnabled(true);
    }

    /**
     * Sets the date the customer wants the technician to be available and preforms validity checks
     * @param yearString the year as entered by the user
     * @param monthString the month as entered by the user
     * @param dayOfMonthString the day of the month as entered by the user
     */
    public void setDate(String yearString, String monthString, String dayOfMonthString)
    {
        try
        {
            int year = Integer.parseInt(yearString);
            int month = Integer.parseInt(monthString);
            int dayOfMonth = Integer.parseInt(dayOfMonthString);
            if (year >= 0 && month <= 12 && month >= 1 && dayOfMonth <= 31 && dayOfMonth >= 0)
            {
                date = new GregorianCalendar(year, month, dayOfMonth);
            }
            else
            {
                throw new Exception("");
            }
        }
        catch (Exception e)
        {
            view.showErrorMessage("Please enter a valid Date (YYYY/MM/DD)");
        }
    }

    /**
     * A list that represents the technicians that are showed on the view
     */
    private int selectedJobTypeId = -1;

    /**
     * Selects the jobType to search for
     *
     * @param jobTypeId
     */
    public void selectJobType(int jobTypeId)
    {
        selectedJobTypeId = jobTypeId;
    }

    /**
     * Filters the technicians list according to the selected area
     *
     * @param area the selected area
     */

    public void setArea(String area)
    {
        selectedArea = area;
    }

    /**
     * Filters the technician list according to their maximum price for the jobtype
     *
     * @param input the input maximum price
     */
    public void setMaxPrice(String input)
    {
        //If the input is null or empty we don't set an upper bound
        if (input == null || input.equals("")) return;
        double price = 0;
        //Checking if user has entered a valid price
        try
        {
            price = Double.parseDouble(input);
        }
        catch (NumberFormatException e)
        {
            view.showErrorMessage("Please enter a valid from of price");
            return;
        }

        selectedMaxPrice = price;
    }

    /**
     * Called when a user clicks on a technician on the list
     * @param technicianId the id of the technician that was selected
     */
    public void onTechnicianClick(int technicianId)
    {
        if (date == null)
        {
            view.showErrorMessage("Please enter a valid Date (YYYY/MM/DD)");
            return;
        }

        if (loggedInUser != 0)
        {
            view.navigateToRequestRepair(technicianId, selectedJobTypeId, date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH));
        }
        else
        {
            view.navigateToLogin(technicianId, selectedJobTypeId, date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH));
        }
    }

    /**
     * Called when the search button is pressed from the view and repopulates the technician list
     * if the user has entered correct data
     * @param jobID the job type id
     * @param price the price id
     * @param area the area
     * @param year the year
     * @param month the month
     * @param day the day
     */
    public void search(int jobID, String price, String area, String year, String month, String day)
    {
        selectedJobTypeId = -1;
        selectedArea = null;
        selectedMaxPrice = -1;
        date = null;

        selectJobType(jobID);
        setMaxPrice(price);
        setArea(area);
        setDate(year, month, day);

        if (selectedJobTypeId == -1 || selectedArea == null || date == null)
        {
            return;
        }

        repopulateTechnicianList();
    }

    /**
     * Searches the DAO and applies the given criteria to find the right technicians and populates
     * the view with results
     */
    private void repopulateTechnicianList()
    {
        List<Integer> technicianIds = new ArrayList<>();
        List<String> technicianNames = new ArrayList<>();
        List<Double> averageRatings = new ArrayList<>();
        List<Double> prices = new ArrayList<>();
        for (Technician technician : technicianDAO.findAll())
        {
            boolean hasSpecialty = selectedSpecialtyId == -1 || technician.getSpecialty().getUid() == selectedSpecialtyId;
            boolean offersJobType = selectedJobTypeId == -1 || offersJobType(technician.getUid(), selectedJobTypeId);
            boolean underMaxPrice = selectedJobTypeId == -1 || selectedMaxPrice < 0 || offersJobTypeForLessThan(technician.getUid(), selectedJobTypeId, selectedMaxPrice);
            boolean servesArea = selectedArea == null || technician.servesArea(selectedArea);
            boolean available = technician.isDayAvailable(date.get(Calendar.DAY_OF_WEEK));
            if (!(hasSpecialty && offersJobType && underMaxPrice && servesArea && available))
            {
                continue;
            }
            technicianIds.add(technician.getUid());
            technicianNames.add(technician.getName());
            averageRatings.add(getTechnicianAverageRatings(technician.getUid()));
            prices.add(getTechnicianPriceForJobType(technician.getUid(), selectedJobTypeId));
        }
        view.populateTechnicianList(technicianIds, technicianNames, averageRatings, prices);
    }


    /**
     * Checks if the technician offers the given jobtype
     * @param technicianId the technician id
     * @param jobTypeId the job type id
     * @return true if technician offers the jobtype
     */
    private boolean offersJobType(int technicianId, int jobTypeId)
    {
        Technician technician = technicianDAO.find(technicianId);
        for (Job job : technician.getJobs())
        {
            if (job.getJobType().getUid() == jobTypeId)
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the technician offers the given jobtype for less than the given amount of money
     * @param technicianId the tehcnician id
     * @param jobTypeId the jobtype id
     * @param price the price
     * @return true of technician offers the job type for less than price
     */
    private boolean offersJobTypeForLessThan(int technicianId, int jobTypeId, double price)
    {
        Technician technician = technicianDAO.find(technicianId);
        for (Job job : technician.getJobs())
        {
            if (job.getJobType().getUid() == jobTypeId)
            {
                if (job.getPrice() <= price)
                {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns the technicians price for the given jobtype
     * @param  technicianId the technician's id
     * @param jobTypeId the job type id
     * @return  the technicians price for the job type
     */
    private double getTechnicianPriceForJobType(int technicianId, int jobTypeId)
    {
        Technician technician = technicianDAO.find(technicianId);
        for (Job job : technician.getJobs())
        {
            if (job.getJobType().getUid() == jobTypeId)
            {
                return job.getPrice();
            }
        }
        return 0;
    }

    /**
     * Calculates the average ratings for a technician
     * @param technicianId the technicians id
     * @return the average of all ratings for the technician
     */
    private double getTechnicianAverageRatings(int technicianId)
    {
        Technician technician = technicianDAO.find(technicianId);

        int ratingSum = 0;
        int nRatings = 0;
        for (Job job : technician.getJobs())
        {
            for (RepairRequest repairRequest : job.getRepairRequests())
            {
                //Evaluation exists if repair request is completed
                if (repairRequest.isCompleted() && repairRequest.getRepair().getEvaluation() != null)
                {

                    Evaluation evaluation = repairRequest.getRepair().getEvaluation();
                    System.out.println("Found 1 evalutaion" + evaluation.getComment());
                    ratingSum += evaluation.getRate();
                    nRatings++;
                }
            }
        }
        return nRatings == 0 ? 0 : ratingSum / (float) nRatings;
    }
}
