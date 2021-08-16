package com.example.quickrepair.view.Customer.ShowCompletedRepairRequest;

public interface CustomerCompletedRepairRequestView {
    /**
     * Προβάλει μήνυμα λάθους
     * @param message Το Μήνυμα λάθους
     */
    void showError(String message);

    /**
     * Εμφανίζει τη δουλειά
     * @param job Η δουλειά
     */
    void setJob(String job);

    /**
     * Εμφανίζει το όνομα του τεχνικού
     * @param technicianName Το όνομα του τεχνηκού
     */
    void setTechnicianName(String technicianName);

    /**
     * Εμφανίζει τη διεύθυνση
     * @param address Η διεύθυνση
     */
    void setAddress(String address);

    /**
     * Εμφανίζει τα σχόλια
     * @param comments Τα σχόλια
     */
    void setComments(String comments);

    /**
     * Εμφανίζει την ημερομηνία διεξαγωγής της επισκευής
     * @param conductionDate Η ημερομηνία διεξαγωγής  της επισκευής
     */
    void setConductionDate(String conductionDate);

    /**
     * Εμφανίζει το εκτιμώμενο απο τον τεχνικό χρόνο της επισκευής
     * @param estimatedDuration Ο εκτιμώμενος απο τον τεχνικό χρόνος της επισκευής
     */
    void setEstimatedDuration(String estimatedDuration);

    /**
     * Εμφανίζει το κόστος
     * @param cost Το κόστος
     */
    void setCost(String cost);

    /**
     * Εμφανίζει τα στοιχεία της αξιολόγησης
     * @param title Ο τίτλος της αξιολόγησης
     * @param comments Τα σχόλια της αξιολόγησης
     * @param rate Η βαθμολογία της αξιολόγησης
     */
    void setEvaluationData(String title, String comments, String rate);

    /**
     * Εμφανίζει τα πεδία για πληρωμή και προσθήκη σχολίων
     */
    void setPayAndEvaluationFields();

    /**
     * Ενεργοποίηση δυνατότητας πληρωμής
     */
    void setPayListener();

    /**
     * Αίτημα για πληρωμή και αξιολόγηση
     */
    void donePayAndEvaluate();
}
