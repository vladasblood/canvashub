package com.example.canvashub;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class PaymentStoreAdapter extends BaseAdapter {

    Context context;
    List<String> allStoresArrayList;
    List<String> allPricesPerStoreArrayList;
    LayoutInflater layoutInflater;

    public PaymentStoreAdapter(Context context, List<String> allStoresArrayList, List<String> allPricesPerStoreArrayList) {
        this.context = context;
        this.allStoresArrayList = allStoresArrayList;
        this.allPricesPerStoreArrayList = allPricesPerStoreArrayList;
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
        convertView = layoutInflater.inflate(R.layout.activity_payment_store_list_row, null);
        TextView paymentStoreNameTextView = convertView.findViewById(R.id.paymentStoreNameTextView);
        TextView paymentTotalPerStoreTextView = convertView.findViewById(R.id.paymentTotalPerStoreTextView);

        paymentStoreNameTextView.setText(allStoresArrayList.get(position));
        paymentTotalPerStoreTextView.setText("₱"+allPricesPerStoreArrayList.get(position));
        return convertView;
    }
}
