package com.example.quickrepair.view.SearchTechnicians;

import com.example.quickrepair.dao.Initializer;
import com.example.quickrepair.memorydao.MemoryInitializer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class SearchTechnicianTest
{
    SearchTechnicianViewStub view;
    SearchTechniciansPresenter presenter;

    /**
     * Sets up the tests by preparing an initialized presenter
     */
    @Before
    public void setup()
    {
        Initializer initializer = new MemoryInitializer();
        initializer.prepareData();

        view = new SearchTechnicianViewStub();
        presenter = new SearchTechniciansPresenter(initializer.getTechnicianDAO(), initializer.getSpecialtyDAO(), initializer.getJobTypeDAO(), initializer.getAreaDAO());
        presenter.setView(view);
    }

    /**
     * Tests that when a specialty is selected the correct job types are showed
     */
    @Test
    public void setSpecialityTest()
    {
        presenter.onStart();
        presenter.selectSpecialty(1);

        Assert.assertEquals(new ArrayList<>(Arrays.asList(1, 2, 3, 4)), view.getJobTypeIds());
    }
    /**
     * Tests a correct execution of search
     */
    @Test
    public void searchTest()
    {
        presenter.setLoggedInUser(0);
        presenter.onStart();

        presenter.search(1, "", "Argos", "1", "1", "1");
        Assert.assertEquals(new ArrayList<>(Arrays.asList(1, 2)), view.getTechnicianIds());
    }
    /**
     * Tests that the price filter is working and only one technician is chosen
     */
    @Test
    public void searchLessPriceTest()
    {
        presenter.setLoggedInUser(0);
        presenter.onStart();

        presenter.search(1, "13", "Argos", "1", "1", "1");
        Assert.assertEquals(new ArrayList<>(Arrays.asList(1)), view.getTechnicianIds());
    }
    /**
     * Tests that when setting a low price that no technician offers no technician is output
     */
    @Test
    public void searchEmptyTest()
    {
        presenter.setLoggedInUser(0);
        presenter.onStart();

        presenter.search(1, "1", "Argos", "1", "1", "1");
        Assert.assertEquals(new ArrayList<>(), view.getTechnicianIds());
    }

    /**
     * Tests the case the user enters an invalid year
     */
    @Test
    public void searchFailTest()
    {
        presenter.setLoggedInUser(0);
        presenter.onStart();

        presenter.search(1, "1", "Argos", "", "1", "1");
        Assert.assertEquals("Please enter a valid Date (YYYY/MM/DD)", view.getError());
    }
    /**
     * Tests the case the user enters an invalid day
     */
    @Test
    public void searchFailInvalidDateTest()
    {
        presenter.setLoggedInUser(0);
        presenter.onStart();

        presenter.search(1, "1", "Argos", "1", "1", "40");
        Assert.assertEquals("Please enter a valid Date (YYYY/MM/DD)", view.getError());
    }
    /**
     * Tests the case the user enters an invalid price
     */
    @Test
    public void searchFailPriceTest()
    {
        presenter.setLoggedInUser(0);
        presenter.onStart();

        presenter.search(1, "a", "Argos", "1", "1", "1");
        Assert.assertEquals("Please enter a valid from of price", view.getError());
    }
    /**
     * Tests that when the customer is already logged in , the customer is not redirected to login
     */
    @Test
    public void selectLogin()
    {
        presenter.setLoggedInUser(1);
        presenter.onStart();

        presenter.search(1, "", "Argos", "1", "1", "1");
        presenter.onTechnicianClick(1);
        Assert.assertEquals(1, view.getTechnicianId());
        Assert.assertEquals(1, view.getJobTypeId());
        Assert.assertEquals(1, view.getYear());
        Assert.assertEquals(1, view.getMonth());
        Assert.assertEquals(1, view.getDayOfMonth());
        Assert.assertEquals(false, view.isToLogin());
    }
    /**
     * Tests that when the customer is not logged in , the customer is  redirected to login
     */
    @Test
    public void selectNoLogin()
    {
        presenter.setLoggedInUser(0);
        presenter.onStart();

        presenter.search(1, "", "Argos", "1", "1", "1");
        presenter.onTechnicianClick(1);
        Assert.assertEquals(1, view.getTechnicianId());
        Assert.assertEquals(1, view.getJobTypeId());
        Assert.assertEquals(1, view.getYear());
        Assert.assertEquals(1, view.getMonth());
        Assert.assertEquals(1, view.getDayOfMonth());
        Assert.assertEquals(true, view.isToLogin());
    }

    /**
     * Tests the case that an invalid year was given
     */
    @Test
    public void selectNoDateLogin()
    {
        presenter.setLoggedInUser(0);
        presenter.onStart();

        presenter.search(1, "", "Argos", "", "1", "1");
        presenter.onTechnicianClick(1);

        Assert.assertEquals("Please enter a valid Date (YYYY/MM/DD)", view.getError());
    }
}
