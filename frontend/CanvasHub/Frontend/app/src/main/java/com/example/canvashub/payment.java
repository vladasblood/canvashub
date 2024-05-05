package com.example.canvashub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.canvashub.api.cartItemApi;
import com.example.canvashub.model.CartItem;
import com.example.canvashub.model.Store;
import com.example.canvashub.retrofit.retrofitService;
import com.example.canvashub.session.SessionManager;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class payment extends AppCompatActivity {
    private SessionManager sessionManager;
    ArrayList<CartItem> allCartItemsArrayList = new ArrayList<>();
    ArrayList<Store> allStoresArrayList = new ArrayList<>();
    ArrayList< ArrayList<String> > allStorePricesPerArrayList = new ArrayList<>();
    retrofitService retrofitService = new retrofitService();
    cartItemApi cartItemApi = retrofitService.getRetrofit().create(cartItemApi.class);

    private ProgressBar loadingProgressBar; // Declare ProgressBar

    TabLayout  tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment);

        sessionManager = new SessionManager(this);
        loadingProgressBar = findViewById(R.id.loadingProgressBar); // Initialize ProgressBar

        ListView paymentTotalPerStoreListView = findViewById(R.id.paymentTotalPerStoreListView);

        allStorePricesPerArrayList.add(new ArrayList<String>());
        allStorePricesPerArrayList.add(new ArrayList<String>());

        // Show ProgressBar before making API call
        loadingProgressBar.setVisibility(View.VISIBLE);

        cartItemApi.getAllCartItems().enqueue(new Callback<List<CartItem>>() {
            @Override
            public void onResponse(Call<List<CartItem>> call, Response<List<CartItem>> response) {
                // Hide ProgressBar after receiving response
                loadingProgressBar.setVisibility(View.GONE);

                ArrayList testArrayList = new ArrayList<>();
                Map<String, Double> storeTotals = new HashMap<>();
                List<CartItem> cartItems = response.body();

                for (CartItem cartItem : cartItems) {
                    if (cartItem.getCart_Id().getCustomer_id().getId().equals(sessionManager.getCustomerId())) {
                        Store store = cartItem.getStore_Id();
                        String storeName = store.getStore_name();
                        double totalPrice = Double.parseDouble(cartItem.getQuantity()) * cartItem.getPrice().getCost();
                        storeTotals.put(storeName, storeTotals.getOrDefault(storeName, 0.0) + totalPrice);
                        testArrayList.add(cartItem);
                    }
                }

                if (testArrayList.isEmpty()) {
                    Toast.makeText(getBaseContext(), "CART IS EMPTY", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(payment.this, home.class));
                    finish();
                }

                // Convert store totals to strings
                List<String> storeNames = new ArrayList<>();
                List<String> storeTotalStrings = new ArrayList<>();
                for (Map.Entry<String, Double> entry : storeTotals.entrySet()) {
                    storeNames.add(entry.getKey());
                    storeTotalStrings.add(String.format("%.2f", entry.getValue())); // Format total price to two decimal places
                }

                runOnUiThread(() -> {
                    PaymentStoreAdapter paymentStoreAdapter = new PaymentStoreAdapter(payment.this, storeNames, storeTotalStrings);
                    paymentTotalPerStoreListView.setAdapter(paymentStoreAdapter);
                    paymentStoreAdapter.notifyDataSetChanged();
                });
            }


            @Override
            public void onFailure(Call<List<CartItem>> call, Throwable throwable) {
                // Hide ProgressBar if API call fails
                loadingProgressBar.setVisibility(View.GONE);
                Toast.makeText(getBaseContext(), "CHECK YOUR NETWORK!", Toast.LENGTH_SHORT).show();
            }
        });



        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.getTabAt(3).select();

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
                        break;
                    case 4:
                        startActivity(new Intent(getBaseContext(), profile.class));
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