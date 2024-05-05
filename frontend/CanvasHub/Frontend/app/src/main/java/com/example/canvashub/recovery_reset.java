package com.example.canvashub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.canvashub.api.customerApi;
import com.example.canvashub.model.Customer;
import com.example.canvashub.retrofit.retrofitService;

import java.sql.Timestamp;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class recovery_reset extends AppCompatActivity {

    // Constant for waiting period in milliseconds (24 hours)
    //private static final long WAITING_PERIOD = 24 * 60 * 60 * 1000; // 24 hours in milliseconds
    private static final long WAITING_PERIOD = 15 * 60 * 1000; // 15 minutes in milliseconds
    EditText inputOtp;
    Button confirmToken;
    // Variable to track last failed attempt time
    private long lastFailedAttemptTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recovery_reset);

        inputOtp = findViewById(R.id.input_otp);
        confirmToken = findViewById(R.id.btn_confirm);

        Intent intent = getIntent();
        retrofitService retrofitService = new retrofitService();
        customerApi customerApi = retrofitService.getRetrofit().create(customerApi.class);

        confirmToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                customerApi.getCustomer(intent.getStringExtra("customerIdAsIntent")).enqueue(new Callback<Customer>() {
                    @Override
                    public void onResponse(Call<Customer> call, Response<Customer> response) {
                        Customer tempCustomer = response.body();

                        if (tempCustomer.getTries() >= 3 && tempCustomer.getTries_timestamp() != 0) {
                            long elapsedTimeInMillis = System.currentTimeMillis() - tempCustomer.getTries_timestamp();
                            long remainingTimeInMillis = (15 * 60 * 1000 - elapsedTimeInMillis); // Remaining time in milliseconds

                            if (remainingTimeInMillis > 0) {
                                long remainingMinutes = remainingTimeInMillis / (60 * 1000);
                                long remainingSeconds = (remainingTimeInMillis % (60 * 1000)) / 1000; // Remaining seconds
                                Toast.makeText(recovery_reset.this, "You have exceeded the allowed OTP tries. Please wait for " + remainingMinutes + " minutes and " + remainingSeconds + " seconds or contact an administrator.", Toast.LENGTH_SHORT).show();
                            } else {
                                // Handle the case when the waiting period is over
                                tempCustomer.setTries_timestamp(0);
                                tempCustomer.setTries(0);
                                customerApi.updateCustomer(tempCustomer).enqueue(new Callback<Customer>() {
                                    @Override
                                    public void onResponse(Call<Customer> call, Response<Customer> response) {
                                        //Take OTP typed if there are more tries
                                        String tokenInput = inputOtp.getText().toString().trim();

                                        if (!validateOtp(tokenInput)) {
                                            return;
                                        }

                                        if (Objects.equals(tempCustomer.getToken().toString().trim(), tokenInput.toString().trim())) {
                                            Toast.makeText(recovery_reset.this, "Correct OTP", Toast.LENGTH_SHORT).show();

                                            Intent passwordResetIntent = new Intent(recovery_reset.this, password_confirmation.class);
                                            passwordResetIntent.putExtra("customerIdAsIntentForPasswordReset", String.valueOf(intent.getStringExtra("customerIdAsIntent")));
                                            startActivity(passwordResetIntent);
                                        } else {
                                            Toast.makeText(recovery_reset.this, "Wrong OTP. Please try again.", Toast.LENGTH_SHORT).show();
                                            tempCustomer.setTries(tempCustomer.getTries() + 1);
                                            customerApi.updateCustomer(tempCustomer).enqueue(new Callback<Customer>() {
                                                @Override
                                                public void onResponse(Call<Customer> call, Response<Customer> response) {

                                                }

                                                @Override
                                                public void onFailure(Call<Customer> call, Throwable throwable) {
                                                    Toast.makeText(recovery_reset.this, "Failed to Update Tries.", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Customer> call, Throwable throwable) {

                                    }
                                });
                            }
                        } else {
                            //Take OTP typed if there are more tries
                            String tokenInput = inputOtp.getText().toString().trim();

                            if (!validateOtp(tokenInput)) {
                                return;
                            }

                            if (Objects.equals(tempCustomer.getToken().toString().trim(), tokenInput.toString().trim())) {
                                Toast.makeText(recovery_reset.this, "Correct OTP", Toast.LENGTH_SHORT).show();

                                Intent passwordResetIntent = new Intent(recovery_reset.this, password_confirmation.class);
                                passwordResetIntent.putExtra("customerIdAsIntentForPasswordReset", String.valueOf(intent.getStringExtra("customerIdAsIntent")));
                                startActivity(passwordResetIntent);
                            } else {
                                Toast.makeText(recovery_reset.this, "Wrong OTP. Please try again.", Toast.LENGTH_SHORT).show();
                                if (tempCustomer.getTries() + 1 > 2) {
                                    tempCustomer.setTries_timestamp(System.currentTimeMillis());
                                    Toast.makeText(recovery_reset.this, String.valueOf(tempCustomer.getTries_timestamp()), Toast.LENGTH_SHORT).show();
                                }
                                tempCustomer.setTries(tempCustomer.getTries() + 1);
                                customerApi.updateCustomer(tempCustomer).enqueue(new Callback<Customer>() {
                                    @Override
                                    public void onResponse(Call<Customer> call, Response<Customer> response) {

                                    }

                                    @Override
                                    public void onFailure(Call<Customer> call, Throwable throwable) {
                                        Toast.makeText(recovery_reset.this, "Failed to Update Tries.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Customer> call, Throwable throwable) {
                        Toast.makeText(recovery_reset.this, "Failed to get customer info for OTP.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private boolean validateOtp(String otp) {
        if (otp.isEmpty()) {
            showError("OTP IS EMPTY");
            inputOtp.setError("EMPTY FIELD");
            inputOtp.requestFocus();
            return false;
        }
        if (!otp.matches("\\d{6}")) {
            showError("Invalid OTP format. OTP should be a 6-digit number.");
            inputOtp.setError("INVALID OTP FORMAT");
            inputOtp.requestFocus();
            return false;
        }
        return true;
    }

    private void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
