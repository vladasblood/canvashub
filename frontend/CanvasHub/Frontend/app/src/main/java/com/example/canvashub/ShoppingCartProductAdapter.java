package com.example.canvashub;

import static androidx.core.app.ActivityCompat.recreate;
import static androidx.core.content.ContextCompat.startActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.canvashub.model.CartItem;
import com.example.canvashub.api.cartItemApi;
import com.example.canvashub.retrofit.retrofitService;

import android.app.Activity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShoppingCartProductAdapter extends BaseAdapter {

    Context context;
    ArrayList<CartItem> allCartItemsPerStoreArrayList;
    LayoutInflater layoutInflater;
    cartItemApi cartItemApi;
    retrofitService retrofitService;
    StoreAdapter storeAdapter;

    public ShoppingCartProductAdapter(Context context, ArrayList<CartItem> allCartItemsPerStoreArrayList, StoreAdapter storeAdapter) {
        this.context = context;
        this.allCartItemsPerStoreArrayList = allCartItemsPerStoreArrayList;
        this.retrofitService = new retrofitService();
        this.cartItemApi = retrofitService.getRetrofit().create(cartItemApi.class);
        this.storeAdapter = storeAdapter;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return allCartItemsPerStoreArrayList.size();
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
        convertView = layoutInflater.inflate(R.layout.activity_shopping_cart_products_list_view, null);
        TextView shoppingCartProductListProductName = convertView.findViewById(R.id.shoppingCartProductListProductName);
        TextView shoppingCartProductListBasePrice = convertView.findViewById(R.id.shoppingCartProductListBasePrice);
        TextView shoppingCartProductListQuantity = convertView.findViewById(R.id.shoppingCartProductListQuantity);
        TextView shoppingCartProductListTotalPrice = convertView.findViewById(R.id.shoppingCartProductListTotalPrice);
        ImageButton deleteCartItemButton = convertView.findViewById(R.id.deleteCartItemButton);

        deleteCartItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if the list is not empty
                if (!allCartItemsPerStoreArrayList.isEmpty()) {
                    // Store the position before removing the item
                    int itemPosition = position;

                    // Check if the item position is valid
                    if (itemPosition >= 0 && itemPosition < allCartItemsPerStoreArrayList.size()) {
                        // Remove the item from the list
                        CartItem removedItem = allCartItemsPerStoreArrayList.remove(itemPosition);
                        notifyDataSetChanged();

                        // Perform the API call to delete the item
                        cartItemApi.deleteCartItem(removedItem.getId()).enqueue(new Callback<CartItem>() {
                            @Override
                            public void onResponse(Call<CartItem> call, Response<CartItem> response) {
                                // Handle successful response if needed
                            }

                            @Override
                            public void onFailure(Call<CartItem> call, Throwable throwable) {
                                // Handle failure if needed
                            }
                        });

                        Toast.makeText(context, "Item deleted", Toast.LENGTH_SHORT).show();
                    } else {
                        // Log or handle the case where the item position is invalid
                        Log.e("Error", "Invalid item position");
                    }
                } else {
                    // Show a toast indicating that the cart is empty
                    Toast.makeText(context, "The cart is empty", Toast.LENGTH_SHORT).show();
                }
            }
        });


        shoppingCartProductListProductName.setText(allCartItemsPerStoreArrayList.get(position).getProduct_Id().getProduct_name());
        shoppingCartProductListBasePrice.setText(String.valueOf(allCartItemsPerStoreArrayList.get(position).getPrice().getCost()));
        shoppingCartProductListQuantity.setText(allCartItemsPerStoreArrayList.get(position).getQuantity());
        Float tempTotalPrice = allCartItemsPerStoreArrayList.get(position).getPrice().getCost() * Integer.parseInt(allCartItemsPerStoreArrayList.get(position).getQuantity());
        shoppingCartProductListTotalPrice.setText(String.valueOf(tempTotalPrice).format("%.2f", tempTotalPrice));
        return convertView;
    }
}
