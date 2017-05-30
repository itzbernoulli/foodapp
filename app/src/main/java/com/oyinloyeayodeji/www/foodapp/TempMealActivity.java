package com.oyinloyeayodeji.www.foodapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.oyinloyeayodeji.www.foodapp.Adapters.FoodSelectionAdapter;
import com.oyinloyeayodeji.www.foodapp.Adapters.FoodViewHolders;
import com.oyinloyeayodeji.www.foodapp.Adapters.MealRecyclerAdapter;
import com.oyinloyeayodeji.www.foodapp.Effects.MyBounceInterpolator;
import com.oyinloyeayodeji.www.foodapp.Objects.Food;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TempMealActivity extends BaseActivity {

    private TextView orderCount;

    private StaggeredGridLayoutManager staggeredGridLayoutManager;

    int count = 1;

    String chosenCategory;

    static ArrayList<Food> foodItems = new ArrayList<>();

    Boolean anotherOrder = false;
    FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference dbRef,
            resBreakfastChild,
            resLunchChild,
            resDinnerChild,
            restaurantRef,
            resSnacksChild;

    ValueEventListener valueEventListener;

    ProgressBar mProgressBar;

    MealRecyclerAdapter mealAdapter;

    public static HashMap<String, Integer> existingFood = new HashMap<>();

    public static final String PREFS = "Restaurant";

    String Name,Role;
    Food newFood = null;

    //Toggle the filling of database
    //true tells it not to fill it up
    Boolean databaseUpdated = true;

    SharedPreferences userDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_meal);

//        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        userDetails = getSharedPreferences(PREFS,0);
        Name = userDetails.getString("userRestaurant","none");
        Role = userDetails.getString("userRole","none");

        getSupportActionBar().setTitle(Name + " Menu");
//        Toast.makeText(this, Name, Toast.LENGTH_SHORT).show();
        database = FirebaseDatabase.getInstance();
        dbRef = database.getReference("Restaurants");
        restaurantRef = dbRef.child(Name);
        resBreakfastChild = restaurantRef.child("Breakfast");
        resLunchChild = restaurantRef.child("Lunch");
        resDinnerChild = restaurantRef.child("Dinner");
        resSnacksChild = restaurantRef.child("Snacks");

        if(!databaseUpdated){
            addToDatabase(getListItemData());
        }

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

//        if(user != null){
//            Toast.makeText(this, user.getEmail() + " Is signed in", Toast.LENGTH_LONG).show();
//        }else{
//            Toast.makeText(this, "No user is signed in", Toast.LENGTH_LONG).show();
//        }

        mProgressBar = (ProgressBar)findViewById(R.id.progress_bar_temp);
        showProgressDialog();

        final List<Food> staggeredListViewItems = new ArrayList<Food>();
        final RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,1);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        final List<Food> foodStaggeredList = staggeredListViewItems;

        mealAdapter = new MealRecyclerAdapter(TempMealActivity.this, foodStaggeredList);
        recyclerView.setAdapter(mealAdapter);

        recyclerView.addOnItemTouchListener(new CustomRVItemTouchListener(this, recyclerView, new RecyclerViewItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Food food = foodStaggeredList.get(position);
                storeOrder(food);
                animateFab();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot foodSnapshot : dataSnapshot.getChildren()) {
                    Food food = foodSnapshot.getValue(Food.class);
                    staggeredListViewItems.add(food);
                }
                mealAdapter.notifyDataSetChanged();
                hideProgressDialog();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            chosenCategory = extras.getString("Category");

            switch(chosenCategory){
                case "Breakfast":
                    //load firebase breakfast data
                    resBreakfastChild.addValueEventListener(valueEventListener);
                    break;
                case "Lunch":
                    //load firebase breakfast data
                    resLunchChild.addValueEventListener(valueEventListener);
                    break;
                case "Dinner":
                    //load firebase breakfast data
                    resDinnerChild.addValueEventListener(valueEventListener);
                    break;
                case "Snacks":
                    //load firebase breakfast data
                    resSnacksChild.addValueEventListener(valueEventListener);
                    break;
                default:
                    Toast.makeText(this, "Nothing", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        orderCount = (TextView)findViewById(R.id.count);

        orderCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TempMealActivity.this, MyMealsActivity.class);
                intent.putExtra("foodlist",foodItems);
                intent.putExtra("currentMealCategory",chosenCategory);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            if(extras.getBoolean("AnotherOrder")){
                anotherOrder = extras.getBoolean("AnotherOrder");
            }else if(extras.getSerializable("orders")!= null){
                chosenCategory = extras.getString("Category");
                foodItems.clear();
                foodItems = (ArrayList<Food>)extras.getSerializable("orders");
                setFabText();
            }
        }
        if(anotherOrder){
            foodItems.clear();
        }
        mealAdapter.notifyDataSetChanged();
        super.onResume();
    }

    public void animateFab(){
        final Animation myAnim = AnimationUtils.loadAnimation(TempMealActivity.this,R.anim.bounce);
        myAnim.setDuration(2000);

        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2,20);
        myAnim.setInterpolator(interpolator);
        orderCount.startAnimation(myAnim);
    }

    public void storeOrder(Food food){
        Food foo = new Food(

                food.getmImageUrl(),
                food.getmName(),
                food.getmDescription(),
                food.getmAmount()
        );
        if(foodItems.size() == 0){
            foodItems.add(foo);
            orderCount.setText(""+ count);
            existingFood.put(foo.getmName(),0);
        }else{
            int counter = 0;
            for (Food foodItem : foodItems){
                Log.d("FOOD","Existing food item " + foodItem.getmName());
                if(!existingFood.containsKey(foo.getmName())){
                    existingFood.put(foo.getmName(),counter );
                    newFood = foo;
                    Log.d("FOOD","A New Food is added to the Map and Arraylist " + foo.getmName());
                }
                else{
                    if(foo.getmName().equals(foodItem.getmName())){
                        Log.d("FOOD","Increment QTY " + foodItem.getmName());
                        foodItem.addToQuantity();
                    }
                }
            }
            if(newFood != null){
                foodItems.add(newFood);
                Log.d("NEW FOOD ADDED","Food Addition complete" + newFood.getmName());
                newFood = null;
                Log.d("FOOD","**********THE END*************");
                Log.d("FOOD","  ");
                Log.d("FOOD","  ");
            }
            setFabText();
        }
    }

    public void setFabText(){
        orderCount.setText(""+ checkQuantity());
    }

    public int checkQuantity(){
        count = 0;
        for(Food food : foodItems){
            count += food.getmQuantity();
        }
        return count;
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

    private List<Food> getListItemData(){
        List<Food> listViewItems = new ArrayList<Food>();
        listViewItems.add(new Food("https://res.cloudinary.com/dw0nbbvlf/image/upload/v1495280439/FoodApp/ic_1.jpg","Rice Balls","Sumptuous meal",30));
        listViewItems.add(new Food("https://res.cloudinary.com/dw0nbbvlf/image/upload/v1495280440/FoodApp/ic_10.jpg","Jollof Rice","Sumptuous meal",50));
        listViewItems.add(new Food("https://res.cloudinary.com/dw0nbbvlf/image/upload/v1495280440/FoodApp/ic_9.jpg","Banku","Another Sumptuous meal",80));
        listViewItems.add(new Food("https://res.cloudinary.com/dw0nbbvlf/image/upload/v1495280440/FoodApp/ic_8.jpg","Chocolate Cake","Sumptuous meal",30));
        listViewItems.add(new Food("https://res.cloudinary.com/dw0nbbvlf/image/upload/v1495280439/FoodApp/ic_2.jpg","Fried Rice","Sumptuous meal",50));
        listViewItems.add(new Food("https://res.cloudinary.com/dw0nbbvlf/image/upload/v1495280439/FoodApp/ic_1.jpg","Waakye","Another Sumptuous meal",70));
        listViewItems.add(new Food("https://res.cloudinary.com/dw0nbbvlf/image/upload/v1495280439/FoodApp/ic_6.jpg","Donut","Sumptuous meal",30));
        listViewItems.add(new Food("https://res.cloudinary.com/dw0nbbvlf/image/upload/v1495280439/FoodApp/ic_3.jpg","Chicken Wings","Sumptuous meal",50));
        listViewItems.add(new Food("https://res.cloudinary.com/dw0nbbvlf/image/upload/v1495280439/FoodApp/ic_5.jpg","Pepperoni Pizza","Sumptuous meal",80));
        listViewItems.add(new Food("https://res.cloudinary.com/dw0nbbvlf/image/upload/v1495280439/FoodApp/ic_2.jpg","Konkonte","Sumptuous meal",30));
        listViewItems.add(new Food("https://res.cloudinary.com/dw0nbbvlf/image/upload/v1495280439/FoodApp/ic_4.jpg","Ice Cream","Sumptuous meal",50));

        return listViewItems;
    }

    public void addToDatabase(List<Food> listOfMeals){
        for (Food food : listOfMeals){
            resBreakfastChild.push().setValue(food);
            resLunchChild.push().setValue(food);
            resDinnerChild.push().setValue(food);
            resSnacksChild.push().setValue(food);
        }
    }
}
