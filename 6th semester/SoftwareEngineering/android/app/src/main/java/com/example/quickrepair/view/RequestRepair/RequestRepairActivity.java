package com.example.quickrepair.view.RequestRepair;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quickrepair.R;
import com.example.quickrepair.dao.RepairRequestDAO;
import com.example.quickrepair.domain.RepairRequest;
import com.example.quickrepair.memorydao.RepairRequestDAOMemory;
import com.example.quickrepair.view.Customer.RepairRequests.CustomerRepairRequestsActivity;

import java.util.List;

import static com.example.quickrepair.QuickRepairApplication.CUSTOMER_ID_EXTRA;
import static com.example.quickrepair.QuickRepairApplication.DAY_EXTRA;
import static com.example.quickrepair.QuickRepairApplication.JOBTYPE_ID_EXTRA;
import static com.example.quickrepair.QuickRepairApplication.MONTH_EXTRA;
import static com.example.quickrepair.QuickRepairApplication.TECHNICIAN_ID_EXTRA;
import static com.example.quickrepair.QuickRepairApplication.YEAR_EXTRA;

public class RequestRepairActivity extends AppCompatActivity implements RequestRepairView
{
    RequestRepairViewModel viewModel;
    RequestRepairPresenter presenter;

    //Parameters of the activity to be passed on startup
    int year;
    int month;
    int dayOfMonth;

    int loggedInCustomerId;

    int technicianId;
    int jobTypeId;

    ListView timesList;
    EditText addressText;
    EditText commentsText;
    NumberPicker hourNumber;
    NumberPicker minuteNumber;

    Button confirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_repair);
        viewModel = new ViewModelProvider(this).get(RequestRepairViewModel.class);
        presenter = viewModel.getPresenter();
        presenter.setView(this);
        //Reading parameters from the intent that created the activity

        Intent intent = getIntent();
        year = intent.getIntExtra(YEAR_EXTRA , 0);
        month = intent.getIntExtra(MONTH_EXTRA , 0);
        dayOfMonth =  intent.getIntExtra(DAY_EXTRA , 0);
        loggedInCustomerId =  intent.getIntExtra(CUSTOMER_ID_EXTRA , 0);
        technicianId = intent.getIntExtra(TECHNICIAN_ID_EXTRA , 0);
        jobTypeId = intent.getIntExtra(JOBTYPE_ID_EXTRA , 0);

        presenter.setDate(year, month, dayOfMonth);
        presenter.setLoggedInUser(loggedInCustomerId);
        presenter.setJobTypeId(jobTypeId);
        presenter.setTechnicianId(technicianId);

        timesList = findViewById(R.id.times_list);
        addressText = findViewById(R.id.address);
        commentsText = findViewById(R.id.comments);
        hourNumber = findViewById(R.id.hour);
        minuteNumber = findViewById(R.id.minutes);
        hourNumber.setMaxValue(23);
        hourNumber.setMinValue(0);
        minuteNumber.setMaxValue(59);
        minuteNumber.setMinValue(0);

        confirmButton = findViewById(R.id.confirm_button);
        confirmButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                presenter.setComments(commentsText.getText().toString());
                presenter.setTime(hourNumber.getValue(), minuteNumber.getValue());
                presenter.setAddress(addressText.getText().toString());
                presenter.requestRepair();
            }
        });

        presenter.onStart();

    }

    @Override
    public void setTechnicianPhoneNumber(String text)
    {
        ((TextView) findViewById(R.id.phone_number)).setText(text);
    }

    @Override
    public void setTechnicianName(String text)
    {
        ((TextView) findViewById(R.id.technician_name)).setText(text);
    }

    @Override
    public void setJobTypeName(String text)
    {
        ((TextView) findViewById(R.id.job_type_name)).setText(text);
    }

    @Override
    public void showTimesAvailable(List<String> availableHours)
    {
        ArrayAdapter<String> listAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, availableHours);
        timesList.setAdapter(listAdapter);
    }

    @Override
    public void showError(String error)
    {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    public void submit()
    {
        Intent intent = new Intent(this, CustomerRepairRequestsActivity.class);
        intent.putExtra(CUSTOMER_ID_EXTRA, getIntent().getIntExtra(CUSTOMER_ID_EXTRA, 0));
        startActivity(intent);

        finish();
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, CustomerRepairRequestsActivity.class);
        intent.putExtra(CUSTOMER_ID_EXTRA, getIntent().getIntExtra(CUSTOMER_ID_EXTRA, 0));
        startActivity(intent);

        finish();
    }
}
