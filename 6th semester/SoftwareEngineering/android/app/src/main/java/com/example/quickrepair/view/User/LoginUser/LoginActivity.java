package com.example.quickrepair.view.User.LoginUser;

import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.quickrepair.R;
import com.example.quickrepair.view.Base.BaseActivity;
import com.example.quickrepair.view.Customer.RepairRequests.CustomerRepairRequestsActivity;
import com.example.quickrepair.view.HomePage.HomePageActivity;
import com.example.quickrepair.view.Technician.RepairRequests.TechnicianRepairRequestsActivity;

import static com.example.quickrepair.QuickRepairApplication.CUSTOMER_ID_EXTRA;
import static com.example.quickrepair.QuickRepairApplication.REDIRECT_TO_SEARCH_EXTRA;
import static com.example.quickrepair.QuickRepairApplication.RESULT_DENIED;
import static com.example.quickrepair.QuickRepairApplication.RESULT_INVALID;
import static com.example.quickrepair.QuickRepairApplication.TECHNICIAN_ID_EXTRA;

public class LoginActivity extends BaseActivity<LoginViewModel> implements LoginView
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
        setContentView(R.layout.activity_login);

        final LoginPresenter presenter = viewModel.getPresenter();
        presenter.setView(this);

        findViewById(R.id.LoginButton).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                presenter.login(getUsername(), getPassword());
            }
        });
    }

    /**
     * Get the username of the user trying to log in.
     *
     * @return The username of the user trying to log in.
     */
    @Override
    public String getUsername()
    {
        return ((EditText) findViewById(R.id.Username)).getText().toString().trim();
    }

    /**
     * Get the password of the user trying to log in.
     *
     * @return The password of the user trying to log in.
     */
    @Override
    public String getPassword()
    {
        return ((EditText) findViewById(R.id.Password)).getText().toString().trim();
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
        if (getIntent().getBooleanExtra(REDIRECT_TO_SEARCH_EXTRA, false))
        {
            Intent intent = new Intent();
            setResult(RESULT_INVALID, intent);
            finish();
        }
        else
        {
            new AlertDialog.Builder(this).setCancelable(true).setTitle(title).setMessage(message).setPositiveButton(R.string.ok, null).create().show();
        }
    }

    /**
     * Get the viewModel associated with this activity.
     *
     * @return The viewModel.
     */
    @Override
    protected LoginViewModel getViewModel()
    {
        return new ViewModelProvider(this).get(LoginViewModel.class);
    }

    /**
     * Navigate a valid customer to the customer home page.
     *
     * @param id The customer's id.
     */
    @Override
    public void OnLoginCustomerSuccess(Integer id)
    {
        if (getIntent().getBooleanExtra(REDIRECT_TO_SEARCH_EXTRA, false))
        {
            Intent intent = new Intent();
            intent.putExtra(CUSTOMER_ID_EXTRA, id);
            setResult(RESULT_OK, intent);
        }
        else
        {
            Intent intent = new Intent(this, CustomerRepairRequestsActivity.class);
            intent.putExtra(CUSTOMER_ID_EXTRA, id);
            startActivity(intent);
        }

        finish();
    }

    /**
     * Navigate a valid technician to the technician home page.
     *
     * @param id The technician's id.
     */
    @Override
    public void OnLoginTechnicianSuccess(Integer id)
    {

        if (getIntent().getBooleanExtra(REDIRECT_TO_SEARCH_EXTRA, false))
        {
            Intent intent = new Intent();
            setResult(RESULT_DENIED, intent);
        }
        else
        {
            Intent intent = new Intent(this, TechnicianRepairRequestsActivity.class);
            intent.putExtra(TECHNICIAN_ID_EXTRA, id);
            startActivity(intent);
        }

        finish();
    }

    /**
     * Handle on back button press event.
     */
    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, HomePageActivity.class);
        startActivity(intent);

        finish();
    }
}
