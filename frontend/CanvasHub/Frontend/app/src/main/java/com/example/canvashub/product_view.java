package com.example.canvashub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.canvashub.api.priceApi;
import com.example.canvashub.api.productApi;
import com.example.canvashub.model.Price;
import com.example.canvashub.retrofit.retrofitService;
import com.example.canvashub.session.SessionManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class product_view extends AppCompatActivity {
    ArrayList<Price> allPricesArrayList = new ArrayList<>();
//    ArrayList<Customer> allCustomersArrayList = new ArrayList<>();
//
//    ArrayList<Cart> allCartsArrayList = new ArrayList<>();
//
//    ArrayList<CartItem> allCartItemsArrayList = new ArrayList<>();

    ImageButton backButton;

    private SessionManager sessionManager;
    int selectedPosition;
    retrofitService retrofitService = new retrofitService();
    productApi productApi = retrofitService.getRetrofit().create(productApi.class);
    priceApi priceApi = retrofitService.getRetrofit().create(priceApi.class);

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);

        // Initialize other views and variables
        progressBar = findViewById(R.id.progressBar);

        // Show the progress bar when data loading starts
        progressBar.setVisibility(View.VISIBLE);

        Intent intent = getIntent();
        String product_id_intent = intent.getStringExtra("product_id_intent");
        String product_name_intent = intent.getStringExtra("product_name_intent");
        String product_description_intent = intent.getStringExtra("product_description_intent");
        String product_image_intent = intent.getStringExtra("product_image_intent");

        sessionManager = new SessionManager(this);

        ListView pricesListView = findViewById(R.id.pricesListView);
        TextView productName = findViewById(R.id.productNameTextView);
        TextView productDescription = findViewById(R.id.productDescriptionTextView);
        ImageView productImageView = findViewById(R.id.productImageView);
        backButton = findViewById(R.id.backButton);

        productName.setText(product_name_intent);
        productDescription.setText(product_description_intent);
        //Picasso.get().load(pricesArrayList.get(position).getStore_Id().getStore_image_url()).resize(50,50).centerCrop().into(productImageView);
        Picasso.get().load(product_image_intent).resize(250,250).centerCrop().into(productImageView);

        pricesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedPosition = position;
                pricesListView.setItemChecked(position, true);
            }
        });

        priceApi.getAllPrices().enqueue(new Callback<List<Price>>() {
            @Override
            public void onResponse(Call<List<Price>> call, Response<List<Price>> response) {

                // Hide the progress bar when data loading is complete
                progressBar.setVisibility(View.GONE);

                for (Price xPrice : response.body()) {
                    if (xPrice.getProduct_Id().getId().equals(product_id_intent)) {
                        allPricesArrayList.add(xPrice);
                    }
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        PriceAdapter priceAdapter = new PriceAdapter(product_view.this, allPricesArrayList, sessionManager.getCustomerId());
                        pricesListView.setAdapter(priceAdapter);
                        priceAdapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Price>> call, Throwable throwable) {

                // Hide the progress bar when data loading is complete
                progressBar.setVisibility(View.GONE);

            }
        });

        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(get, category.class));
                finish();

            }
        });
    }
}