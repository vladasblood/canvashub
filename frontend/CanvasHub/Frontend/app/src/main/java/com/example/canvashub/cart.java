package com.example.canvashub;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.canvashub.api.storeApi;
import com.example.canvashub.api.cartApi;
import com.example.canvashub.api.cartItemApi;
import com.example.canvashub.model.Store;
import com.example.canvashub.model.Cart;
import com.example.canvashub.model.CartItem;
import com.example.canvashub.retrofit.retrofitService;
import com.example.canvashub.session.SessionManager;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class cart extends AppCompatActivity {
    Activity activity;

    TabLayout tabLayout;

    private SessionManager sessionManager;
    ArrayList<Store> allStoresArrayList = new ArrayList<>();
    ArrayList<CartItem> allCartItemsArrayList = new ArrayList<>();
    int selectedPosition;
    retrofitService retrofitService = new retrofitService();
    storeApi storeApi = retrofitService.getRetrofit().create(storeApi.class);
    cartApi cartApi = retrofitService.getRetrofit().create(cartApi.class);
    cartItemApi cartItemApi = retrofitService.getRetrofit().create(cartItemApi.class);
    Cart cartSession = new Cart();

    ProgressBar loadingProgressBar; // Declare the ProgressBar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);
        activity = this;

        loadingProgressBar = findViewById(R.id.loadingProgressBar);

        sessionManager = new SessionManager(this);

        ListView storesListView = findViewById(R.id.storesListView);

        tabLayout = findViewById(R.id.tabLayout);

        tabLayout.getTabAt(2).select();

        storesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        loadingProgressBar.setVisibility(View.VISIBLE);

        cartApi.getAllCarts().enqueue(new Callback<List<Cart>>() {
            @Override
            public void onResponse(Call<List<Cart>> call, Response<List<Cart>> response) {

                for(Cart xCart:response.body()) {
                    if (xCart.getCustomer_id().getId().equals(sessionManager.getCustomerId())) {
                        cartSession = xCart;
                        cartItemApi.getAllCartItems().enqueue(new Callback<List<CartItem>>() {
                            @Override
                            public void onResponse(Call<List<CartItem>> call, Response<List<CartItem>> response) {

                                loadingProgressBar.setVisibility(View.GONE);

                                for (CartItem xCartItem:response.body()) {
                                    if (xCartItem.getCart_Id().getId().equals(cartSession.getId())) {
                                        allCartItemsArrayList.add(xCartItem);
                                        if (!allStoresArrayList.contains(xCartItem.getStore_Id())){
                                            allStoresArrayList.add(xCartItem.getStore_Id());
                                        }
                                    }
                                }

                                if(allCartItemsArrayList.isEmpty()){
                                    //finish();
                                    Toast.makeText(getBaseContext(), "CART IS EMPTY", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(cart.this, home.class));
                                    finish();
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {

                                            StoreAdapter storeAdapter = new StoreAdapter(cart.this, allStoresArrayList, allCartItemsArrayList);
                                            storesListView.setAdapter(storeAdapter);
                                            storeAdapter.notifyDataSetChanged();

                                        }
                                    });
                                }
                            }
                            @Override
                            public void onFailure(Call<List<CartItem>> call, Throwable throwable) {

                                loadingProgressBar.setVisibility(View.GONE);

                                Toast.makeText(getApplicationContext(), "Outside", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Cart>> call, Throwable throwable) {

                Toast.makeText(cart.this, "Outside2", Toast.LENGTH_SHORT).show();
                loadingProgressBar.setVisibility(View.GONE);
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
                        break;
                    case 3:
                        startActivity(new Intent(getBaseContext(), payment.class));
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