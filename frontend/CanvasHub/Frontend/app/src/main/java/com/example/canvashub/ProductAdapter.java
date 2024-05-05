package com.example.canvashub;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.canvashub.model.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductAdapter extends BaseAdapter {
    Context context;
    ArrayList<Product> productsArrayList;
    LayoutInflater layoutInflater;

    public ProductAdapter(Context context, ArrayList<Product> productsArrayList) {
        this.context = context;
        this.productsArrayList = productsArrayList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return productsArrayList.size();
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
        convertView = layoutInflater.inflate(R.layout.product_list_row, null);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.productPreview);
        TextView productName = (TextView) convertView.findViewById(R.id.productNameTextView);
        TextView productDescription = (TextView) convertView.findViewById(R.id.TextView);

        //Need to fetch image URL for product
        Picasso.get().load(productsArrayList.get(position).getProduct_image_url()).resize(50,50).centerCrop().into(imageView);
        productName.setText(productsArrayList.get(position).getProduct_name());
        productDescription.setText(productsArrayList.get(position).getDescription());
        return convertView;
    }
}
