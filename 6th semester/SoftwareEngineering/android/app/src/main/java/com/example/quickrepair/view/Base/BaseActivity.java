package com.example.quickrepair.view.Base;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quickrepair.R;

public abstract class BaseActivity<VM extends BaseViewModel> extends AppCompatActivity implements BaseView
{
    protected VM viewModel;

    /**
     * Create and initialize the activity.
     *
     * @param savedInstanceState The activity's saved instance.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        viewModel = getViewModel();
    }

    /**
     * Display a message in the event of an error.
     *
     * @param title The title of the error.
     * @param message The message of the error.
     */
    @Override
    public void showErrorMessage(String title, String message)
    {
        new AlertDialog.Builder(this).setCancelable(true).setTitle(title).setMessage(message).setPositiveButton(R.string.ok, null).create().show();
    }

    /**
     * Get the viewModel associated with this activity.
     *
     * @return The viewModel.
     */
    protected abstract VM getViewModel();
}
