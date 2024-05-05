package com.example.canvashub;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.canvashub.api.customerApi;
import com.example.canvashub.model.Address;
import com.example.canvashub.model.Customer;
import com.example.canvashub.retrofit.retrofitService;
import com.example.canvashub.session.SessionManager;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class profile extends AppCompatActivity {

    TabLayout tabLayout;
    private SessionManager sessionManager;

    TextView profileAddress;
    TextView profileFirstName;
    TextView profileLastName;
    TextView profileContact;
    String fullAddress;

    // Define the ProgressBar
    ProgressBar progressBarProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        sessionManager = new SessionManager(this);
        retrofitService retrofitService = new retrofitService();
        customerApi customerApi = retrofitService.getRetrofit().create(customerApi.class);

        // Initialize the ProgressBar
        progressBarProfile = findViewById(R.id.loadingProgressBar);

        profileFirstName = findViewById(R.id.profile_first_name_text_view);
        profileLastName = findViewById(R.id.profile_last_name_text_view);
        profileAddress = findViewById(R.id.profile_address_text_view);
        profileContact = findViewById(R.id.profile_contact_number_text_view);

        EditText inputFirstName = findViewById(R.id.profile_first_name);
        EditText inputLastName = findViewById(R.id.profile_last_name);
        EditText inputUsername = findViewById(R.id.profile_username);
        EditText inputEmail = findViewById(R.id.profile_email_address);
        EditText inputPhoneNumber = findViewById(R.id.profile_phone_number);

        Button buttonUpdateAddress = findViewById(R.id.update_address_profile);
        Button buttonSaveChanges = findViewById(R.id.save_profile);
        Button buttonResetPassword = findViewById(R.id.reset_password_profile);
        Button buttonLogout = findViewById(R.id.logout_button);

        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.getTabAt(4).select();

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.logout();
                Intent intent = new Intent(getApplicationContext(), login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        buttonUpdateAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), update_address.class);
                startActivity(intent);
            }
        });

        buttonResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), recovery.class);
                startActivity(intent);
            }
        });

        buttonSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isValidated = true;

                if((inputFirstName.getText().toString().trim()).isEmpty()){
                    inputFirstName.setError("FIRST NAME IS REQUIRED");
                    inputFirstName.requestFocus();
                    isValidated = false;
                }
                if((inputLastName.getText().toString().trim()).isEmpty()){
                    inputLastName.setError("LAST NAME IS REQUIRED");
                    inputLastName.requestFocus();
                    isValidated = false;
                }
                if((inputUsername.getText().toString().trim()).isEmpty()){
                    inputUsername.setError("USERNAME IS REQUIRED");
                    inputUsername.requestFocus();
                    isValidated = false;
                }
                if((inputEmail.getText().toString().trim()).isEmpty()){
                    inputEmail.setError("EMAIL ADDRESS IS REQUIRED");
                    inputEmail.requestFocus();
                    isValidated = false;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(inputEmail.getText().toString().trim()).matches()) {
                    inputEmail.setError("ENTER VALID EMAIL");
                    inputEmail.requestFocus();
                    isValidated = false;
                }
                if((inputPhoneNumber.getText().toString().trim()).isEmpty()){
                    inputPhoneNumber.setError("CONTACT IS REQUIRED");
                    inputPhoneNumber.requestFocus();
                    isValidated = false;
                }

                if(isValidated){
                    customerApi.getCustomer(sessionManager.getCustomerId()).enqueue(new Callback<Customer>() {
                        @Override
                        public void onResponse(Call<Customer> call, Response<Customer> response) {
                            Customer updatedCustomer = response.body();
                            if (updatedCustomer != null) {
                                updatedCustomer.setFirst_name(inputFirstName.getText().toString().trim());
                                updatedCustomer.setLast_name(inputLastName.getText().toString().trim());
                                updatedCustomer.setUsername(inputUsername.getText().toString().trim());
                                updatedCustomer.setEmail_address(inputEmail.getText().toString().trim());
                                updatedCustomer.setContact_number(inputPhoneNumber.getText().toString().trim());

                                customerApi.updateCustomer(updatedCustomer).enqueue(new Callback<Customer>() {
                                    @Override
                                    public void onResponse(Call<Customer> call, Response<Customer> response) {
                                        if (response.isSuccessful()) {
                                            updateProfileUI(updatedCustomer);
                                            inputFirstName.getText().clear();
                                            inputLastName.getText().clear();
                                            inputUsername.getText().clear();
                                            inputEmail.getText().clear();
                                            inputPhoneNumber.getText().clear();
                                            Toast.makeText(profile.this, "PROFILE UPDATED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(profile.this, "CHECK YOUR NETWORK", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Customer> call, Throwable throwable) {
                                        // Handle failure
                                        Toast.makeText(profile.this, "CHECK YOUR NETWORK", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(Call<Customer> call, Throwable throwable) {
                            // Handle failure
                            Toast.makeText(profile.this, "CHECK YOUR NETWORK", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        startActivity(new Intent(getBaseContext(), home.class));
                        break;
                    case 1:
                        startActivity(new Intent(getBaseContext(), search.class));
                        break;
                    case 2:
                        startActivity(new Intent(getBaseContext(), cart.class));
                        break;
                    case 3:
                        startActivity(new Intent(getBaseContext(), payment.class));
                        break;
                    case 4:
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // Optional: Handle tab unselected
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Optional: Handle tab reselection
            }
        });

        fetchCustomerData(customerApi);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchCustomerData(new retrofitService().getRetrofit().create(customerApi.class));
    }

    // Method to show the ProgressBar
    private void showProgressBar() {
        progressBarProfile.setVisibility(View.VISIBLE);
    }

    // Method to hide the ProgressBar
    private void hideProgressBar() {
        progressBarProfile.setVisibility(View.GONE);
    }

    private void fetchCustomerData(customerApi customerApi) {

        // Show the ProgressBar before fetching customer data
        showProgressBar();

        customerApi.getCustomer(sessionManager.getCustomerId()).enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                // Once data is fetched, hide the ProgressBar
                hideProgressBar();

                Customer getCustomer = response.body();
                if (getCustomer != null) {
                    updateProfileUI(getCustomer);
                }
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable throwable) {
                // Handle failure
                // Once data is fetched, hide the ProgressBar
                hideProgressBar();
            }
        });
    }

    private void updateProfileUI(Customer customer) {
        profileFirstName.setText(customer.getFirst_name());
        profileLastName.setText(customer.getLast_name());
        profileContact.setText(customer.getContact_number());
        //profileImageIcon
        Address address = customer.getAddress_Id();
        if (address != null) {
            fullAddress = getString(R.string.address_format, address.getBuilding(), address.getStreet(), address.getDistrict(), address.getCity(), address.getProvince(), address.getZip_code(), address.getCountry());

            if(fullAddress.contains("N/A")) {
                // Display a toast notification if the address contains "N/A"
                Toast.makeText(profile.this, "UPDATE YOUR INFORMATION", Toast.LENGTH_SHORT).show();
            }

            profileAddress.setText(fullAddress);
        }
    }
}
