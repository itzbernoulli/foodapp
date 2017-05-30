package com.oyinloyeayodeji.www.foodapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateRestaurantActivity extends AppCompatActivity {

    TextView mRestaurant;

    Button mCreateRestaurant;

    DatabaseReference myRef, dbRestaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_restaurant);

        myRef = FirebaseDatabase.getInstance().getReference();
        dbRestaurant = myRef.child("Restaurants");

        mRestaurant = (TextView)findViewById(R.id.create_restaurant);
        mCreateRestaurant = (Button)findViewById(R.id.create_restaurant_button);

        mCreateRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbRestaurant.child(mRestaurant.getText().toString()).setValue("New Restaurant");
//                Toast.makeText(CreateRestaurantActivity.this, mRestaurant.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
