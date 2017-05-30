package com.oyinloyeayodeji.www.foodapp.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.oyinloyeayodeji.www.foodapp.Objects.Food;
import com.oyinloyeayodeji.www.foodapp.R;

import java.util.ArrayList;

import static com.oyinloyeayodeji.www.foodapp.R.id.count;

/**
 * Created by Ayo on 24/04/2017.
 */

public class FoodViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageView itemImage;
    public TextView nameText, priceText,descriptionText;

    public int itemImageResource, orderCount;

    final static ArrayList<Food> foodItems = new ArrayList<>();

    public FoodViewHolders(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        itemImage = (ImageView)itemView.findViewById(R.id.item_image);
        nameText = (TextView)itemView.findViewById(R.id.food_name);
        priceText = (TextView)itemView.findViewById(R.id.food_price);
        descriptionText = (TextView)itemView.findViewById(R.id.food_description);
    }

    @Override
    public void onClick(View view) {
//        Toast.makeText(view.getContext(), "Clicked Position = " + getPosition(), Toast.LENGTH_SHORT).show();
    }

//    public void storeOrder(FoodViewHolders food){
//        Food foo = new Food(
//
//                food.itemImageResource,
//                food.nameText.getText().toString(),
//                food.descriptionText.getText().toString(),
//                Integer.parseInt((food.priceText.getText().toString()).replace("GH"+"\u20B5 ",""))
//        );
//        if(foodItems.size() == 0){
//            foodItems.add(foo);
//            orderCount.setText(""+ count);
//        }
////        else{
////            count = 0;
////            for(Food foodItem : foodItems){
////                if(foodItem.getmName().equals(foo.getmName())){
////                    foodItem.addToQuantity();
////                    count += foodItem.getmQuantity();
////                    orderCount.setText(""+ count);
////                }else{
////                    foodItems.add(foo);
////                    count += 1;
////                    orderCount.setText(""+ count);
////                }
////            }
////        }
//        else{
//            foodItems.add(foo);
//            orderCount.setText(""+ foodItems.size());
//        }
}
