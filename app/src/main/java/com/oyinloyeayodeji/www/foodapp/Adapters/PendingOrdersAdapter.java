package com.oyinloyeayodeji.www.foodapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.oyinloyeayodeji.www.foodapp.Objects.Food;
import com.oyinloyeayodeji.www.foodapp.R;

import java.util.List;

/**
 * Created by Ayo on 20/04/2017.
 */

public class PendingOrdersAdapter extends ArrayAdapter<Food> {

    public PendingOrdersAdapter(Context context, List<Food> myOrders) {
        super(context, 0, myOrders);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_my_waiting_meals,parent,false
            );
        }

        Food currentFood = getItem(position);

        TextView currentFoodName = (TextView)listItemView.findViewById(R.id.selected_food_name);
        currentFoodName.setText(currentFood.getmName());

        TextView currentCost = (TextView)listItemView.findViewById(R.id.selected_food_amount);
        currentCost.setText("GH"+"\u20B5"+" "+(currentFood.getmAmount() * currentFood.getmQuantity()));

        TextView quantityText = (TextView)listItemView.findViewById(R.id.quantity);
        quantityText.setText("x "+currentFood.getmQuantity());

        return listItemView;
    }
}
