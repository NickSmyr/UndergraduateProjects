package com.example.quickrepair.view.Technician.AddEditArea;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.quickrepair.R;
import com.example.quickrepair.view.Base.BaseActivity;
import com.example.quickrepair.view.Technician.AddEditJob.AddEditJobActivity;
import com.example.quickrepair.view.Technician.RepairRequests.TechnicianRepairRequestsActivity;

import java.util.ArrayList;
import java.util.List;

import static com.example.quickrepair.QuickRepairApplication.TECHNICIAN_ID_EXTRA;

public class AddEditAreaActivity extends BaseActivity<AddEditAreaViewModel> implements AddEditAreaView
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
        setContentView(R.layout.activity_add_edit_area);

        final AddEditAreaPresenter presenter = viewModel.getPresenter();
        presenter.setView(this);

        final RecyclerView areaList = findViewById(R.id.AreaList);

        RecyclerView.LayoutManager manager = new GridLayoutManager(this, 1);
        areaList.setLayoutManager(manager);

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
                presenter.removeArea(viewHolder.getAdapterPosition());
                presenter.setUpDataSource();
            }
        });

        itemTouchHelper.attachToRecyclerView(areaList);

        presenter.setTechnician(getIntent().getIntExtra(TECHNICIAN_ID_EXTRA, 0));
        presenter.setUpDataSource();

        findViewById(R.id.AddAreaButton).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                presenter.addArea(((Spinner)findViewById(R.id.AreaSpinner)).getSelectedItemPosition());
            }
        });

        findViewById(R.id.NextButton).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                goToEditJob();
            }
        });
    }

    /**
     * Populate the list holding all available areas.
     *
     * @param areaList The list of areas.
     * @param defaultName A default area placeholder.
     */
    @Override
    public void setAreaList(List<String> areaList, String defaultName)
    {
        areaList.add(0, defaultName);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, areaList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ((Spinner)findViewById(R.id.AreaSpinner)).setAdapter(adapter);
    }

    /**
     * Populate the list holding the technician's areas.
     *
     * @param selectedArea The list of the technician's areas.
     */
    @Override
    public void setSelectedArea(List<String> selectedArea)
    {
        RecyclerView areaList = findViewById(R.id.AreaList);
        AreaAdapter adapter = new AreaAdapter(new ArrayList<>(selectedArea));
        areaList.setAdapter(adapter);
    }

    /**
     * Navigate to the AddEditJob page.
     */
    public void goToEditJob()
    {
        Intent intent = new Intent(this, AddEditJobActivity.class);
        intent.putExtra(TECHNICIAN_ID_EXTRA, getIntent().getIntExtra(TECHNICIAN_ID_EXTRA, 0));
        startActivity(intent);

        finish();
    }

    /**
     * Get the viewModel associated with this activity.
     *
     * @return The viewModel.
     */
    @Override
    protected AddEditAreaViewModel getViewModel()
    {
        return new ViewModelProvider(this).get(AddEditAreaViewModel.class);
    }

    /**
     * Handle on back button press event.
     */
    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, TechnicianRepairRequestsActivity.class);
        intent.putExtra(TECHNICIAN_ID_EXTRA, getIntent().getIntExtra(TECHNICIAN_ID_EXTRA, 0));
        startActivity(intent);

        finish();
    }
}
