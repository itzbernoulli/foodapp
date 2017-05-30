package com.oyinloyeayodeji.www.foodapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SuperAdminActivity extends AppCompatActivity {

    Button createRestaurant, createUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_admin);

        createRestaurant = (Button)findViewById(R.id.create_restaurant_page);
        createUser = (Button)findViewById(R.id.create_user_page);

        createRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SuperAdminActivity.this,CreateRestaurantActivity.class);
                startActivity(i);
            }
        });

        createUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SuperAdminActivity.this,CreateUserActivity.class);
                startActivity(i);
            }
        });
    }
}
