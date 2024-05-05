package com.example.canvashub;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.canvashub.api.cartApi;
import com.example.canvashub.api.cartItemApi;
import com.example.canvashub.api.customerApi;
import com.example.canvashub.model.Cart;
import com.example.canvashub.model.CartItem;
import com.example.canvashub.model.Customer;
import com.example.canvashub.model.Price;
import com.example.canvashub.retrofit.retrofitService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PriceAdapter extends BaseAdapter {
    Context context;
    ArrayList<Price> pricesArrayList;

    String customer_id;

    ArrayList<Customer> customerArrayList;

    ArrayList<Cart> cartArrayList;

    ArrayList<CartItem> cartItemArrayList;

    retrofitService retrofitService = new retrofitService();

    cartApi cartApi = retrofitService.getRetrofit().create(cartApi.class);

    customerApi customerApi = retrofitService.getRetrofit().create(customerApi.class);

    cartItemApi cartItemApi = retrofitService.getRetrofit().create(cartItemApi.class);

    LayoutInflater layoutInflater;


    public PriceAdapter(Context context, ArrayList<Price> pricesArrayList, String customer_id) {
        this.context = context;
        this.pricesArrayList = pricesArrayList;
        this.customer_id = customer_id;

        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return pricesArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.store_list_row, null);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.store_list_row_productPreview);
        TextView storeName = (TextView) convertView.findViewById(R.id.storeName);
        TextView storePrice = (TextView) convertView.findViewById(R.id.storePrice);
        TextView storeQuantity = (TextView) convertView.findViewById(R.id.storeQuantity);
        Button subtractButton = (Button) convertView.findViewById(R.id.productQuantity_subtract);
        Button addButton = (Button) convertView.findViewById(R.id.productQuantity_add);
        Button addCartButton = (Button) convertView.findViewById(R.id.productQuantity_add_cart);

        Price price = pricesArrayList.get(position);

        // Find the ProgressBar in the layout
        ProgressBar loadingProgressBar= convertView.findViewById(R.id.loadingProgressBar);

        //Need to fetch image URL for prices
        storeName.setText(pricesArrayList.get(position).getStore_Id().getStore_name());
        Picasso.get().load(pricesArrayList.get(position).getStore_Id().getStore_image_url()).resize(50,50).centerCrop().into(imageView);

        storePrice.setText(String.valueOf(pricesArrayList.get(position).getCost()));
        storeQuantity.setText(String.valueOf(0));

        addCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int qty = Integer.parseInt(String.valueOf(storeQuantity.getText()));

                // Show the ProgressBar when adding to cart starts
                loadingProgressBar.setVisibility(View.VISIBLE);

                if (qty <= 0) {
                    Toast.makeText(context.getApplicationContext(), "Quantity is 0", Toast.LENGTH_SHORT).show();
                    loadingProgressBar.setVisibility(View.GONE);
                } else {
                    updateQuantity(qty, price, loadingProgressBar);
                }

            }
        });
        subtractButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int qty;

                if (Integer.parseInt(String.valueOf(storeQuantity.getText())) > 0) {
                    qty = Integer.parseInt(String.valueOf(storeQuantity.getText())) - 1;
                    storeQuantity.setText(String.valueOf(qty));
                }
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int qty = Integer.parseInt(String.valueOf(storeQuantity.getText())) + 1;
                storeQuantity.setText(String.valueOf(qty));
            }
        });
        return convertView;
    }

    private void updateQuantity(int newQuantity, Price price, ProgressBar progressBar) {

        // Fetch the cart for the customer
        cartApi.getCart("CART" + customer_id.trim()).enqueue(new Callback<Cart>() {
            @Override
            public void onResponse(Call<Cart> call, Response<Cart> response) {
                Cart cart = response.body();
                if (cart != null) {
                    // Create a new cart item
                    String uniqueId = cart.getId() + "_" + ((int) (Math.random() * 900000) + 100000);

                    CartItem xCartItem = new CartItem();

                    xCartItem.setId(uniqueId);
                    xCartItem.setCart_Id(cart);
                    xCartItem.setStore_Id(price.getStore_Id());
                    xCartItem.setProduct_Id(price.getProduct_Id());
                    xCartItem.setQuantity(String.valueOf(newQuantity));
                    xCartItem.setPrice(price);

                    // Add the cart item
                    cartItemApi.addCartItem(xCartItem).enqueue(new Callback<CartItem>() {
                        @Override
                        public void onResponse(Call<CartItem> call, Response<CartItem> response) {
                            // Handle success
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onFailure(Call<CartItem> call, Throwable throwable) {
                            // Handle failure
                            progressBar.setVisibility(View.GONE);

                        }
                    });
                } else {
                    // Handle scenario where cart is not found
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<Cart> call, Throwable throwable) {
                // Handle failure to fetch cart
                progressBar.setVisibility(View.GONE);
            }
        });

    }
}
