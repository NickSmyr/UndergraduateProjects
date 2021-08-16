package com.example.quickrepair.view.Customer.ShowCompletedRepairRequest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.quickrepair.R;
import com.example.quickrepair.view.Customer.RepairRequests.CustomerRepairRequestsActivity;

import static com.example.quickrepair.QuickRepairApplication.CUSTOMER_ID_EXTRA;
import static com.example.quickrepair.QuickRepairApplication.REPAIR_REQUEST_ID_EXTRA;

public class CustomerCompletedRepairRequestActivity extends AppCompatActivity implements CustomerCompletedRepairRequestView{

    private static int repairRequestID;
    private static int customerID;

    private CustomerCompletedRepairRequestViewModel customerCompletedRepairRequestViewModel;
    CustomerCompletedRepairRequestPresenter presenter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_completed_repair_request);

        Intent intent = getIntent();
        repairRequestID = intent.getIntExtra(REPAIR_REQUEST_ID_EXTRA, 0);
        customerID = intent.getIntExtra(CUSTOMER_ID_EXTRA, 0);

        customerCompletedRepairRequestViewModel = new ViewModelProvider(this).get(CustomerCompletedRepairRequestViewModel.class);

        presenter = customerCompletedRepairRequestViewModel.getPresenter();
        presenter.setView(this);

        presenter.searchRepairRequestData(repairRequestID);

    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setJob(String job) {
        TextView jobTextView = findViewById(R.id.job);
        jobTextView.setText(job);
    }

    @Override
    public void setTechnicianName(String technicianName) {
        TextView technicianTextView = findViewById(R.id.technician);
        technicianTextView.setText(technicianName);
    }

    @Override
    public void setAddress(String address) {
        TextView addressTextView = findViewById(R.id.address);
        addressTextView.setText(address);
    }

    @Override
    public void setComments(String comments) {
        TextView commentsTextView = findViewById(R.id.comments);
        commentsTextView.setText(comments);
    }

    @Override
    public void setConductionDate(String conductionDate) {
        TextView conductionDateTextView = findViewById(R.id.conduction_date);
        conductionDateTextView.setText(conductionDate);
    }

    @Override
    public void setEstimatedDuration(String estimatedDuration) {
        TextView estimatedDurationTextView = findViewById(R.id.estimated_duration);
        estimatedDurationTextView.setText(estimatedDuration);
    }

    @Override
    public void setCost(String cost) {
        TextView costTextView = findViewById(R.id.cost);
        costTextView.setText(cost);
    }

    @Override
    public void setPayAndEvaluationFields() {

        Button pay = findViewById(R.id.pay_button);
        pay.setVisibility(View.VISIBLE);

        EditText editTextTitle = findViewById(R.id.title);
        EditText editTextEvaluate = findViewById(R.id.evaluate);

        editTextTitle.setVisibility(View.VISIBLE);
        editTextEvaluate.setVisibility(View.VISIBLE);

        NumberPicker rate = findViewById(R.id.rate);
        rate.setVisibility(View.VISIBLE);

        rate.setMaxValue(5);
        rate.setMinValue(1);
    }



    @Override
    public void donePayAndEvaluate() {
        Intent intent = new Intent(this, CustomerRepairRequestsActivity.class);
        intent.putExtra(CUSTOMER_ID_EXTRA, customerID);
        this.startActivity(intent);
        finish();
    }

    @Override
    public void setPayListener() {
        Button buttonConfirm = findViewById(R.id.pay_button);
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // get title, evaluate
                EditText editTextTitle = findViewById(R.id.title);
                EditText editTextEvaluate = findViewById(R.id.evaluate);
                String title = editTextTitle.getText().toString();
                String evaluate = editTextEvaluate.getText().toString();
                //get rate
                NumberPicker numberPicker = findViewById(R.id.rate);
                int rate = numberPicker.getValue();
                presenter.payAndEvaluate(title, evaluate, rate);
            }
        });
    }

    @Override
    public void setEvaluationData(String title, String comments, String rate) {
        TextView evaluationTitleText = findViewById(R.id.evaluation_title);
        TextView evaluationCommentsText = findViewById(R.id.evaluation_comments);
        TextView evaluationRateText = findViewById(R.id.evaluation_rate);

        // set text
        evaluationTitleText.setText(title);
        evaluationCommentsText.setText(comments);
        evaluationRateText.setText(rate);
        // set visible
        evaluationTitleText.setVisibility(View.VISIBLE);
        evaluationCommentsText.setVisibility(View.VISIBLE);
        evaluationRateText.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, CustomerRepairRequestsActivity.class);
        intent.putExtra(CUSTOMER_ID_EXTRA, customerID);
        this.startActivity(intent);
        finish();
    }
}
