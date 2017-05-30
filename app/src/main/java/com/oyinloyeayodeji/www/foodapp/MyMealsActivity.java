package com.oyinloyeayodeji.www.foodapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.oyinloyeayodeji.www.foodapp.Adapters.OrderAdapter;
import com.oyinloyeayodeji.www.foodapp.Objects.Food;

import java.util.ArrayList;

public class MyMealsActivity extends AppCompatActivity {

    Button addMeal, checkOut;
    TextView mealTotalText;

    ArrayList<Food> orders;
    String chosenCategory;

    final static ArrayList<Integer> orderNumber = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_meals);

        addMeal = (Button)findViewById(R.id.add_meal);
        checkOut = (Button)findViewById(R.id.checkout);
        mealTotalText = (TextView) findViewById(R.id.meal_total);

        addMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MyMealsActivity.this, TempMealActivity.class);
                i.putExtra("orders", orders);
                i.putExtra("Category",chosenCategory);
                startActivity(i);
            }
        });

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            orders = (ArrayList<Food>)extras.getSerializable("foodlist");
            chosenCategory = extras.getString("currentMealCategory");
        }

        //Make a call to firebase to save all orders for this
        //particular buyer under the respective restaurant
        //Also Add a progress bar Let the all inherit from BaseActivity Class
        //**********FIREBASE*****************

        checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(orders.size() == 0){
                    Toast.makeText(MyMealsActivity.this, "Please add a meal to proceed to checkout.", Toast.LENGTH_SHORT).show();
                }else{

                    Intent i = new Intent(MyMealsActivity.this, WaitingActivity.class);
                    i.putExtra("OrderAmount",calculateMealTotal());
                    i.putExtra("Number",generatedOrderNumber());
                    i.putExtra("MealCount", orders.size());
                    i.putExtra("Order", orders);
                    startActivity(i);
                }
            }
        });

        ListView storedOrders = (ListView)findViewById(R.id.selected_food_list);

        OrderAdapter adapter = new OrderAdapter(this, orders);

        storedOrders.setAdapter(adapter);

        mealTotalText.setText("GH"+"\u20B5"+" "+ calculateMealTotal());
    }

    public int calculateMealTotal(){
        int mealTotal = 0;
        for(Food order : orders){
            mealTotal += order.getmAmount() * order.getmQuantity();
        }
        return mealTotal;
    }

    public int generatedOrderNumber(){
        orderNumber.add(orderNumber.size()+1);
        return orderNumber.get(orderNumber.size()-1);
    }
}
