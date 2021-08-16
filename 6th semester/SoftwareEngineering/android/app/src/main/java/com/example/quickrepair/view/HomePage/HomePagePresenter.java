package com.example.quickrepair.view.HomePage;

public class HomePagePresenter {
    private HomePageView view;

    public HomePagePresenter(){}

    /**
     * set View
     * @param view The view
     */
    public void setView(HomePageView view) {
        this.view = view;
    }

    /**
     * Responds to the user selecting search for a technician
     */
    public void onSearchSelected(){
        view.searchTechnicians();
    }

    /**
     * Responds to the user selecting Log In
     */
    public void onLogInSelected(){
        view.login();
    }

    /**
     * Responds to the user selecting register as technician
     */
    public void onRegisterAsTechnicianSelected(){
        view.registerAsTechnician();
    }

    /**
     * Responds to the user selecting register as customer
     */
    public void onRegisterAsCustomerSelected(){
        view.registerAsCustomer();
    }

    /**
     * clear view
     */
    public void clearView(){
        this.view = null;
    }

}
