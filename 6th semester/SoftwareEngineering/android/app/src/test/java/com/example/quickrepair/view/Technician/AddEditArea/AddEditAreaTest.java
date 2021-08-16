package com.example.quickrepair.view.Technician.AddEditArea;

import com.example.quickrepair.dao.Initializer;
import com.example.quickrepair.memorydao.AreaDAOMemory;
import com.example.quickrepair.memorydao.MemoryInitializer;
import com.example.quickrepair.memorydao.TechnicianDAOMemory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AddEditAreaTest
{
    private AddEditAreaPresenter presenter;
    private AddEditAreaViewStub view;

    @Before
    public void setup()
    {
        Initializer initializer = new MemoryInitializer();
        initializer.prepareData();

        view = new AddEditAreaViewStub();
        presenter = new AddEditAreaPresenter();
        presenter.setAreaDAO(new AreaDAOMemory());
        presenter.setTechnicianDAO(new TechnicianDAOMemory());
        presenter.setView(view);
    }

    /**
     * Add area successfully.
     */
    @Test
    public void addAreaTest()
    {
        presenter.setTechnician(1);
        presenter.setUpDataSource();

        for (int i = 1; i < view.getAreaList().size(); ++i)
        {
            boolean found = false;

            for (String area : view.getSelectedArea())
            {
                if (area.equals(view.getAreaList().get(i)))
                {
                    found = true;
                    break;
                }
            }

            if (!found)
            {
                presenter.addArea(i);
                Assert.assertEquals(5, view.getSelectedArea().size());
                return;
            }
        }
    }

    /**
     * Try adding an area already existing or not selected.
     */
    @Test
    public void AddAreaFailedTest()
    {
        presenter.setTechnician(1);
        presenter.setUpDataSource();

        presenter.addArea(0);
        Assert.assertEquals("No area selected", view.getErrorTitle());
        Assert.assertEquals("You have to add a valid area.", view.getErrorMessage());
        Assert.assertEquals(4, view.getSelectedArea().size());

        for (int i = 1; i < view.getAreaList().size(); ++i)
        {
            for (String area : view.getSelectedArea())
            {
                if (area.equals(view.getAreaList().get(i)))
                {
                    presenter.addArea(i);
                    Assert.assertEquals("Area already exist", view.getErrorTitle());
                    Assert.assertEquals("This area is already selected.", view.getErrorMessage());
                    Assert.assertEquals(4, view.getSelectedArea().size());
                    return;
                }
            }
        }
    }

    /**
     * Remove an area.
     */
    @Test
    public void removeAreaTest()
    {
        presenter.setTechnician(1);
        presenter.setUpDataSource();

        presenter.removeArea(2);
        Assert.assertEquals(3, view.getSelectedArea().size());
    }
}
