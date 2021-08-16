package com.example.quickrepair.view.Customer.RepairRequests;

public interface CustomerRepairRequestsView {

    /**
     * Προβάλη στον πελάτη το Μη επιβεβαιωμένο Αίτημα Κατασκευής με το δοσμένο αναγνωριστικό
     * @param repairRequestUid Το Αίτημα Κατασκευής
     */
    void returnRepairRequestUnconfirmed(int repairRequestUid);

    /**
     * Προβάλη στον πελάτη το Επιβεβαιωμένο Αίτημα Κατασκευής με το δοσμένο αναγνωριστικό
     * @param repairRequestUid Το Αίτημα Κατασκευής
     */
    void returnRepairRequestConfirmed(int repairRequestUid);

    /**
     * Προβάλη στον πελάτη το Ολοκληρωμένο Αίτημα Κατασκευής με το δοσμένο αναγνωριστικό
     * @param repairRequestUid Το Αίτημα Κατασκευής
     */
    void returnRepairRequestCompleted(int repairRequestUid);

    /**
     * Προβάλει στο πελάτη τα στοιχεία του για επεξεργασία
     */
    void editData();

    /**
     * Προβάλει στο πελάτη το μενού για να αναζητήσει
     */
    void search();

    /**
     *  Προβάλει μήνυμα λάθους
     * @param message Το Μήνυμα λάθους
     */
    void showError(String message);
}
