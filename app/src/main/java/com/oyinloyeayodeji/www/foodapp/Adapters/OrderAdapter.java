package com.oyinloyeayodeji.www.foodapp.Adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.oyinloyeayodeji.www.foodapp.Objects.Food;
import com.oyinloyeayodeji.www.foodapp.R;

import java.util.List;

/**
 * Created by Ayo on 17/04/2017.
 */

public class OrderAdapter extends ArrayAdapter<Food>{
    public OrderAdapter(Context context, List<Food> myOrders) {
        super(context, 0, myOrders);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_my_meal,parent,false
            );
        }

        final Food currentFood = getItem(position);

        TextView currentFoodName = (TextView)listItemView.findViewById(R.id.selected_food_name);
        currentFoodName.setText(currentFood.getmName());

        final TextView currentCost = (TextView)listItemView.findViewById(R.id.selected_food_amount);
        currentCost.setText("GH"+"\u20B5"+" "+ (currentFood.getmAmount() * currentFood.getmQuantity()));

        final TextView quantityText = (TextView)listItemView.findViewById(R.id.quantity);
        quantityText.setText("x "+currentFood.getmQuantity());

        TextView addMeal = (TextView)listItemView.findViewById(R.id.plus_meal);
        addMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getContext(), "Adding to "+ currentFood.getmName(), Toast.LENGTH_SHORT).show();
                currentFood.addToQuantity();
                quantityText.setText("x "+currentFood.getmQuantity());
                currentCost.setText("GH"+"\u20B5"+" "+ (currentFood.getmAmount() * currentFood.getmQuantity()));
            }
        });

        TextView subtractMeal = (TextView)listItemView.findViewById(R.id.minus_meal);
        subtractMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentFood.removeFromQuantity();
                quantityText.setText("x "+currentFood.getmQuantity());
                currentCost.setText("GH"+"\u20B5"+" "+ (currentFood.getmAmount() * currentFood.getmQuantity()));

            }
        });

//        quantityText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
        return listItemView;
    }
}
