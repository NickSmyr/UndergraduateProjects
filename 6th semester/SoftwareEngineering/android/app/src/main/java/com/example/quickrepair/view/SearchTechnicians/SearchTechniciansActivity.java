package com.example.quickrepair.view.SearchTechnicians;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.quickrepair.QuickRepairApplication;
import com.example.quickrepair.R;
import com.example.quickrepair.view.Customer.RegisterCustomer.CustomerRegisterActivity;
import com.example.quickrepair.view.Customer.RepairRequests.CustomerRepairRequestsActivity;
import com.example.quickrepair.view.HomePage.HomePageActivity;
import com.example.quickrepair.view.RequestRepair.RequestRepairActivity;
import com.example.quickrepair.view.User.LoginUser.LoginActivity;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static com.example.quickrepair.QuickRepairApplication.CUSTOMER_ID_EXTRA;
import static com.example.quickrepair.QuickRepairApplication.DAY_EXTRA;
import static com.example.quickrepair.QuickRepairApplication.JOBTYPE_ID_EXTRA;
import static com.example.quickrepair.QuickRepairApplication.MONTH_EXTRA;
import static com.example.quickrepair.QuickRepairApplication.REDIRECT_TO_SEARCH_EXTRA;
import static com.example.quickrepair.QuickRepairApplication.REQUEST_CODE_LOGIN;
import static com.example.quickrepair.QuickRepairApplication.RESULT_DENIED;
import static com.example.quickrepair.QuickRepairApplication.RESULT_INVALID;
import static com.example.quickrepair.QuickRepairApplication.TECHNICIAN_ID_EXTRA;
import static com.example.quickrepair.QuickRepairApplication.YEAR_EXTRA;

public class SearchTechniciansActivity extends AppCompatActivity implements SearchTechniciansView
{

    SearchTechniciansViewModel viewModel;
    SearchTechniciansPresenter presenter;

    Spinner specialtySpinner;
    Spinner jobTypeSpinner;
    Spinner areaSpinner;
    EditText maxpriceText;

    ArrayAdapter<SpinnerEntry> specialtyAdapter;
    ArrayAdapter<SpinnerEntry> jobTypeAdapter;
    ArrayAdapter<String> areaAdapter;

    EditText yearText;
    EditText monthText;
    EditText dayText;

    TechnicianAdapter technicianAdapter;
    ListView techniciansList;

    int loggedInUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_technicians);
        Intent intent = getIntent();
        loggedInUserId = intent.getIntExtra(CUSTOMER_ID_EXTRA, 0);


        viewModel = new ViewModelProvider(this).get(SearchTechniciansViewModel.class);
        presenter = viewModel.getPresenter();
        presenter.setView(this);

        presenter.setLoggedInUser(loggedInUserId);


        //Setting spinner adapters
        specialtySpinner = findViewById(R.id.specialty_spinner);
        jobTypeSpinner = findViewById(R.id.job_type_spinner);
        areaSpinner = findViewById(R.id.area_spinner);

        specialtyAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item);
        jobTypeAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item);
        areaAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item);

        maxpriceText = findViewById(R.id.max_price_text);
        yearText = findViewById(R.id.year);
        monthText = findViewById(R.id.month);
        dayText = findViewById(R.id.day);

        specialtySpinner.setAdapter(specialtyAdapter);
        jobTypeSpinner.setAdapter(jobTypeAdapter);
        areaSpinner.setAdapter(areaAdapter);

        specialtySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                SpinnerEntry entry = (SpinnerEntry) parent.getAdapter().getItem(position);
                presenter.selectSpecialty(entry.getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
        //Setting technicianListAdapters
        techniciansList = findViewById(R.id.technician_list);
        technicianAdapter = new TechnicianAdapter(this, R.layout.search_technicians_technician_list_layout);
        techniciansList.setAdapter(technicianAdapter);

        Button b = findViewById(R.id.search_button);
        b.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                SpinnerEntry entry = (SpinnerEntry) jobTypeSpinner.getSelectedItem();
                int selectedJobTypeId = entry.getId();
                String selectedMaxPrice = maxpriceText.getText().toString();
                String selectedArea = (String) areaSpinner.getSelectedItem();

                String month = monthText.getText().toString();
                String year = yearText.getText().toString();
                String day = dayText.getText().toString();

                presenter.search(selectedJobTypeId, selectedMaxPrice, selectedArea, year, month, day);
            }
        });

        techniciansList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                presenter.onTechnicianClick((int) id);
            }
        });

        presenter.onStart();
    }

    @Override
    public void setJobTypeSpinnerEnabled(boolean b)
    {
        findViewById(R.id.job_type_spinner).setEnabled(b);
    }

    @Override
    public void showErrorMessage(String errorMessage)
    {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setSpecialtiesSource(List<Integer> specialtyIds, List<String> specialtyNames)
    {
        List<SpinnerEntry> list = new ArrayList<>();
        for (int i = 0; i < specialtyIds.size(); i++)
        {
            list.add(new SpinnerEntry(specialtyIds.get(i), specialtyNames.get(i)));
        }
        specialtyAdapter = new ArrayAdapter<>(this
                , R.layout.support_simple_spinner_dropdown_item, list);
        specialtySpinner.setAdapter(specialtyAdapter);

    }

    @Override
    public void setJobTypesSource(List<Integer> jobTypeIds, List<String> jobTypeNames)
    {
        List<SpinnerEntry> list = new ArrayList<>();
        for (int i = 0; i < jobTypeIds.size(); i++)
        {
            list.add(new SpinnerEntry(jobTypeIds.get(i), jobTypeNames.get(i)));
        }
        jobTypeAdapter = new ArrayAdapter<>(this
                , R.layout.support_simple_spinner_dropdown_item, list);
        jobTypeSpinner.setAdapter(jobTypeAdapter);
    }

    @Override
    public void setAreasSource(List<String> areas)
    {
        areaAdapter = new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item, areas);
        areaSpinner.setAdapter(areaAdapter);
    }

    @Override
    public void populateTechnicianList(List<Integer> technicianIds, List<String> technicianNames, List<Double> averageRatings, List<Double> prices)
    {
        TechnicianAdapter technicianAdapter = new TechnicianAdapter(this, 2);
        technicianAdapter.setSource(technicianIds, technicianNames, averageRatings, prices);
        techniciansList.setAdapter(technicianAdapter);
    }

    @Override
    public void navigateToRequestRepair(int technicianId, int jobTypeId, int year, int month, int dayOfMonth)
    {
        Intent intent = new Intent(this, RequestRepairActivity.class);
        intent.putExtra(TECHNICIAN_ID_EXTRA, technicianId);
        intent.putExtra(JOBTYPE_ID_EXTRA, jobTypeId);
        intent.putExtra(YEAR_EXTRA, year);
        intent.putExtra(MONTH_EXTRA, month);
        intent.putExtra(DAY_EXTRA, dayOfMonth);
        intent.putExtra(CUSTOMER_ID_EXTRA, loggedInUserId);
        startActivity(intent);

        finish();
    }

    /**
     * Starts the login activity , expects a result on onActivityResult
     */
    @Override
    public void navigateToLogin(int technicianId, int jobTypeId, int year, int month, int dayOfMonth)
    {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra(REDIRECT_TO_SEARCH_EXTRA, true);

        Intent repairIntent = new Intent(this, RequestRepairActivity.class);
        repairIntent.putExtra(TECHNICIAN_ID_EXTRA, technicianId);
        repairIntent.putExtra(JOBTYPE_ID_EXTRA, jobTypeId);
        repairIntent.putExtra(YEAR_EXTRA, year);
        repairIntent.putExtra(MONTH_EXTRA, month);
        repairIntent.putExtra(DAY_EXTRA, dayOfMonth);

        SharedPreferences settings = getSharedPreferences("PREFERENCES", 0);
        SharedPreferences.Editor editor = settings.edit();
        String uriString = repairIntent.toUri(0);

        editor.putString("Repair", uriString);
        editor.apply();

        startActivityForResult(intent, REQUEST_CODE_LOGIN);
    }

    /**
     * Called when we get a result from the login activity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_LOGIN)
        {
            if (resultCode == RESULT_OK)
            {
                loggedInUserId = data.getIntExtra(CUSTOMER_ID_EXTRA, 0);
                presenter.setLoggedInUser(loggedInUserId);

                SharedPreferences settings = getSharedPreferences("PREFERENCES", 0);
                String uri = settings.getString("Repair", null);

                Intent intent = null;
                try
                {
                    intent = Intent.parseUri(uri, 0);
                }
                catch (URISyntaxException e)
                {
                    e.printStackTrace();
                }

                intent.putExtra(CUSTOMER_ID_EXTRA, loggedInUserId);
                startActivity(intent);

                finish();
            }
            else if (resultCode == RESULT_INVALID)
            {
                showErrorMessage("Invalid customer credentials.");
            }
            else if (resultCode == RESULT_DENIED)
            {
                showErrorMessage("Only customer accounts have access to the requested page.");
            }
        }
    }

    @Override
    public void onBackPressed()
    {
        if (loggedInUserId == 0)
        {
            Intent intent = new Intent(this, HomePageActivity.class);
            startActivity(intent);
        }
        else
        {
            Intent intent = new Intent(this, CustomerRepairRequestsActivity.class);
            intent.putExtra(CUSTOMER_ID_EXTRA, loggedInUserId);
            startActivity(intent);
        }

        finish();
    }

    private class SpinnerEntry
    {
        int id;
        String value;

        public SpinnerEntry(int id, String value)
        {
            this.id = id;
            this.value = value;
        }

        @Override
        public String toString()
        {
            return value;
        }

        public int getId()
        {
            return id;
        }
    }
}
