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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class password_confirmation extends AppCompatActivity {

    EditText firstPasswordInput;
    EditText secondPasswordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_confirmation);

        firstPasswordInput = (EditText) findViewById(R.id.firstPasswordInput);
        secondPasswordInput = (EditText) findViewById(R.id.secondPasswordInput);
        Button submitButton = (Button) findViewById(R.id.changePasswordSubmitButton);

        Intent intent = getIntent();
        retrofitService retrofitService = new retrofitService();
        customerApi customerApi = retrofitService.getRetrofit().create(customerApi.class);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enter = firstPasswordInput.getText().toString().trim();
                String reenter = secondPasswordInput.getText().toString().trim();

                if (!validatePassword(enter, reenter, firstPasswordInput, secondPasswordInput)) {
                    return;
                }

                if (enter.trim().equals(reenter.trim()) && !enter.isEmpty()) {
                    String customerId = intent.getStringExtra("customerIdAsIntentForPasswordReset");
                    customerApi.getCustomer(customerId).enqueue(new Callback<Customer>() {
                        @Override
                        public void onResponse(Call<Customer> call, Response<Customer> response) {
                            Customer tempCustomer = response.body();
                            assert tempCustomer != null;
                            tempCustomer.setPassword(enter.trim());
                            customerApi.updateCustomer(tempCustomer).enqueue(new Callback<Customer>() {
                                @Override
                                public void onResponse(Call<Customer> call, Response<Customer> response) {
                                    Toast.makeText(password_confirmation.this, "Password Updated.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(password_confirmation.this, login.class);
                                    startActivity(intent);
                                }

                                @Override
                                public void onFailure(Call<Customer> call, Throwable throwable) {
                                    Toast.makeText(password_confirmation.this, "Password not updated.", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void onFailure(Call<Customer> call, Throwable throwable) {

                        }
                    });
                } else {
                    Toast.makeText(password_confirmation.this, "Make sure the two password inputs are correct.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validatePassword(String password, String confirmPassword, EditText passwordInput, EditText confirmPasswordInput) {

        if (password.isEmpty()) {
            showError(passwordInput, "Password is required");
            return false;
        } else if (!password.equals(confirmPassword)) {
            showError(confirmPasswordInput, "Passwords do not match");
            return false;
        } else if (password.length() < 6) {
            showError(passwordInput, "Password must be at least 6 characters long");
            return false;
        }

        return true;
    }

    private void showError(EditText editText, String errorMessage) {
        editText.setError(errorMessage);
        editText.requestFocus();
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

}