package com.example.quickrepair.view.Technician.AddEditJob;

import com.example.quickrepair.dao.Initializer;
import com.example.quickrepair.domain.Job;
import com.example.quickrepair.memorydao.MemoryInitializer;
import com.example.quickrepair.memorydao.TechnicianDAOMemory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AddEditJobTest
{
    private AddEditJobPresenter presenter;
    private AddEditJobViewStub view;

    @Before
    public void setup()
    {
        Initializer initializer = new MemoryInitializer();
        initializer.prepareData();

        view = new AddEditJobViewStub();
        presenter = new AddEditJobPresenter();
        presenter.setTechnicianDAO(new TechnicianDAOMemory());
        presenter.setView(view);
    }

    /**
     * Add a job successfully.
     */
    @Test
    public void addJobTest()
    {
        presenter.setTechnician(1);
        presenter.setUpDataSource();

        for (int i = 1; i < view.getJobs().size(); ++i)
        {
            boolean found = false;

            for (Job job : view.getSelectedJobs())
            {
                if (job.getJobType().getName().equals(view.getJobs().get(i)))
                {
                    found = true;
                    break;
                }
            }

            if (!found)
            {
                presenter.addJob(i, "100");
                Assert.assertEquals(4, view.getSelectedJobs().size());
                return;
            }
        }
    }

    /**
     * Try adding a job with invalid price or already existing.
     */
    @Test
    public void addJobFailedTest()
    {
        presenter.setTechnician(1);
        presenter.setUpDataSource();

        presenter.addJob(0, "100");
        Assert.assertEquals("No job selected", view.getErrorTitle());
        Assert.assertEquals("You have to add a valid job.", view.getErrorMessage());
        Assert.assertEquals(3, view.getSelectedJobs().size());

        presenter.addJob(1, "a");
        Assert.assertEquals("Invalid price value", view.getErrorTitle());
        Assert.assertEquals("Please enter a valid price value", view.getErrorMessage());
        Assert.assertEquals(3, view.getSelectedJobs().size());

        presenter.addJob(1, "0");
        Assert.assertEquals("Invalid price value", view.getErrorTitle());
        Assert.assertEquals("Price can not be zero or negative.", view.getErrorMessage());
        Assert.assertEquals(3, view.getSelectedJobs().size());

        for (int i = 1; i < view.getJobs().size(); ++i)
        {
            for (Job job : view.getSelectedJobs())
            {
                if (job.getJobType().getName().equals(view.getJobs().get(i)))
                {
                    presenter.addJob(i, "10");
                    Assert.assertEquals("Invalid value.", view.getErrorTitle());
                    Assert.assertEquals(3, view.getSelectedJobs().size());
                    return;
                }
            }
        }
    }

    /**
     * Remove a job.
     */
    @Test
    public void removeJob()
    {
        presenter.setTechnician(1);
        presenter.setUpDataSource();

        presenter.removeJob(2);
        Assert.assertEquals(2, view.getSelectedJobs().size());
    }
}
