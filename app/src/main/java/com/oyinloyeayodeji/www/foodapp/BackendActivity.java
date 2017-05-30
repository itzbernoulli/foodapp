package com.oyinloyeayodeji.www.foodapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.oyinloyeayodeji.www.foodapp.Adapters.BackendAdapter;
import com.oyinloyeayodeji.www.foodapp.Adapters.PendingOrdersAdapter;
import com.oyinloyeayodeji.www.foodapp.Adapters.SingleOrderAdapter;
import com.oyinloyeayodeji.www.foodapp.Objects.Food;
import com.oyinloyeayodeji.www.foodapp.Objects.FoodOrder;

import org.joda.time.DateTime;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

public class BackendActivity extends BackendBaseActivity {

    private ArrayList<FoodOrder> orders;

    FirebaseDatabase database;
    DatabaseReference dbRef,
                    restaurantRef,
                    ordersFromDb,
                    orderDayRef;

    TextView ordersTextView;
    int orderTotal;

    SharedPreferences userDetails;
    public static final String PREFS = "Restaurant";
    String Name,Role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backend);

        java.util.Date juDate = new java.util.Date();
        DateTime dt = new DateTime(juDate);

        userDetails = getSharedPreferences(PREFS,0);
        Name = userDetails.getString("userRestaurant","none");
//        Role = userDetails.getString("userRole","none");

        database = FirebaseDatabase.getInstance();

        dbRef = database.getReference("Restaurants");
        restaurantRef = dbRef.child(Name);
        ordersFromDb = restaurantRef.child("Orders");
        orderDayRef = ordersFromDb.child(dt.toString("dd:MM:yy"));
        showProgressDialog();
        orders = new ArrayList<FoodOrder>();

        Date now = new Date(0);


        Toast.makeText(this, "" + now.getTime(), Toast.LENGTH_SHORT).show();

//        Timestamp timestamp = new Timestamp(new Date(6).getTime());

        ordersTextView = (TextView)findViewById(R.id.order_total_amount);

        ListView multipleOrders = (ListView)findViewById(R.id.food_order_list);

        final BackendAdapter adapter = new BackendAdapter(this, orders);

        multipleOrders.setAdapter(adapter);
        multipleOrders.setEmptyView(findViewById(R.id.emptyElement));

//        Query todaysOrders = ordersFromDb.orderByKey().
        orderDayRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot orderSnapshot : dataSnapshot.getChildren()){
                    FoodOrder dbFoodOrder = orderSnapshot.getValue(FoodOrder.class);
                    orders.add(dbFoodOrder);
                    ordersTextView.setText("\u20B5" + calculateOrderAmount(dbFoodOrder));
                }
                adapter.notifyDataSetChanged();
                hideProgressDialog();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        multipleOrders.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FoodOrder order = orders.get(i);
                Intent intent = new Intent(BackendActivity.this, SingleOrderActivity.class);
                intent.putExtra("SingleOrder",order.getOrders());
                startActivity(intent);
            }
        });

    }

    public int calculateOrderAmount(FoodOrder singleOrder){
           orderTotal += singleOrder.getOrderAmount();
        return orderTotal;
    }
}
