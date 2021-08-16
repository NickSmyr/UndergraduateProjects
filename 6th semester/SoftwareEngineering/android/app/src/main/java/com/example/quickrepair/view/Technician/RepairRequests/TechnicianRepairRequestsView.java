package com.example.quickrepair.view.Technician.RepairRequests;

import com.example.quickrepair.domain.RepairRequest;

import java.util.ArrayList;

public interface TechnicianRepairRequestsView
{
    /**
     * Προβάλη στον τεχνικό το Μη επιβεβαιωμένο Αίτημα Κατασκευής με το δοσμένο αναγνωριστικό
     * @param repairRequestUid Το Αίτημα Κατασκευής
     */
    void returnRepairRequestUnconfirmed(int repairRequestUid);

    /**
     * Προβάλη στον τεχνικό το Επιβεβαιωμένο Αίτημα Κατασκευής με το δοσμένο αναγνωριστικό
     * @param repairRequestUid Το Αίτημα Κατασκευής
     */
    void returnRepairRequestConfirmed(int repairRequestUid);

    /**
     * Προβάλη στον τεχνικό το Ολοκληρωμένο Αίτημα Κατασκευής με το δοσμένο αναγνωριστικό
     * @param repairRequestUid Το Αίτημα Κατασκευής
     */
    void returnRepairRequestCompleted(int repairRequestUid);

    /**
     * Προβάλει στο πελάτη τα στοιχεία του για επεξεργασία
     */
    void editData();

    /**
     *  Προβάλει μήνυμα λάθους
     * @param message Το Μήνυμα λάθους
     */
    void showError(String message);
}
