package com.oyinloyeayodeji.www.foodapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminWelcomeActivity extends AppCompatActivity {

    Button viewAllOrders, placeAdminOrder, viewSuper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_welcome);

        viewAllOrders = (Button)findViewById(R.id.view_all_orders);
        placeAdminOrder = (Button)findViewById(R.id.place_an_admin_order);
//        viewSuper = (Button)findViewById(R.id.view_super);

        viewAllOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AdminWelcomeActivity.this, BackendActivity.class);
                startActivity(i);
            }
        });

        placeAdminOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AdminWelcomeActivity.this, MealCategoriesActivity.class);
                startActivity(i);
            }
        });

//        viewSuper.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(AdminWelcomeActivity.this, SuperAdminActivity.class);
//                startActivity(i);
//            }
//        });
    }
}
