package com.example.quickrepair.view.Technician.ShowCompletedRepairRequest;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.quickrepair.R;
import com.example.quickrepair.view.Technician.RepairRequests.TechnicianRepairRequestsActivity;

import static com.example.quickrepair.QuickRepairApplication.REPAIR_REQUEST_ID_EXTRA;
import static com.example.quickrepair.QuickRepairApplication.TECHNICIAN_ID_EXTRA;

public class TechnicianCompletedRepairRequestActivity extends AppCompatActivity implements TechnicianCompletedRepairRequestView
{
    private static int repairRequestID;
    private static int technicianID;

    private TechnicianCompletedRepairRequestViewModel technicianCompletedRepairRequestViewModel;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.technician_completed_repair_request);

        Intent intent = getIntent();
        repairRequestID = intent.getIntExtra(REPAIR_REQUEST_ID_EXTRA, 0);
        technicianID = intent.getIntExtra(TECHNICIAN_ID_EXTRA, 0);

        technicianCompletedRepairRequestViewModel = new ViewModelProvider(this).get(TechnicianCompletedRepairRequestViewModel.class);

        final TechnicianCompletedRepairRequestPresenter presenter = technicianCompletedRepairRequestViewModel.getPresenter();
        presenter.setView(this);

        presenter.searchRepairRequestData(repairRequestID);

    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, TechnicianRepairRequestsActivity.class);
        intent.putExtra(TECHNICIAN_ID_EXTRA, technicianID);
        this.startActivity(intent);
        finish();
    }

    @Override
    public void setJob(String job)
    {
        TextView jobTextView = findViewById(R.id.job);
        jobTextView.setText(job);
    }

    @Override
    public void setConsumerName(String consumerName)
    {
        TextView consumerTextView = findViewById(R.id.consumer);
        consumerTextView.setText(consumerName);
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
    public void setEstimatedDuration(String estimatedDuration)
    {
        TextView estimatedDurationTextView = findViewById(R.id.estimated_duration);
        estimatedDurationTextView.setText(estimatedDuration);

    }

    @Override
    public void setCost(String cost)
    {
        TextView costTextView = findViewById(R.id.cost);
        costTextView.setText(cost);

    }

    @Override
    public void showError(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

}
