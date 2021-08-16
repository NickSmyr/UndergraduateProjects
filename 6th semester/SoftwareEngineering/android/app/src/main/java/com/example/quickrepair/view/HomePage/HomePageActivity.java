package com.example.quickrepair.view.HomePage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.quickrepair.R;
import com.example.quickrepair.memorydao.MemoryInitializer;
import com.example.quickrepair.view.Customer.RegisterCustomer.CustomerRegisterActivity;
import com.example.quickrepair.view.SearchTechnicians.SearchTechniciansActivity;
import com.example.quickrepair.view.Technician.RegisterTechnician.TechnicianRegisterActivity;
import com.example.quickrepair.view.User.LoginUser.LoginActivity;

import static com.example.quickrepair.QuickRepairApplication.INITIALIZED_EXTRA;

public class HomePageActivity extends AppCompatActivity implements HomePageView
{

    private HomePageViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        boolean initialized = getIntent().getBooleanExtra(INITIALIZED_EXTRA, false);

        if (!initialized)
        {
            new MemoryInitializer().prepareData();
        }

        viewModel = new ViewModelProvider(this).get(HomePageViewModel.class);
        final HomePagePresenter presenter = viewModel.getPresenter();
        presenter.setView(this);

        //Buttons
        Button search = findViewById(R.id.search);
        Button logInPage = findViewById(R.id.login);
        Button registerTechnicianPage = findViewById(R.id.registerTechnician);
        Button registerCustomerPage = findViewById(R.id.registerCustomer);

        //Button's listeners
        search.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                presenter.onSearchSelected();
            }
        });

        logInPage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                presenter.onLogInSelected();
            }
        });

        registerTechnicianPage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                presenter.onRegisterAsTechnicianSelected();
            }
        });

        registerCustomerPage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                presenter.onRegisterAsCustomerSelected();
            }
        });

    }

    /**
     * Όταν πραγματοποιείται click στο κουμπι για login , ο χρηστης μεταφερεται στο κατάλληλο
     * activity
     */
    @Override
    public void login()
    {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

        finish();
    }

    /**
     * Όταν πραγματοποιείται click στο κουμπι για signup , ο χρηστης μεταφερεται στο κατάλληλο
     * activity
     */
    @Override
    public void registerAsTechnician()
    {
        Intent intent = new Intent(this, TechnicianRegisterActivity.class);
        startActivity(intent);

        finish();
    }

    /**
     * Όταν πραγματοποιείται click στο κουμπι για signup , ο χρηστης μεταφερεται στο κατάλληλο
     * activity
     */
    @Override
    public void registerAsCustomer()
    {
        Intent intent = new Intent(this, CustomerRegisterActivity.class);
        startActivity(intent);

        finish();
    }

    /**
     * Όταν πραγματοποιείται click στο κουμπι για αναζήτηση τεχνικού μεταφερόμαστε στο κατάλληλο
     * activity
     */
    @Override
    public void searchTechnicians()
    {
        Intent intent = new Intent(this, SearchTechniciansActivity.class);
        startActivity(intent);

        finish();
    }

}
