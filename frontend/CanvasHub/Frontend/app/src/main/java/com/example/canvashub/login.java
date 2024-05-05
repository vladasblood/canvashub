package com.example.canvashub;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.canvashub.api.addressApi;
import com.example.canvashub.api.customerApi;
import com.example.canvashub.model.Customer;
import com.example.canvashub.retrofit.retrofitService;
import com.example.canvashub.session.SessionManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class login extends AppCompatActivity {

    private SessionManager sessionManager;

    ProgressBar progressBarProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // Initialize the ProgressBar
        progressBarProfile = findViewById(R.id.loginProgressBar);

        Button login = (Button) findViewById(R.id.createaccount);
        TextView signup = findViewById(R.id.loginback);
        TextView forgotpassword = findViewById(R.id.txtview_forgot);
        EditText inputUsername = (EditText) findViewById(R.id.input_username);
        EditText inputPassword = (EditText) findViewById(R.id.input_password);

        retrofitService retrofit_service = new retrofitService();
        customerApi customer_api = retrofit_service.getRetrofit().create(customerApi.class);
        addressApi address_api = retrofit_service.getRetrofit().create(addressApi.class);

        //Initialize Session
        try{
            sessionManager = new SessionManager(this);

        } catch(Exception e){
            e.printStackTrace();
            Toast.makeText(getBaseContext(), "FAILED TO INITIALIZE ", Toast.LENGTH_SHORT).show();
            return;
        }

        //Check if user is logged on
        if(sessionManager.isLoggedIn()){
            //Redirect to Home Activity
            Intent intent = new Intent(getApplicationContext(), home.class);
            intent.putExtra("customer_id", sessionManager.getCustomerId());
            startActivity(intent);
            finish();
        }
        //End-of-Initialization

        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), recovery.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Boolean isValidated = true;

                String username = inputUsername.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

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

                if (isValidated) {
                    Customer xCustomer = new Customer();
                    xCustomer.setUsername(username);
                    xCustomer.setPassword(password);

                    progressBarProfile.setVisibility(View.VISIBLE);
                    customer_api.getAllCustomers().enqueue(new Callback<List<Customer>>() {
                        @Override
                        public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                            if (response.isSuccessful()) {
                                List<Customer> allCustomers = response.body();
                                boolean found = false;

                                progressBarProfile.setVisibility(View.GONE);

                                for (Customer yCustomer : allCustomers) {
                                    try {
                                        if (xCustomer.getUsername().equals(yCustomer.getUsername())
                                                && xCustomer.getPassword().equals(yCustomer.getPassword())) {
                                            found = true;

                                            progressBarProfile.setVisibility(View.GONE);
                                            sessionManager.setLogin(true, yCustomer.getId());

                                            Intent intent = new Intent(getApplicationContext(), home.class);
                                            intent.putExtra("customer_id", yCustomer.getId());
                                            startActivity(intent);
                                            break; // Exit loop once user is found
                                        }
                                    } catch (Exception e) {
                                        Toast.makeText(login.this, "An error occurred", Toast.LENGTH_SHORT).show();

                                        progressBarProfile.setVisibility(View.VISIBLE);

                                        Log.e("LoginActivity", "Error during login", e);
                                    }
                                }

                                if (!found) {
                                    // No matching user found
                                    Toast.makeText(login.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                // Request not successful, handle accordingly
                                Toast.makeText(login.this, "Failed to retrieve customer data", Toast.LENGTH_SHORT).show();
                                Log.e("LoginActivity", "Failed to retrieve customer data: " + response.message());
                                progressBarProfile.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Customer>> call, Throwable throwable) {
                            progressBarProfile.setVisibility(View.GONE);
                            Toast.makeText(login.this, "NETWORK ERROR", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), register.class);
                startActivity(intent);
            }
        });
    }
}