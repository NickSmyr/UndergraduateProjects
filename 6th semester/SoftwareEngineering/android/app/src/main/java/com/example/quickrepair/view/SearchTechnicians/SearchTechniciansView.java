package com.example.quickrepair.view.SearchTechnicians;

import java.util.List;

public interface SearchTechniciansView {
    /**
     *  Θέτει τον spinner των τύπων εργασίας ως ενεργοποιημένο
     * @param b enabled if true
     */
    public void setJobTypeSpinnerEnabled(boolean b);
    /**
     *  Δείχνει ένα μήνυμα λάθους
     * @param errrorMessage the error message to show
     */
    public void showErrorMessage(String errrorMessage);
    /**
     *  Ορίζει τις ειδικότητες που μπορουν να εμφανιστούν στον spinner
     * @param specialtyIds  list of specialty ids
     * @param specialtyNames  list of specialty names
     */
    public void setSpecialtiesSource(List<Integer> specialtyIds , List<String> specialtyNames);
    /**
     *  Ορίζει τους τύπους εργασίας που μπορούν να εμφανιστούν στον spinner
     * @param jobTypeIds  list of job type ids
     * @param jobTypeIds  list of job type names
     */
    public void setJobTypesSource(List<Integer> jobTypeIds , List<String> jobTypeNames);
    /**
     *  Ορίζει την λίστα απο περιοχές απο την οποία μπορεί να διαλέξει ο χρήστης
     * @param areas list of areas
     */
    public void setAreasSource(List<String> areas);

    /**
     *  Γεμίζει την λίστα με τεχνικούς
     *  @param technicianIds  list of technician ids
     *  @param technicianNames  list of technician names
     *  @param averageRatings list of technician ratings
     *  @param averageRatings list of technician prices
     */
    public void populateTechnicianList(List<Integer> technicianIds ,  List<String> technicianNames
                                       , List<Double> averageRatings , List<Double> prices);

    /**
     * Προχωράει στην περιπτωση χρησης δημιουργία ραντεβού
     * @param technicianId the technician id
     * @param jobTypeId the job type id
     * @param year the year
     * @param month the month
     * @param dayOfMonth the day of the month
     */
    public void navigateToRequestRepair(int technicianId , int jobTypeId , int year , int month , int dayOfMonth);

    /**
     * Πηγαίνει τον χρήστη στην οθόνη για login.
     * @param technicianId the technician id
     * @param jobTypeId the job type id
     * @param year the year
     * @param month the month
     * @param dayOfMonth the day of the month
     */
    public void navigateToLogin(int technicianId, int jobTypeId, int year, int month, int dayOfMonth);

}
