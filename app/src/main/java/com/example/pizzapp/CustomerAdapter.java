package com.example.pizzapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerViewHolder> {

    private Context context;
    private List<FoodClass> dataList;

    public CustomerAdapter(Context context, List<FoodClass> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new CustomerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
        // Bind your regular item data here
        Glide.with(context).load(dataList.get(position).getFoodimage()).into(holder.recImage);
        holder.recName.setText(dataList.get(position).getFoodname());
        holder.recDesc.setText(dataList.get(position).getFooddesc());
        holder.recPrice.setText(String.valueOf(dataList.get(position).getFoodprice()));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    // Add this method to update data in the adapter
    public void addData(List<FoodClass> newDataList) {
        dataList.clear();
        dataList.addAll(newDataList);

        notifyDataSetChanged();
    }


}

 class CustomerViewHolder extends RecyclerView.ViewHolder {

    ImageView recImage;
    TextView recName, recDesc, recPrice;

    public CustomerViewHolder(@NonNull View itemView) {
        super(itemView);

        recImage = itemView.findViewById(R.id.recImage);
        recDesc = itemView.findViewById(R.id.recDesc);
        recPrice = itemView.findViewById(R.id.recPrice);
        recName = itemView.findViewById(R.id.recName);
    }
}
