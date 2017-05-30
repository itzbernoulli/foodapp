package com.oyinloyeayodeji.www.foodapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.oyinloyeayodeji.www.foodapp.Adapters.OrderAdapter;
import com.oyinloyeayodeji.www.foodapp.Adapters.SingleOrderAdapter;
import com.oyinloyeayodeji.www.foodapp.Objects.Food;
import com.oyinloyeayodeji.www.foodapp.Objects.FoodOrder;

import java.util.ArrayList;

public class SingleOrderActivity extends AppCompatActivity {

    ArrayList<Food> foodlist;
//    Button backToOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_order);

//        backToOrders = (Button)findViewById(R.id.back_to_orders);

        Bundle extras = getIntent().getExtras();

        if(extras != null){
            foodlist = (ArrayList<Food>)extras.getSerializable("SingleOrder");
        }

//        backToOrders.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(SingleOrderActivity.this, BackendActivity.class);
//                startActivity(i);
//            }
//        });

        ListView Orders = (ListView)findViewById(R.id.single_order_food_list);

        SingleOrderAdapter adapter = new SingleOrderAdapter(this, foodlist);

        Orders.setAdapter(adapter);
    }
}
