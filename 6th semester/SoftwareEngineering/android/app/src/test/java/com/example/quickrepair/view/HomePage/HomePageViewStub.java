package com.example.quickrepair.view.HomePage;

public class HomePageViewStub implements HomePageView {

    //Buttons
    private static final int logIn = 1;
    private static final int registerAsTechnician = 2;
    private static final int registerAsCustomer = 3;
    private static final int searchTechnician = 4;

    private int clickedButton;

    public int getClickedButton() {
        return clickedButton;
    }

    public void setClickedButton(int clickedButton) {
        this.clickedButton = clickedButton;
    }

    /**
     * Όταν πραγματοποιείται click στο κουμπι για login , ο χρηστης μεταφερεται στο κατάλληλο
     * activity
     */
    @Override
    public void login() {
        setClickedButton(logIn);
    }

    /**
     * Όταν πραγματοποιείται click στο κουμπι για signup , ο χρηστης μεταφερεται στο κατάλληλο
     * activity
     */
    @Override
    public void registerAsTechnician() {
        setClickedButton(registerAsTechnician);
    }

    /**
     * Όταν πραγματοποιείται click στο κουμπι για signup , ο χρηστης μεταφερεται στο κατάλληλο
     * activity
     */
    @Override
    public void registerAsCustomer() {
        setClickedButton(registerAsCustomer);
    }

    /**
     * Όταν πραγματοποιείται click στο κουμπι για αναζήτηση τεχνικού μεταφερόμαστε στο κατάλληλο
     * activity
     */
    @Override
    public void searchTechnicians() {
        setClickedButton(searchTechnician);
    }
}
