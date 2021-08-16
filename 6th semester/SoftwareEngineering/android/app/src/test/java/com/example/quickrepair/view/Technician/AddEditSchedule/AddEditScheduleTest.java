package com.example.quickrepair.view.Technician.AddEditSchedule;

import com.example.quickrepair.dao.Initializer;
import com.example.quickrepair.memorydao.MemoryInitializer;
import com.example.quickrepair.memorydao.TechnicianDAOMemory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AddEditScheduleTest
{
    private AddEditSchedulePresenter presenter;
    private AddEditScheduleViewStub view;

    @Before
    public void setup()
    {
        Initializer initializer = new MemoryInitializer();
        initializer.prepareData();

        view = new AddEditScheduleViewStub();
        presenter = new AddEditSchedulePresenter();
        presenter.setTechnicianDAO(new TechnicianDAOMemory());
        presenter.setView(view);
    }

    /**
     * Submit a correct schedule.
     */
    @Test
    public void submitTest()
    {
        presenter.setTechnician(1);
        presenter.setUpDataSource();

        Integer[][] schedule = new Integer[7][2];

        for (int i = 0; i < 7; ++i)
        {
            for (int j = 0; j < 2; ++j)
            {
                schedule[i][j] = i + j + 1;
            }
        }

        presenter.submit(schedule);

        Assert.assertNull(view.getErrorTitle());
        Assert.assertNull(view.getErrorMessage());
    }

    /**
     * Try submitting invalid or incomplete schedule.
     */
    @Test
    public void submitFailTest()
    {
        presenter.setTechnician(1);
        presenter.setUpDataSource();

        Integer[][] schedule = new Integer[7][2];

        for (int i = 0; i < 7; ++i)
        {
            for (int j = 0; j < 2; ++j)
            {
                schedule[i][j] = i - j;
            }
        }

        presenter.submit(schedule);

        Assert.assertEquals("Invalid value", view.getErrorTitle());
        Assert.assertEquals("Starting hour can not be after ending hour.", view.getErrorMessage());

        presenter.submit(new Integer[6][2]);
        Assert.assertEquals("Invalid value", view.getErrorTitle());
        Assert.assertEquals("The schedule must have 7 entries", view.getErrorMessage());

    }
}
