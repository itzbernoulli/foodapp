package com.oyinloyeayodeji.www.foodapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MealCategoriesActivity extends AppCompatActivity {

    ImageView breakfast, lunch, dinner, snack;

    FirebaseAuth mAuth;
    FirebaseUser user;

    SharedPreferences userDetails;
    public static final String PREFS = "Restaurant";
    String Name,Role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_categories);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        userDetails = getSharedPreferences(PREFS,0);
        Name = userDetails.getString("userRestaurant","none");
        Role = userDetails.getString("userRole","none");

        getSupportActionBar().setTitle("Name");
//        if(user != null){
//            Toast.makeText(this, user.getEmail() + " Is signed in", Toast.LENGTH_LONG).show();
//        }else{
//            Toast.makeText(this, "No user is signed in", Toast.LENGTH_LONG).show();
//        }
        breakfast = (ImageView)findViewById(R.id.breakfast);
        lunch = (ImageView)findViewById(R.id.lunch);
        dinner = (ImageView)findViewById(R.id.dinner);
        snack = (ImageView)findViewById(R.id.snacks);

        breakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MealCategoriesActivity.this, TempMealActivity.class);
                i.putExtra("Category","Breakfast");
                i.putExtra("AnotherOrder",true);
                startActivity(i);
            }
        });

        lunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MealCategoriesActivity.this, TempMealActivity.class);
                i.putExtra("Category","Lunch");
                i.putExtra("AnotherOrder",true);
                startActivity(i);
            }
        });

        dinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MealCategoriesActivity.this, TempMealActivity.class);
                i.putExtra("Category","Dinner");
                i.putExtra("AnotherOrder",true);
                startActivity(i);
            }
        });

        snack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MealCategoriesActivity.this, TempMealActivity.class);
                i.putExtra("Category","Snacks");
                i.putExtra("AnotherOrder",true);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.options, menu);

        if(!Role.equals("admin")) {
            MenuItem admin = menu.findItem(R.id.action_admin);
            admin.setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_sign_out:
                mAuth.signOut();
                Intent i = new Intent(this, SignInActivity.class);
                startActivity(i);
                return true;
            case R.id.action_admin:
                Intent intent = new Intent(this, AdminWelcomeActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}
