package com.example.quickrepair.view.Technician.AddEditJob;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.quickrepair.R;
import com.example.quickrepair.domain.Job;
import com.example.quickrepair.view.Base.BaseActivity;
import com.example.quickrepair.view.Technician.AddEditArea.AddEditAreaActivity;
import com.example.quickrepair.view.Technician.RepairRequests.TechnicianRepairRequestsActivity;

import java.util.ArrayList;
import java.util.List;

import static com.example.quickrepair.QuickRepairApplication.TECHNICIAN_ID_EXTRA;

public class AddEditJobActivity extends BaseActivity<AddEditJobViewModel> implements AddEditJobView
{
    /**
     * Create and initialize the activity.
     *
     * @param savedInstanceState The activity's saved instance.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_job);

        final AddEditJobPresenter presenter = viewModel.getPresenter();
        presenter.setView(this);

        final RecyclerView jobList = findViewById(R.id.JobList);

        RecyclerView.LayoutManager manager = new GridLayoutManager(this, 1);
        jobList.setLayoutManager(manager);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT)
        {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target)
            {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int swipeDir)
            {
                presenter.removeJob(viewHolder.getAdapterPosition());
                presenter.setUpDataSource();
            }
        });

        itemTouchHelper.attachToRecyclerView(jobList);

        presenter.setTechnician(getIntent().getIntExtra(TECHNICIAN_ID_EXTRA, 0));
        presenter.setUpDataSource();

        findViewById(R.id.AddJobButton).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int jobTypePosition = ((Spinner) findViewById(R.id.JobTypesSpinner)).getSelectedItemPosition();
                String price = ((EditText) findViewById(R.id.PriceInput)).getText().toString();

                presenter.addJob(jobTypePosition, price);
            }
        });

        findViewById(R.id.FinalizeRegisterButton).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finalizeChanges();
            }
        });
    }

    /**
     * Populate the list holding all available job types.
     *
     * @param jobTypeList The list of job types.
     * @param defaultName A default job type placeholder.
     */
    @Override
    public void setJobTypeList(List<String> jobTypeList, String defaultName)
    {
        jobTypeList.add(0, defaultName);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, jobTypeList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ((Spinner) findViewById(R.id.JobTypesSpinner)).setAdapter(adapter);
    }

    /**
     * Populate the list holding the technician's job types.
     *
     * @param selectedJobs The list of the technician's job types.
     */
    @Override
    public void setJobList(List<Job> selectedJobs)
    {
        RecyclerView jobList = findViewById(R.id.JobList);
        JobAdapter adapter = new JobAdapter(new ArrayList<>(selectedJobs));
        jobList.setAdapter(adapter);
    }

    /**
     * Get the viewModel associated with this activity.
     *
     * @return The viewModel.
     */
    @Override
    protected AddEditJobViewModel getViewModel()
    {
        return new ViewModelProvider(this).get(AddEditJobViewModel.class);
    }

    /**
     * Navigate to the technician home page.
     */
    public void finalizeChanges()
    {
        Intent intent = new Intent(this, TechnicianRepairRequestsActivity.class);
        intent.putExtra(TECHNICIAN_ID_EXTRA, getIntent().getIntExtra(TECHNICIAN_ID_EXTRA, 0));
        startActivity(intent);

        finish();
    }

    /**
     * Handle on back button press event.
     */
    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, AddEditAreaActivity.class);
        intent.putExtra(TECHNICIAN_ID_EXTRA, getIntent().getIntExtra(TECHNICIAN_ID_EXTRA, 0));
        startActivity(intent);

        finish();
    }
}
