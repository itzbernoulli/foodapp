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

public class SingleOrderAdapter extends ArrayAdapter<Food> {

    public SingleOrderAdapter(Context context, List<Food> myOrders) {
        super(context, 0, myOrders);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_single_order,parent,false
            );
        }

        Food currentFood = getItem(position);

        TextView currentFoodName = (TextView)listItemView.findViewById(R.id.ordered_food_name);
        currentFoodName.setText(currentFood.getmName());

        TextView currentCost = (TextView)listItemView.findViewById(R.id.ordered_food_amount);
        currentCost.setText("GH"+"\u20B5"+" "+currentFood.getmAmount());

        TextView quantityText = (TextView)listItemView.findViewById(R.id.ordered_quantity);
        quantityText.setText("x "+currentFood.getmQuantity());

        return listItemView;
    }
}
