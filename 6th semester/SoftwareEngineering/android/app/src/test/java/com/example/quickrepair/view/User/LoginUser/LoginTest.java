package com.example.quickrepair.view.User.LoginUser;

import com.example.quickrepair.dao.Initializer;
import com.example.quickrepair.memorydao.CustomerDAOMemory;
import com.example.quickrepair.memorydao.MemoryInitializer;
import com.example.quickrepair.memorydao.TechnicianDAOMemory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LoginTest
{
    LoginPresenter presenter;
    LoginViewStub view;

    @Before
    public void setup()
    {
        Initializer initializer = new MemoryInitializer();
        initializer.prepareData();

        view = new LoginViewStub();
        presenter = new LoginPresenter();
        presenter.setTechnicianDAO(new TechnicianDAOMemory());
        presenter.setCustomerDAO(new CustomerDAOMemory());
        presenter.setView(view);
    }

    /**
     * Try successful login.
     */
    @Test
    public void loginTest()
    {
        presenter.login("aggelidis", "123");
        Assert.assertEquals(1, view.getTechnicianID());
        Assert.assertEquals(0, view.getCustomerID());

        presenter.login("ann", "123");
        Assert.assertEquals(1, view.getCustomerID());
    }

    /**
     * Try failed login.
     */
    @Test
    public void loginFailTest()
    {
        presenter.login("", "");
        Assert.assertEquals(1, view.getErrorCount());

        presenter.login("aggelidis", "111");
        Assert.assertEquals(2, view.getErrorCount());

        presenter.login("ann", "111");
        Assert.assertEquals(3, view.getErrorCount());
    }
}
