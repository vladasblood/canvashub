package com.example.canvashub;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.canvashub.model.Category;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryAdapter extends BaseAdapter {
    Context context;
    ArrayList<Category> categoriesArrayList;
    LayoutInflater layoutInflater;

    public CategoryAdapter(Context context, ArrayList<Category> categoriesArrayList) {
        this.context = context;
        this.categoriesArrayList = categoriesArrayList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return categoriesArrayList.size();
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
        convertView = layoutInflater.inflate(R.layout.category_list_row, null);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.categoryImage);
        TextView productName = (TextView) convertView.findViewById(R.id.categoryName);

        //Need to fetch image URL for product
        productName.setText(categoriesArrayList.get(position).getCategory_name());
        Picasso.get().load(categoriesArrayList.get(position).getCategory_image_url()).resize(50,50).centerCrop().into(imageView);
        return convertView;
    }
}
