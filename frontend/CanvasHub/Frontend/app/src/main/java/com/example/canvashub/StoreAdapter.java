package com.example.canvashub;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.canvashub.model.CartItem;
import com.example.canvashub.model.Store;

import java.util.ArrayList;

public class StoreAdapter extends BaseAdapter {
    Context context;
    ArrayList<Store> allStoresArrayList;
    ArrayList<CartItem> allCartItemsArrayList;
    LayoutInflater layoutInflater;
//    Double totalPerStore;

    public StoreAdapter(Context context, ArrayList<Store> allStoresArrayList, ArrayList<CartItem> allCartItemsArrayList) {
        this.context = context;
        this.allStoresArrayList = allStoresArrayList;
        this.allCartItemsArrayList = allCartItemsArrayList;
//        this.totalPerStore = 0.0;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return allStoresArrayList.size();
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
        convertView = layoutInflater.inflate(R.layout.activity_shopping_cart_store_list_row, null);
        TextView storeNameTextView = convertView.findViewById(R.id.storeNameTextView);
        ListView shoppingCartProductsListView = convertView.findViewById(R.id.shoppingCartProductsListView);

        ArrayList<CartItem> allCartItemsPerStoreArrayList = new ArrayList<>();
        double totalPerStore = 0.0;


        for (CartItem xCartItem:allCartItemsArrayList) {
            if (allStoresArrayList.get(position).getId().equals(xCartItem.getStore_Id().getId())) {
                allCartItemsPerStoreArrayList.add(xCartItem);
                totalPerStore += xCartItem.getPrice().getCost() * Integer.parseInt(xCartItem.getQuantity());
            }
        }

        ShoppingCartProductAdapter shoppingCartProductAdapter = new ShoppingCartProductAdapter(context, allCartItemsPerStoreArrayList, this);
        shoppingCartProductsListView.setAdapter(shoppingCartProductAdapter);

        // Calculate total height of the ListView based on the items
        int totalHeight = 0;
        for (int i = 0; i < shoppingCartProductAdapter.getCount(); i++) {
            View listItem = shoppingCartProductAdapter.getView(i, null, shoppingCartProductsListView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        // Set the height of the ListView
        ViewGroup.LayoutParams params = shoppingCartProductsListView.getLayoutParams();
        params.height = totalHeight + (shoppingCartProductsListView.getDividerHeight() * (shoppingCartProductAdapter.getCount() - 1));
        shoppingCartProductsListView.setLayoutParams(params);
        shoppingCartProductsListView.requestLayout();

        storeNameTextView.setText(allStoresArrayList.get(position).getStore_name());



        return convertView;
    }
}
