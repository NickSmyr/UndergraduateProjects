package com.example.quickrepair.view.Customer.RepairRequests;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.example.quickrepair.R;
import com.example.quickrepair.view.Customer.RegisterCustomer.CustomerRegisterActivity;
import com.example.quickrepair.view.Customer.ShowCompletedRepairRequest.CustomerCompletedRepairRequestActivity;
import com.example.quickrepair.view.Customer.ShowConfirmedRepairRequest.CustomerConfirmedRepairRequestActivity;
import com.example.quickrepair.view.Customer.ShowUnconfirmedRepairRequest.CustomerUnconfirmedRepairRequestActivity;
import com.example.quickrepair.view.HomePage.HomePageActivity;
import com.example.quickrepair.view.SearchTechnicians.SearchTechniciansActivity;
import com.google.android.material.tabs.TabLayout;

import static com.example.quickrepair.QuickRepairApplication.CUSTOMER_ID_EXTRA;
import static com.example.quickrepair.QuickRepairApplication.INITIALIZED_EXTRA;
import static com.example.quickrepair.QuickRepairApplication.REPAIR_REQUEST_ID_EXTRA;


public class CustomerRepairRequestsActivity extends AppCompatActivity implements CustomerRepairRequestsView
{
    int customerID;
    CustomerRepairRequestsViewModel customerRepairRequestsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_repair_requests);

        customerID = getIntent().getIntExtra(CUSTOMER_ID_EXTRA, 0);

        customerRepairRequestsViewModel = new ViewModelProvider(this).get(CustomerRepairRequestsViewModel.class);
        customerRepairRequestsViewModel.getPresenter().setView(this);

        ViewRepairRequestsPagerAdapter sectionsPagerAdapter = new ViewRepairRequestsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.customer_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        int id = menuItem.getItemId();
        switch (id)
        {
            case R.id.edit:
                customerRepairRequestsViewModel.getPresenter().onEditDataPage();
                return true;
            case R.id.search:
                customerRepairRequestsViewModel.getPresenter().searchForJob();
                return true;
            case R.id.logout:
                logout();
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void returnRepairRequestUnconfirmed(int repairRequestUid)
    {
        Intent intent = new Intent(this, CustomerUnconfirmedRepairRequestActivity.class);
        intent.putExtra(REPAIR_REQUEST_ID_EXTRA, repairRequestUid);
        intent.putExtra(CUSTOMER_ID_EXTRA, customerID);
        startActivity(intent);

        finish();
    }

    @Override
    public void returnRepairRequestConfirmed(int repairRequestUid)
    {
        Intent intent = new Intent(this, CustomerConfirmedRepairRequestActivity.class);
        intent.putExtra(REPAIR_REQUEST_ID_EXTRA, repairRequestUid);
        intent.putExtra(CUSTOMER_ID_EXTRA, customerID);
        startActivity(intent);

        finish();
    }

    @Override
    public void returnRepairRequestCompleted(int repairRequestUid)
    {
        Intent intent = new Intent(this, CustomerCompletedRepairRequestActivity.class);
        intent.putExtra(REPAIR_REQUEST_ID_EXTRA, repairRequestUid);
        intent.putExtra(CUSTOMER_ID_EXTRA, customerID);
        startActivity(intent);

        finish();
    }

    @Override
    public void editData()
    {
        Intent intent = new Intent(this, CustomerRegisterActivity.class);
        intent.putExtra(CUSTOMER_ID_EXTRA, customerID);
        startActivity(intent);

        finish();
    }

    @Override
    public void search()
    {
        Intent intent = new Intent(this, SearchTechniciansActivity.class);
        intent.putExtra(CUSTOMER_ID_EXTRA, customerID);
        startActivity(intent);

        finish();
    }

    @Override
    public void showError(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public CustomerRepairRequestsViewModel getViewModel()
    {
        return customerRepairRequestsViewModel;
    }

    public int getCustomerID()
    {
        return customerID;
    }

    private void logout()
    {
        Intent intent = new Intent(this, HomePageActivity.class);
        intent.putExtra(INITIALIZED_EXTRA, true);
        startActivity(intent);

        finish();
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        finishAffinity();
    }
}
