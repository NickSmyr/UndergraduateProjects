package com.example.quickrepair.view.HomePage;

import com.example.quickrepair.view.HomePage.HomePagePresenter;
import com.example.quickrepair.view.HomePage.HomePageView;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HomePagePresenterTest {
    HomePageViewStub view;
    HomePagePresenter presenter;

    /**
     * Αρχικοποίηση
     */
    @Before
    public void setUp(){
        view = new HomePageViewStub();
        presenter = new HomePagePresenter();
        presenter.setView(view);
    }

    /**
     * Έλεγχος ότι αφού επιλεγχθεί Log in προβάλετε το κατάλληλο αποτέλεσμα
     */
    @Test
    public void testLogIn(){
        view.login();
        Assert.assertEquals(1, view.getClickedButton());
    }

    /**
     * Έλεγχος ότι αφού επιλεγχθεί Register ως τεχνικός προβάλετε το κατάλληλο αποτέλεσμα
     */
    @Test
    public void testRegisterTechnician(){
        view.registerAsTechnician();
        Assert.assertEquals(2, view.getClickedButton());
    }

    /**
     * Έλεγχος ότι αφού επιλεγχθεί Register ως πελάτης προβάλετε το κατάλληλο αποτέλεσμα
     */
    @Test
    public void testRegisterCustomer(){
        view.registerAsCustomer();
        Assert.assertEquals(3, view.getClickedButton());
    }

    /**
     * Έλεγχος ότι αφού επιλεγχθεί Αναζήτηση προβάλετε το κατάλληλο αποτέλεσμα
     */
    @Test
    public void testSearch(){
        view.searchTechnicians();
        Assert.assertEquals(4, view.getClickedButton());
    }
}
