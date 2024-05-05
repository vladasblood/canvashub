package com.example.canvashub;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.canvashub.api.productApi;
import com.example.canvashub.model.Product;
import com.example.canvashub.retrofit.retrofitService;
import com.example.canvashub.session.SessionManager;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class search extends AppCompatActivity {
    private SessionManager sessionManager;
    ArrayList<Product> allProductsArrayList = new ArrayList<>();
    ArrayList<Product> currentQueriedProductsArrayList = new ArrayList<>();
    int selectedPosition;
    retrofitService retrofitService = new retrofitService();
    productApi productApi = retrofitService.getRetrofit().create(productApi.class);

    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        sessionManager = new SessionManager(this);

        // Find ProgressBar
        ProgressBar loadingProgressBar = findViewById(R.id.loadingProgressBar);

        ListView productsListView = findViewById(R.id.productQueryListView);
        EditText searchInput = findViewById(R.id.searchInput);

        productsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedPosition = position;
                productsListView.setItemChecked(position, true);

                // Determine the list to retrieve the clicked product from
                ArrayList<Product> productList = currentQueriedProductsArrayList.isEmpty() ? allProductsArrayList : currentQueriedProductsArrayList;

                Intent intent = new Intent(search.this, product_view.class);
                intent.putExtra("product_id_intent", String.valueOf(productList.get(selectedPosition).getId()));
                intent.putExtra("product_name_intent", String.valueOf(productList.get(selectedPosition).getProduct_name()));
                intent.putExtra("product_description_intent", String.valueOf(productList.get(selectedPosition).getDescription()));
                intent.putExtra("product_image_intent", String.valueOf(productList.get(selectedPosition).getProduct_image_url()));

                startActivity(intent);
            }
        });

        loadingProgressBar.setVisibility(View.VISIBLE);

        productApi.getAllProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {

                for (Product xProduct : response.body()) {
                    allProductsArrayList.add(xProduct);

                    if(allProductsArrayList.isEmpty()){
                        // Hide ProgressBar when response is received
                        loadingProgressBar.setVisibility(View.VISIBLE);
                    } else {
                        loadingProgressBar.setVisibility(View.GONE);
                    }
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ProductAdapter productAdapter = new ProductAdapter(search.this, allProductsArrayList);
                        productsListView.setAdapter(productAdapter);
                        productAdapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable throwable) {

                // Hide ProgressBar when response is received
                loadingProgressBar.setVisibility(View.VISIBLE);

            }
        });

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                currentQueriedProductsArrayList.clear();
                String currentStringQuery = searchInput.getText().toString().toLowerCase();
                for (Product xProduct:allProductsArrayList) {
                    if (xProduct.getProduct_name().toLowerCase().contains(currentStringQuery)) {
                        currentQueriedProductsArrayList.add(xProduct);

                    }
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ProductAdapter productAdapter = new ProductAdapter(search.this, currentQueriedProductsArrayList);
                        productsListView.setAdapter(productAdapter);
                        productAdapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.getTabAt(1).select();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        startActivity(new Intent(getBaseContext(), home.class));
                        break;
                    case 1:
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