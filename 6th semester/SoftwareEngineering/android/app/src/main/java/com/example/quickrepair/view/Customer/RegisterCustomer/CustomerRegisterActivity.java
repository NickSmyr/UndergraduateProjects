package com.example.quickrepair.view.Customer.RegisterCustomer;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.quickrepair.R;
import com.example.quickrepair.view.Base.BaseActivity;
import com.example.quickrepair.view.Customer.RepairRequests.CustomerRepairRequestsActivity;
import com.example.quickrepair.view.HomePage.HomePageActivity;

import static com.example.quickrepair.QuickRepairApplication.CUSTOMER_ID_EXTRA;

public class CustomerRegisterActivity extends BaseActivity<CustomerRegisterViewModel> implements CustomerRegisterView
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
        setContentView(R.layout.activity_customer_register2);

        final CustomerRegisterPresenter presenter = viewModel.getPresenter();
        presenter.setView(this);

        int customerID = getIntent().getIntExtra(CUSTOMER_ID_EXTRA, 0);
        presenter.setCustomer(customerID);
        presenter.setUpDataSource();

        if (customerID != 0)
        {
            findViewById(R.id.Username).setEnabled(false);
            findViewById(R.id.Username).setFocusable(false);
            ((EditText) findViewById(R.id.Username)).setInputType(InputType.TYPE_NULL);
            ((TextView) findViewById(R.id.Title)).setText(R.string.profile);
            ((Button) findViewById(R.id.CustomerRegisterButton)).setText(R.string.save);
        }

        findViewById(R.id.CustomerRegisterButton).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                presenter.registerCustomer(getName(), getSurname(), getPhoneNumber(), getEmail(), getBankAccountNumber(), getUsername(), getPassword());
            }
        });
    }

    /**
     * Set the name of the user trying to register or edit.
     *
     * @param name The name of the customer.
     */
    @Override
    public void setName(String name)
    {
        ((EditText) findViewById(R.id.Name)).setText(name);
    }

    /**
     * Set the surname of the user trying to register or edit.
     *
     * @param surname The surname of the customer.
     */
    @Override
    public void setSurname(String surname)
    {
        ((EditText) findViewById(R.id.Surname)).setText(surname);
    }

    /**
     * Set the phone number of the user trying to register or edit.
     *
     * @param phoneNumber The phone number of the customer.
     */
    @Override
    public void setPhoneNumber(String phoneNumber)
    {
        ((EditText) findViewById(R.id.PhoneNumber)).setText(phoneNumber);
    }

    /**
     * Set the bank account number of the user trying to register or edit.
     *
     * @param accountNumber The bank account number of the customer.
     */
    @Override
    public void setBankAccountNumber(String accountNumber)
    {
        ((EditText) findViewById(R.id.BankAccount)).setText(accountNumber);
    }

    /**
     * Set the email of the user trying to register or edit.
     *
     * @param email The email of the customer.
     */
    @Override
    public void setEmail(String email)
    {
        ((EditText) findViewById(R.id.Email)).setText(email);
    }

    /**
     * Set the username of the user trying to register or edit.
     *
     * @param username The username of the customer.
     */
    @Override
    public void setUsername(String username)
    {
        ((EditText) findViewById(R.id.Username)).setText(username);
    }

    /**
     * Set the password of the user trying to register or edit.
     *
     * @param password The password of the customer.
     */
    @Override
    public void setPassword(String password)
    {
        ((EditText) findViewById(R.id.Password)).setText(password);
    }

    /**
     * Get the name of the customer trying to register or edit.
     *
     * @return The name of the customer trying to register or edit.
     */
    @Override
    public String getName()
    {
        return ((EditText) findViewById(R.id.Name)).getText().toString().trim();
    }

    /**
     * Get the surname of the customer trying to register or edit.
     *
     * @return The surname of the customer trying to register or edit.
     */
    @Override
    public String getSurname()
    {
        return ((EditText) findViewById(R.id.Surname)).getText().toString().trim();
    }

    /**
     * Get the phone number of the customer trying to register or edit.
     *
     * @return The phone number of the customer trying to register or edit.
     */
    @Override
    public String getPhoneNumber()
    {
        return ((EditText) findViewById(R.id.PhoneNumber)).getText().toString().trim();
    }

    /**
     * Get the bank account number of the customer trying to register or edit.
     *
     * @return The bank account number of the customer trying to register or edit.
     */
    @Override
    public String getBankAccountNumber()
    {
        return ((EditText) findViewById(R.id.BankAccount)).getText().toString().trim();
    }

    /**
     * Get the email of the customer trying to register or edit.
     *
     * @return The email of the customer trying to register or edit.
     */
    @Override
    public String getEmail()
    {
        return ((EditText) findViewById(R.id.Email)).getText().toString().trim();
    }

    /**
     * Get the username of the customer trying to register or edit.
     *
     * @return The username of the customer trying to register or edit.
     */
    @Override
    public String getUsername()
    {
        return ((EditText) findViewById(R.id.Username)).getText().toString().trim();
    }

    /**
     * Get the password of the customer trying to register or edit.
     *
     * @return The password of the customer trying to register or edit.
     */
    @Override
    public String getPassword()
    {
        return ((EditText) findViewById(R.id.Password)).getText().toString().trim();
    }

    /**
     * Navigate a valid customer to the customer home page.
     *
     * @param id The customer's id.
     */
    @Override
    public void onSuccessfulRegister(Integer id)
    {
        Intent intent = new Intent(this, CustomerRepairRequestsActivity.class);
        intent.putExtra(CUSTOMER_ID_EXTRA, id);
        startActivity(intent);

        finish();
    }

    /**
     * Get the viewModel associated with this activity.
     *
     * @return The viewModel.
     */
    @Override
    protected CustomerRegisterViewModel getViewModel()
    {
        return new ViewModelProvider(this).get(CustomerRegisterViewModel.class);
    }

    /**
     * Handle on back button press event.
     */
    @Override
    public void onBackPressed()
    {
        if (getIntent().getIntExtra(CUSTOMER_ID_EXTRA, 0) == 0)
        {
            Intent intent = new Intent(this, HomePageActivity.class);
            startActivity(intent);
        }
        else
        {
            Intent intent = new Intent(this, CustomerRepairRequestsActivity.class);
            intent.putExtra(CUSTOMER_ID_EXTRA, getIntent().getIntExtra(CUSTOMER_ID_EXTRA, 0));
            startActivity(intent);
        }

        finish();
    }
}