package com.example.canvashub;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.canvashub.api.customerApi;
import com.example.canvashub.model.Customer;
import com.example.canvashub.retrofit.retrofitService;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class recovery extends AppCompatActivity {

    Button btnSend;
    EditText inputEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recovery);

        btnSend = findViewById(R.id.button3);
        inputEmail = findViewById(R.id.input_Email);

        retrofitService retrofitService = new retrofitService();
        customerApi customerApi = retrofitService.getRetrofit().create(customerApi.class);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString().trim();

                if (!validateEmail(email)) {
                    return;
                }

                customerApi.getAllCustomers().enqueue(new Callback<List<Customer>>() {
                    @Override
                    public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                        List<Customer> allCustomers = response.body();
                        boolean customerExists = false;

                        for (Customer xCustomer : allCustomers) {
                            if (email.equals(xCustomer.getEmail_address())) {
                                sendEmail(email, xCustomer, customerApi);
                                customerExists = true;
                                break;
                            }
                        }

                        if (!customerExists) {
                            Toast.makeText(recovery.this, "Customer not found", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Customer>> call, Throwable t) {
                        Toast.makeText(recovery.this, "Failed to fetch customers", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private boolean validateEmail(String email) {
        if (email.isEmpty()) {
            showError("Email is required");
            inputEmail.setError("EMAIL IS REQUIRED");
            inputEmail.requestFocus();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showError("Enter a valid email");
            inputEmail.setError("ENTER VALID EMAIL");
            inputEmail.requestFocus();
            return false;
        }

        return true;
    }

    private void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void sendEmail(String email, Customer customer, customerApi customerApi) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final String senderEmail = "sender-email";
                    final String senderPassword = "sender-password";
                    final String receiverEmail = email;

                    String host = "smtp.gmail.com";

                    Properties properties = System.getProperties();
                    properties.put("mail.smtp.host", host);
                    properties.put("mail.smtp.port", "587");
                    properties.put("mail.smtp.starttls.enable", "true");
                    properties.put("mail.smtp.auth", "true");

                    Session session = Session.getInstance(properties, new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(senderEmail, senderPassword);
                        }
                    });

                    MimeMessage mimeMessage = new MimeMessage(session);
                    mimeMessage.setFrom(new InternetAddress(senderEmail));
                    mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(receiverEmail));

                    mimeMessage.setSubject("CanvasHub Password Reset OTP");
                    String otp = new DecimalFormat("000000").format(new Random().nextInt(999999));
                    // Email body
                    String emailBody = "Dear CanvasHub User,\n\n"
                            + "You have recently requested to reset your password for your CanvasHub account. "
                            + "Please use the following One-Time Password (OTP) to complete the password reset process:\n\n"
                            + "OTP: " + otp + "\n\n"
                            + "If you did not initiate this password reset request, please disregard this email. "
                            + "Your account's password will remain unchanged.\n\n"
                            + "Thank you for using CanvasHub!\n\n"
                            + "Best regards,\n"
                            + "The CanvasHub Team";

                    mimeMessage.setText(emailBody);

                    Transport.send(mimeMessage);

                    updateCustomerToken(customer, otp, customerApi);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(recovery.this, "Email sent successfully!", Toast.LENGTH_SHORT).show();
                        }
                    });

                } catch (MessagingException e) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(recovery.this, "Failed to send email. Please try again later.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }

    private void updateCustomerToken(Customer customer, String otp, customerApi customerApi) {
        Customer token;
        token = customer;
        token.setToken(otp);

        customerApi.updateCustomer(token).enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                Toast.makeText(recovery.this, "OTP sent to your email.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(recovery.this, recovery_reset.class);
                intent.putExtra("customerIdAsIntent", token.getId());
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable throwable) {
                Toast.makeText(recovery.this, "Failed to generate and send OTP.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
