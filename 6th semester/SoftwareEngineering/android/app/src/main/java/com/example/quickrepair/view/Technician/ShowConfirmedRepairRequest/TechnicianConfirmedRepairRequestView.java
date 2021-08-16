package com.example.quickrepair.view.Technician.ShowConfirmedRepairRequest;

public interface TechnicianConfirmedRepairRequestView {
    /**
     * Προβάλει μήνυμα λάθους
     * @param message Το Μήνυμα λάθους
     */
    void showError(String message);

    /**
     * Αίτημα για ολοκλήρωση της επισκευής
     */
    void complete();

    /**
     * Εμφανίζει τη δουλειά
     * @param job Η δουλειά
     */
    void setJob(String job);

    /**
     * Εμφανίζει το όνομα του πελάτη
     * @param consumerName Το όνομα του πελάτη
     */
    void setConsumerName(String consumerName);

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
     * Ενεργοποίηση κουμπιού ολοκλήρωσης
     */
    void setButtonListeners();

}
