package com.example.quickrepair.view.RequestRepair;

import java.util.List;

public interface RequestRepairView
{
    /**
     * Display the technicians phone number
     * @param text the phone number to display
     */
    void setTechnicianPhoneNumber(String text);

    /**
     * Display the technicians name
     * @param text the name to display
     */
    void setTechnicianName(String text);

    /**
     * Displays the job type name
     * @param text the job type name to display
     */
    void setJobTypeName(String text);

    /**
     * Fills a list with the time ranges that the technician is available
     * @param availableHours A list of strings each string representing an hour range
     */
    void showTimesAvailable(List<String> availableHours);

    /**
     * Shows an error message
     * @param error  the error message
     */
    void showError(String error);

    /**
     * Return to the customer home page.
     */
    void submit();

}
