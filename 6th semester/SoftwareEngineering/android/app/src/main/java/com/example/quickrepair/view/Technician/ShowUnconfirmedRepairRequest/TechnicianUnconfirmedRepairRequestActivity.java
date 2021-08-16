package com.example.quickrepair.view.Technician.ShowUnconfirmedRepairRequest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.quickrepair.R;
import com.example.quickrepair.view.Technician.RepairRequests.TechnicianRepairRequestsActivity;

import static com.example.quickrepair.QuickRepairApplication.REPAIR_REQUEST_ID_EXTRA;
import static com.example.quickrepair.QuickRepairApplication.TECHNICIAN_ID_EXTRA;

public class TechnicianUnconfirmedRepairRequestActivity extends AppCompatActivity implements TechnicianUnconfirmedRepairRequestView
{

    private static int repairRequestID;
    private static int technicianID;
    private TechnicianUnconfirmedRepairRequestViewModel technicianRepairRequestsViewModel;
    private TechnicianUnconfirmedRepairRequestPresenter presenter;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.technician_unconfirmed_repair_request);

        Intent intent = getIntent();
        repairRequestID = intent.getIntExtra(REPAIR_REQUEST_ID_EXTRA, 0);
        technicianID = intent.getIntExtra(TECHNICIAN_ID_EXTRA, 0);


        technicianRepairRequestsViewModel = new ViewModelProvider(this).get(TechnicianUnconfirmedRepairRequestViewModel.class);

        presenter = technicianRepairRequestsViewModel.getPresenter();
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
    public void reject()
    {
        Intent intent = new Intent(this, TechnicianRepairRequestsActivity.class);
        intent.putExtra(TECHNICIAN_ID_EXTRA, technicianID);
        this.startActivity(intent);
        finish();
    }

    @Override
    public void confirm()
    {
        Intent intent = new Intent(this, TechnicianRepairRequestsActivity.class);
        intent.putExtra(TECHNICIAN_ID_EXTRA, technicianID);
        this.startActivity(intent);
        finish();
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
    public void setButtonsListeners()
    {
        Button buttonReject = findViewById(R.id.reject);
        buttonReject.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                presenter.setReject();
            }
        });

        Button buttonConfirm = findViewById(R.id.confirm);
        buttonConfirm.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                EditText editText = findViewById(R.id.duration);
                String input = editText.getText().toString();
                presenter.setConfirm(input);
            }
        });
    }

}
