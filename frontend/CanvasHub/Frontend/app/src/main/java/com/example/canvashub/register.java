package com.example.canvashub;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.canvashub.api.addressApi;
import com.example.canvashub.api.customerApi;
import com.example.canvashub.model.Address;
import com.example.canvashub.model.Customer;
import com.example.canvashub.retrofit.retrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class register extends AppCompatActivity {

    ProgressBar progressBarProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        // Initialize the ProgressBar
        progressBarProfile = findViewById(R.id.registerProgressBar);

        Button createAccount = (Button) findViewById(R.id.createaccount);
        TextView loginBack = findViewById(R.id.loginback);
        EditText inputUsername = (EditText) findViewById(R.id.input_username);
        EditText inputPassword = (EditText) findViewById(R.id.input_password);
        EditText inputEmail = (EditText) findViewById(R.id.input_email);
        EditText inputContact = (EditText) findViewById(R.id.input_contact);

        retrofitService retrofit_service = new retrofitService();
        customerApi customer_api = retrofit_service.getRetrofit().create(customerApi.class);
        addressApi address_api = retrofit_service.getRetrofit().create(addressApi.class);


        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBarProfile.setVisibility(View.VISIBLE);

                Boolean isValidated = true;

                String username = inputUsername.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                String email = inputEmail.getText().toString().trim();
                String contact = inputContact.getText().toString().trim();

                if (username.isEmpty()) {
                    inputUsername.setError("USERNAME IS REQUIRED");
                    inputUsername.requestFocus();
                    isValidated = false;
                }

                if (password.isEmpty()) {
                    inputPassword.setError("PASSWORD IS REQUIRED");
                    inputPassword.requestFocus();
                    isValidated = false;
                }

                if (password.length() < 6) {
                    inputPassword.setError("PASSWORD SHOULD BE AT LEAST 6 CHARACTERS");
                    inputPassword.requestFocus();
                    isValidated = false;
                }

                if (email.isEmpty()) {
                    inputEmail.setError("EMAIL IS REQUIRED");
                    inputEmail.requestFocus();
                    isValidated = false;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    inputEmail.setError("ENTER VALID EMAIL");
                    inputEmail.requestFocus();
                    isValidated = false;
                }

                if (contact.isEmpty()) {
                    inputContact.setError("CONTACT IS REQUIRED");
                    inputContact.requestFocus();
                    isValidated = false;
                }

                if (isValidated) {
                    customer_api.getAllCustomers().enqueue(new Callback<List<Customer>>() {
                        @Override
                        public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                            List<Customer> allCustomers = response.body();
                            boolean customerExists = false;

                            for (Customer xCustomer : allCustomers) {
                                if (username.equals(xCustomer.getUsername())) {
                                    progressBarProfile.setVisibility(View.GONE);
                                    customerExists = true;
                                    Toast.makeText(getBaseContext(), "CUSTOMER EXISTED", Toast.LENGTH_SHORT).show();
                                    break;
                                }
                            }

                            if (!customerExists) {
                                progressBarProfile.setVisibility(View.VISIBLE);

                                int maxId = 0;
                                for (Customer xCustomer : allCustomers) {
                                    int customerId = Integer.parseInt(xCustomer.getId());
                                    if (customerId > maxId) {
                                        maxId = customerId;
                                    }
                                }
                                // Increment the maximum ID to get the new ID
                                int newId = maxId + 1;
                                String newCustomerId = String.valueOf(newId);

                                Address xAddress = new Address();
                                xAddress.setId("1");
                                xAddress.setBuilding("Building");
                                xAddress.setCity("City");
                                xAddress.setCountry("Country");
                                xAddress.setDistrict("District");
                                xAddress.setProvince("Province");
                                xAddress.setZip_code("ZipCode");
                                xAddress.setStreet("Street");

                                Customer xCustomer = new Customer();
                                xCustomer.setId(newCustomerId);
                                xCustomer.setUsername(username);
                                xCustomer.setPassword(password);
                                xCustomer.setEmail_address(email);
                                xCustomer.setContact_number(contact);
                                xCustomer.setFirst_name("Update");
                                xCustomer.setLast_name("Information");
                                xCustomer.setAddress_Id(xAddress);
                                xCustomer.setTries_timestamp(0);

                                address_api.addAddress(xAddress).enqueue(new Callback<Address>() {
                                    @Override
                                    public void onResponse(Call<Address> call, Response<Address> response) {
                                        customer_api.addCustomer(xCustomer).enqueue(new Callback<Customer>() {
                                            @Override
                                            public void onResponse(Call<Customer> call, Response<Customer> response) {

                                                progressBarProfile.setVisibility(View.GONE);
                                                Intent intent = new Intent(getApplicationContext(), login.class);
                                                startActivity(intent);
                                            }

                                            @Override
                                            public void onFailure(Call<Customer> call, Throwable throwable) {


                                                //network error
                                                progressBarProfile.setVisibility(View.VISIBLE);
                                                Toast.makeText(getBaseContext(), "NETWORK ERROR!", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }

                                    @Override
                                    public void onFailure(Call<Address> call, Throwable throwable) {

                                        //network error
                                        progressBarProfile.setVisibility(View.VISIBLE);
                                        Toast.makeText(getBaseContext(), "NETWORK ERROR!", Toast.LENGTH_SHORT).show();

                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Customer>> call, Throwable throwable) {

                            //network error
                            progressBarProfile.setVisibility(View.VISIBLE);
                            Toast.makeText(getBaseContext(), "NETWORK ERROR!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        loginBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), login.class);
                startActivity(intent);
            }
        });
    }
}