package com.oyinloyeayodeji.www.foodapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.oyinloyeayodeji.www.foodapp.Objects.Food;
import com.oyinloyeayodeji.www.foodapp.R;

import java.util.List;

/**
 * Created by Ayo on 24/04/2017.
 */

public class MealRecyclerAdapter extends RecyclerView.Adapter<FoodViewHolders> {

    private List<Food> foodList;
    private Context context;

    public MealRecyclerAdapter(Context context, List<Food> foodList){
        this.context = context;
        this.foodList = foodList;
    }

    @Override
    public FoodViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.temp_meal_selection, null);
        FoodViewHolders rcv = new FoodViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(FoodViewHolders holder, int position) {
        holder.nameText.setText(foodList.get(position).getmName());
        holder.priceText.setText("GH"+"\u20B5"+" "+ (foodList.get(position).getmAmount()));
        holder.descriptionText.setText(foodList.get(position).getmDescription());
//        holder.itemImage.setImageResource(foodList.get(position).getmImageResourceId());
//        holder.itemImageResource = foodList.get(position).getmImageResourceId();

        Glide.with(context).load(foodList.get(position).getmImageUrl())
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.itemImage);
    }

    @Override
    public int getItemCount() {
        return this.foodList.size();
    }
}
