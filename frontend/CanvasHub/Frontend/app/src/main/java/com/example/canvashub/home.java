package com.example.canvashub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.canvashub.api.cartApi;
import com.example.canvashub.api.categoryApi;
import com.example.canvashub.api.customerApi;
import com.example.canvashub.model.Cart;
import com.example.canvashub.model.Category;
import com.example.canvashub.model.Customer;
import com.example.canvashub.retrofit.retrofitService;
import com.example.canvashub.session.SessionManager;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class home extends AppCompatActivity {
    //For ListView
    List<Category> allCategories;
    ArrayList<Category> allCategoriesArrayList = new ArrayList<>();
    int selectedPosition;
    retrofitService retrofitService = new retrofitService();
    categoryApi categoryApi = retrofitService.getRetrofit().create(categoryApi.class);

    //Creates a Cart
    customerApi customerApi = retrofitService.getRetrofit().create(customerApi.class);
    cartApi cartApi = retrofitService.getRetrofit().create(cartApi.class);

    //For Navigation Bar
    TabLayout tabLayout;

    private SessionManager sessionManager;

    // Declare ProgressBar
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        // Initialize ProgressBar
        progressBar = findViewById(R.id.progressBar);
        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.getTabAt(0).select();

        ListView categoryListView = findViewById(R.id.categoryListView);

        sessionManager = new SessionManager(this);

        categoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedPosition = position;
                categoryListView.setItemChecked(position, true);
                Intent intent = new Intent(home.this, category.class);
                intent.putExtra("category_id_intent", allCategoriesArrayList.get(selectedPosition).getId());
//                intent.putExtra("customer_id", customer_id); //Session of Customer ID
                startActivity(intent);
            }
        });

        //Create Cart Api for Customers
        customerApi.getCustomer(sessionManager.getCustomerId()).enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {

                Cart xCart = new Cart();
                xCart.setId("CART" + sessionManager.getCustomerId());
                xCart.setCustomer_id(response.body());

                cartApi.addCart(xCart).enqueue(new Callback<Cart>() {
                    @Override
                    public void onResponse(Call<Cart> call, Response<Cart> response) {

                    }

                    @Override
                    public void onFailure(Call<Cart> call, Throwable throwable) {
                        Toast.makeText(getBaseContext(), "NETWORK ERROR", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable throwable) {
                Toast.makeText(getBaseContext(), "NETWORK ERROR", Toast.LENGTH_SHORT).show();
            }
        });

        // Hide ProgressBar when data is loaded
        progressBar.setVisibility(View.VISIBLE);


        categoryApi.getAllCategories().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {

                // Hide ProgressBar when data is loaded
                progressBar.setVisibility(View.GONE);

                allCategories = response.body();
                allCategoriesArrayList.addAll(allCategories);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        CategoryAdapter categoryAdapter = new CategoryAdapter(getApplicationContext(), allCategoriesArrayList);
                        categoryListView.setAdapter(categoryAdapter);
                        categoryAdapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable throwable) {
                // Hide ProgressBar when data is loaded
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getBaseContext(), "NETWORK ERROR", Toast.LENGTH_SHORT).show();
            }
        });


        // Setting the Tab Selected Listener
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
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
