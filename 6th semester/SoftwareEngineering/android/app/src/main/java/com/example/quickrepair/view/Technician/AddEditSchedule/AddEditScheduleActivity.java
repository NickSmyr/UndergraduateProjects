package com.example.quickrepair.view.Technician.AddEditSchedule;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.quickrepair.R;
import com.example.quickrepair.view.Base.BaseActivity;
import com.example.quickrepair.view.Technician.AddEditArea.AddEditAreaActivity;

import static com.example.quickrepair.QuickRepairApplication.TECHNICIAN_ID_EXTRA;

public class AddEditScheduleActivity extends BaseActivity<AddEditScheduleViewModel> implements AddEditScheduleView
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
        setContentView(R.layout.activity_add_edit_schedule);

        final AddEditSchedulePresenter presenter = viewModel.getPresenter();
        presenter.setView(this);

        presenter.setTechnician(getIntent().getIntExtra(TECHNICIAN_ID_EXTRA, 0));
        presenter.setUpDataSource();

        findViewById(R.id.Submit).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                presenter.submit(generateSchedule());
            }
        });

        findViewById(R.id.ScheduleNext).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                goToArea();
            }
        });
    }

    /**
     * Set the starting and ending hour of the given day.
     *
     * @param day The day to set the hours.
     * @param start The starting hour.
     * @param end The ending hour.
     */
    @Override
    public void setDay(int day, int start, int end)
    {
        LinearLayout layout = findViewById(getResources().getIdentifier("day" + day, "id", getPackageName()));

        for (int i = 0; i < 3; ++i)
        {
            if (layout.getChildAt(i) instanceof EditText)
            {
                EditText text = (EditText) layout.getChildAt(i);

                if (text.getHint().equals("Start"))
                {
                    text.setText(String.valueOf(start));
                }
                else
                {
                    text.setText(String.valueOf(end));
                }
            }
        }
    }

    /**
     * Get the viewModel associated with this activity.
     *
     * @return The viewModel.
     */
    @Override
    protected AddEditScheduleViewModel getViewModel()
    {
        return new ViewModelProvider(this).get(AddEditScheduleViewModel.class);
    }

    /**
     * Generate a two dimensional array representing the technician's schedule.
     *
     * @return A two dimensional array representing the technician's schedule.
     */
    private Integer[][] generateSchedule()
    {
        Integer[][] schedule = new Integer[7][2];

        for (int i = 0; i < 7; ++i)
        {
            LinearLayout layout = findViewById(getResources().getIdentifier("day" + i, "id", getPackageName()));

            for (int j = 0; j < 3; ++j)
            {
                if (layout.getChildAt(j) instanceof EditText)
                {
                    EditText text = (EditText) layout.getChildAt(j);

                    if (text.getHint().equals("Start"))
                    {
                        schedule[i][0] = Integer.parseInt(text.getText().toString());
                    }
                    else
                    {
                        schedule[i][1] = Integer.parseInt(text.getText().toString());
                    }
                }
            }
        }

        return schedule;
    }

    /**
     * Navigate to EddEditArea page.
     */
    private void goToArea()
    {
        Intent intent = new Intent(this, AddEditAreaActivity.class);
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
