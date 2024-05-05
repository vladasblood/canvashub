package com.example.canvashub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.canvashub.api.productApi;
import com.example.canvashub.model.Product;
import com.example.canvashub.retrofit.retrofitService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class category extends AppCompatActivity {
    ArrayList<Product> allProductsArrayList = new ArrayList<>();
    int selectedPosition;
    retrofitService retrofitService = new retrofitService();
    productApi productApi = retrofitService.getRetrofit().create(productApi.class);

    ImageButton imageButton;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category);

        Intent intent = getIntent();
        String category_id_intent = intent.getStringExtra("category_id_intent");

        ListView productsListView = findViewById(R.id.productsListView);
        imageButton = findViewById(R.id.imageButton);
        progressBar = findViewById(R.id.progressBar);

        productsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedPosition = position;
                productsListView.setItemChecked(position, true);
                Intent intent = new Intent(category.this, product_view.class);
                intent.putExtra("product_id_intent", String.valueOf(allProductsArrayList.get(selectedPosition).getId()));
                intent.putExtra("product_name_intent", String.valueOf(allProductsArrayList.get(selectedPosition).getProduct_name()));
                intent.putExtra("product_description_intent", String.valueOf(allProductsArrayList.get(selectedPosition).getDescription()));
                intent.putExtra("product_image_intent", String.valueOf(allProductsArrayList.get(selectedPosition).getProduct_image_url()));
                startActivity(intent);
            }
        });

        progressBar.setVisibility(View.VISIBLE);

        productApi.getAllProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {

                // Hide loading indicator
                progressBar.setVisibility(View.GONE);

                for (Product xProduct : response.body()) {
                    if (xProduct.getCategory_Id().getId().equals(category_id_intent)) {
                        allProductsArrayList.add(xProduct);
                    }
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ProductAdapter productAdapter = new ProductAdapter(category.this, allProductsArrayList);
                        productsListView.setAdapter(productAdapter);
                        productAdapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable throwable) {

                // Hide loading indicator
                progressBar.setVisibility(View.GONE);

            }
        });
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}