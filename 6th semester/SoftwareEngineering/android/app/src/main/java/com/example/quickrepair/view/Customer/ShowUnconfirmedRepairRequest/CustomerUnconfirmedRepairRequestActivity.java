package com.example.quickrepair.view.Customer.ShowUnconfirmedRepairRequest;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.quickrepair.R;
import com.example.quickrepair.view.Customer.RepairRequests.CustomerRepairRequestsActivity;

import static com.example.quickrepair.QuickRepairApplication.CUSTOMER_ID_EXTRA;
import static com.example.quickrepair.QuickRepairApplication.REPAIR_REQUEST_ID_EXTRA;

public class CustomerUnconfirmedRepairRequestActivity extends AppCompatActivity implements CustomerUnconfirmedRepairRequestView
{
    private static int repairRequestID;
    private static int customerID;

    private CustomerUnconfirmedRepairRequestViewModel customerUnconfirmedRepairRequestViewModel;
    private CustomerUnconfirmedRepairRequestPresenter presenter;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_unconfirmed_repair_request);

        Intent intent = getIntent();
        repairRequestID = intent.getIntExtra(REPAIR_REQUEST_ID_EXTRA, 0);
        customerID = intent.getIntExtra(CUSTOMER_ID_EXTRA, 0);


        customerUnconfirmedRepairRequestViewModel = new ViewModelProvider(this).get(CustomerUnconfirmedRepairRequestViewModel.class);

        presenter = customerUnconfirmedRepairRequestViewModel.getPresenter();
        presenter.setView(this);

        presenter.searchRepairRequestData(repairRequestID);
    }


    @Override
    public void showError(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setJob(String job)
    {
        TextView jobTextView = findViewById(R.id.job);
        jobTextView.setText(job);
    }

    @Override
    public void setTechnicianName(String technicianName)
    {
        TextView consumerTextView = findViewById(R.id.technician);
        consumerTextView.setText(technicianName);
    }

    @Override
    public void setAddress(String address)
    {
        TextView addressTextView = findViewById(R.id.address);
        addressTextView.setText(address);
    }

    @Override
    public void setComments(String comments)
    {
        TextView commentsTextView = findViewById(R.id.comments);
        commentsTextView.setText(comments);
    }

    @Override
    public void setConductionDate(String conductionDate)
    {
        TextView conductionDateTextView = findViewById(R.id.conduction_date);
        conductionDateTextView.setText(conductionDate);
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, CustomerRepairRequestsActivity.class);
        intent.putExtra(CUSTOMER_ID_EXTRA, customerID);
        this.startActivity(intent);
        finish();
    }
}
