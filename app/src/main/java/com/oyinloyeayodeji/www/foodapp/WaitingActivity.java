package com.oyinloyeayodeji.www.foodapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.oyinloyeayodeji.www.foodapp.Adapters.PendingOrdersAdapter;
import com.oyinloyeayodeji.www.foodapp.Objects.Food;
import com.oyinloyeayodeji.www.foodapp.Objects.FoodOrder;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Date;

public class WaitingActivity extends AppCompatActivity {

    int orderNumber, orderAmount, numberOfMeals;
    TextView orderNumberTextView,totalOrderAmount;
    Button newOrder,viewBackend;

    ArrayList<Food> order;

    final static ArrayList<FoodOrder> foodOrders = new ArrayList<>();

    FirebaseDatabase database;
    DatabaseReference dbRef,restaurantRef,foodOrderRef,orderDayRef;

    FirebaseAuth mAuth;
    FirebaseUser user;

    SharedPreferences userDetails;
    public static final String PREFS = "Restaurant";
    String Name,Role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting);

        Date juDate = new Date();
        DateTime dt = new DateTime(juDate);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        userDetails = getSharedPreferences(PREFS,0);
        Name = userDetails.getString("userRestaurant","none");
        Role = userDetails.getString("userRole","none");
        SharedPreferences.Editor editor = userDetails.edit();

        database = FirebaseDatabase.getInstance();
        dbRef = database.getReference("Restaurants");
        restaurantRef = dbRef.child(Name);
        foodOrderRef = restaurantRef.child("Orders");
        orderDayRef = foodOrderRef.child(dt.toString("dd:MM:yy"));

        viewBackend = (Button)findViewById(R.id.view_backend_orders);
        newOrder = (Button)findViewById(R.id.place_new_order);
        orderNumberTextView = (TextView)findViewById(R.id.unique_number);
        totalOrderAmount = (TextView)findViewById(R.id.total_order_amount);

        if(!Role.equals("admin")){
            viewBackend.setVisibility(View.GONE);
        }

        Bundle extras = getIntent().getExtras();

        String prependedCode = restaurantRef.getKey().substring(0,3).toUpperCase() + "-";

        if(extras != null){
            //all these will dissapear since it will be loaded from the database
            orderNumber = extras.getInt("Number");
            orderAmount = extras.getInt("OrderAmount");
            order = (ArrayList<Food>)extras.getSerializable("Order");
            numberOfMeals = extras.getInt("MealCount");
            foodOrders.add(new FoodOrder( prependedCode+orderNumber,orderAmount,numberOfMeals,order));
            sendOrdersToBackend(new FoodOrder(prependedCode+orderNumber,orderAmount,numberOfMeals,order));
//            editor.putInt("userLastOrder",orderNumber);
//            editor.commit();
        }

        orderNumberTextView.setText(prependedCode+ orderNumber);
        totalOrderAmount.setText("GH"+"\u20B5"+" " + orderAmount);

        newOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(WaitingActivity.this,MealCategoriesActivity.class);
                startActivity(i);
            }
        });

        viewBackend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(WaitingActivity.this, BackendActivity.class);
                i.putExtra("Orders",foodOrders);
                startActivity(i);
            }
        });

        ListView oneOrder = (ListView)findViewById(R.id.awaiting_food_list);

        PendingOrdersAdapter adapter = new PendingOrdersAdapter(this, order);

        oneOrder.setAdapter(adapter);
    }

    private void sendOrdersToBackend(FoodOrder foodOrder) {
        orderDayRef.push().setValue(foodOrder);
//        findViewById(R.id.action_admin).setVisibility(View.GONE);
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
                signOut();
                return true;
//            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_admin:
                Intent i = new Intent(WaitingActivity.this,AdminWelcomeActivity.class);
                startActivity(i);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void signOut() {
        mAuth.signOut();
        //Update this so the user can signout at any point in the app.
        Intent i = new Intent(this,SignInActivity.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}
