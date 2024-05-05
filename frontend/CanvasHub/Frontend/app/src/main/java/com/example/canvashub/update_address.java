package com.example.canvashub;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.canvashub.api.addressApi;
import com.example.canvashub.api.customerApi;
import com.example.canvashub.model.Address;
import com.example.canvashub.model.Customer;
import com.example.canvashub.retrofit.retrofitService;
import com.example.canvashub.session.SessionManager;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class update_address extends AppCompatActivity {

    private ProgressBar progressBarUpdateAddress;
    TabLayout tabLayout;
    private boolean isEditingEnabled = false;

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_address);

        retrofitService retrofitService = new retrofitService();
        customerApi customerApi = retrofitService.getRetrofit().create(customerApi.class);
        addressApi addressApi = retrofitService.getRetrofit().create(addressApi.class);

        progressBarUpdateAddress = findViewById(R.id.progressBarUpdateAddress);

        EditText inputBuilding = (EditText) findViewById(R.id.address_building);
        EditText inputStreet = (EditText) findViewById(R.id.address_street);
        EditText inputDistrict = (EditText) findViewById(R.id.address_district);
        EditText inputCity = (EditText) findViewById(R.id.address_city);
        EditText inputProvince = (EditText) findViewById(R.id.address_province);
        EditText inputZipCode = (EditText) findViewById(R.id.address_zipcode);
        EditText inputCountry = (EditText) findViewById(R.id.address_country);

        Button buttonEnableEdit = (Button) findViewById(R.id.enable_edit_address);
        Button buttonSaveAddress = (Button) findViewById(R.id.save_edit_address);

        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.getTabAt(4).select();

        sessionManager = new SessionManager(this);

        //
        buttonEnableEdit.setText(R.string.enable_edit_button_text);

        buttonEnableEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEditingEnabled) {
                    // Disable editing
                    inputBuilding.setEnabled(false);
                    inputStreet.setEnabled(false);
                    inputDistrict.setEnabled(false);
                    inputCity.setEnabled(false);
                    inputProvince.setEnabled(false);
                    inputZipCode.setEnabled(false);
                    inputCountry.setEnabled(false);
                    isEditingEnabled = false;
                    buttonEnableEdit.setText(R.string.enable_edit_button_text);
                } else {
                    // Enable editing
                    inputBuilding.setEnabled(true);
                    inputStreet.setEnabled(true);
                    inputDistrict.setEnabled(true);
                    inputCity.setEnabled(true);
                    inputProvince.setEnabled(true);
                    inputZipCode.setEnabled(true);
                    inputCountry.setEnabled(true);
                    isEditingEnabled = true;
                    buttonEnableEdit.setText(R.string.disable_edit_button_text);
                }
            }
        });

        buttonSaveAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isValidated = true;

                if((inputBuilding.getText().toString().trim()).isEmpty()){
                    inputBuilding.setError("INPUT IS REQUIRED");
                    inputBuilding.requestFocus();
                    isValidated = false;
                }
                if((inputStreet.getText().toString().trim()).isEmpty()){
                    inputStreet.setError("INPUT IS REQUIRED");
                    inputStreet.requestFocus();
                    isValidated = false;
                }
                if((inputDistrict.getText().toString().trim()).isEmpty()){
                    inputDistrict.setError("INPUT IS REQUIRED");
                    inputDistrict.requestFocus();
                    isValidated = false;
                }
                if((inputCity.getText().toString().trim()).isEmpty()){
                    inputCity.setError("INPUT IS REQUIRED");
                    inputCity.requestFocus();
                    isValidated = false;
                }
                if((inputProvince.getText().toString().trim()).isEmpty()){
                    inputProvince.setError("INPUT IS REQUIRED");
                    inputProvince.requestFocus();
                    isValidated = false;
                }
                if((inputZipCode.getText().toString().trim()).isEmpty()){
                    inputZipCode.setError("INPUT IS REQUIRED");
                    inputZipCode.requestFocus();
                    isValidated = false;
                }
                if((inputCountry.getText().toString().trim()).isEmpty()){
                    inputCountry.setError("INPUT IS REQUIRED");
                    inputCountry.requestFocus();
                    isValidated = false;
                }
                if(isValidated){

                    progressBarUpdateAddress.setVisibility(View.VISIBLE);

                    customerApi.getCustomer(sessionManager.getCustomerId()).enqueue(new Callback<Customer>() {
                        @Override
                        public void onResponse(Call<Customer> call, Response<Customer> response) {

                            Customer getCustomer = response.body();

                            assert getCustomer != null;
                            addressApi.getAddress(getCustomer.getAddress_Id().getId()).enqueue(new Callback<Address>() {
                                @Override
                                public void onResponse(Call<Address> call, Response<Address> response) {

                                    Address getAddress = getCustomer.getAddress_Id();

                                    //Check if ID is 1
                                    if(getAddress != null && getAddress.getId().equals("1".trim())) {

                                        progressBarUpdateAddress.setVisibility(View.VISIBLE);

                                        //Create new Address
                                        Address newAddress = new Address();
                                        newAddress.setId("CID"+getCustomer.getId());
                                        newAddress.setBuilding(inputBuilding.getText().toString().trim());
                                        newAddress.setStreet(inputStreet.getText().toString().trim());
                                        newAddress.setDistrict(inputDistrict.getText().toString().trim());
                                        newAddress.setCity(inputCity.getText().toString().trim());
                                        newAddress.setProvince(inputProvince.getText().toString().trim());
                                        newAddress.setZip_code(inputZipCode.getText().toString().trim());
                                        newAddress.setCountry(inputCountry.getText().toString().trim());

                                        getCustomer.setAddress_Id(newAddress);

                                        addressApi.updateAddress(newAddress).enqueue(new Callback<Address>() {
                                            @Override
                                            public void onResponse(Call<Address> call, Response<Address> response) {

                                                customerApi.updateCustomer(getCustomer).enqueue(new Callback<Customer>() {
                                                    @Override
                                                    public void onResponse(Call<Customer> call, Response<Customer> response) {
                                                        progressBarUpdateAddress.setVisibility(View.GONE);
                                                        Toast.makeText(update_address.this, "Address Updated Successfully", Toast.LENGTH_SHORT).show();
                                                        finish();
                                                    }

                                                    @Override
                                                    public void onFailure(Call<Customer> call, Throwable throwable) {
                                                        progressBarUpdateAddress.setVisibility(View.GONE);
                                                    }
                                                });
                                            }
                                            @Override
                                            public void onFailure(Call<Address> call, Throwable throwable) {
                                                progressBarUpdateAddress.setVisibility(View.GONE);
                                            }
                                        });


                                    } else {

                                        progressBarUpdateAddress.setVisibility(View.VISIBLE);

                                        //Existing Address, update it
                                        if(getAddress != null){
                                            getAddress.setBuilding(inputBuilding.getText().toString().trim());
                                            getAddress.setStreet(inputStreet.getText().toString().trim());
                                            getAddress.setDistrict(inputDistrict.getText().toString().trim());
                                            getAddress.setCity(inputCity.getText().toString().trim());
                                            getAddress.setProvince(inputProvince.getText().toString().trim());
                                            getAddress.setZip_code(inputZipCode.getText().toString().trim());
                                            getAddress.setCountry(inputCountry.getText().toString().trim());
                                        }

                                        assert response.body() != null;
                                        addressApi.updateAddress(getAddress).enqueue(new Callback<Address>() {
                                            @Override
                                            public void onResponse(Call<Address> call, Response<Address> response) {
                                                progressBarUpdateAddress.setVisibility(View.GONE);
                                                Toast.makeText(update_address.this, "Address Updated Successfully", Toast.LENGTH_SHORT).show();
                                                finish();
                                            }
                                            @Override
                                            public void onFailure(Call<Address> call, Throwable throwable) {
                                                progressBarUpdateAddress.setVisibility(View.GONE);
                                                Toast.makeText(update_address.this, "Address Update Failed", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                }
                                @Override
                                public void onFailure(Call<Address> call, Throwable throwable) {

                                }
                            });
                        }
                        @Override
                        public void onFailure(Call<Customer> call, Throwable throwable) {

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
    }
}
