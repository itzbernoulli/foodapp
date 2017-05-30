package com.oyinloyeayodeji.www.foodapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.oyinloyeayodeji.www.foodapp.Objects.FoodOrder;
import com.oyinloyeayodeji.www.foodapp.R;

import java.util.List;

/**
 * Created by Ayo on 20/04/2017.
 */

public class BackendAdapter extends ArrayAdapter<FoodOrder> {

    public BackendAdapter(Context context, List<FoodOrder> myOrders) {
        super(context, 0, myOrders);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_backend_orders,parent,false
            );
        }

        FoodOrder currentOrder = getItem(position);

        TextView currentFoodName = (TextView)listItemView.findViewById(R.id.order_number);
        currentFoodName.setText(""+ currentOrder.getOrderNumber());

        TextView currentCost = (TextView)listItemView.findViewById(R.id.number_of_meals);
        currentCost.setText("x "+currentOrder.getNumberOfMeals());

        TextView quantityText = (TextView)listItemView.findViewById(R.id.order_amount);
        quantityText.setText("GH"+"\u20B5"+" "+ currentOrder.getOrderAmount());

        return listItemView;
    }
}
