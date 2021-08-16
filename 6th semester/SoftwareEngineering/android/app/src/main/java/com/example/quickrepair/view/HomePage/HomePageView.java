package com.example.quickrepair.view.HomePage;

public interface HomePageView
{
    /**
     * Όταν πραγματοποιείται click στο κουμπι για login , ο χρηστης μεταφερεται στο κατάλληλο
     * activity
     */
    void login();

    /**
     * Όταν πραγματοποιείται click στο κουμπι για signup , ο χρηστης μεταφερεται στο κατάλληλο
     * activity
     */
    void registerAsTechnician();

    /**
     * Όταν πραγματοποιείται click στο κουμπι για signup , ο χρηστης μεταφερεται στο κατάλληλο
     * activity
     */
    void registerAsCustomer();

    /**
     * Όταν πραγματοποιείται click στο κουμπι για αναζήτηση τεχνικού μεταφερόμαστε στο κατάλληλο
     * activity
     */
    void searchTechnicians();


}
