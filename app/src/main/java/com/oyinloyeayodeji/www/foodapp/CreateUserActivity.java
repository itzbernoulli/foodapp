package com.oyinloyeayodeji.www.foodapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.oyinloyeayodeji.www.foodapp.Objects.ExtraUserData;

import java.util.ArrayList;

public class CreateUserActivity extends BaseActivity {

    private String[] roles = {
            "admin",
            "user"
    };

   private ArrayList<String> mRestaurants;

    private static final String TAG = "CREATEACTIVITY";
    FirebaseAuth mAuth;
    FirebaseUser dbuser;
    private AppCompatSpinner mSpinnerSlide , mSpinnerRestaurants;
    TextView mPhoneNumber,mPassword;

    Button mCreateUser;

    DatabaseReference myRef, dbUsers;

    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        mAuth = FirebaseAuth.getInstance();
        dbuser = mAuth.getCurrentUser();

       myRef = FirebaseDatabase.getInstance().getReference();
       dbUsers = myRef.child("dbusers");

//        if(dbuser != null){
//            Toast.makeText(this, dbuser.getEmail() + " Is signed in", Toast.LENGTH_LONG).show();
//        }else{
//            Toast.makeText(this, "No dbuser is signed in", Toast.LENGTH_LONG).show();
//        }

        mRestaurants = new ArrayList<String>();
        mRestaurants.add("Bloomfield");
        mRestaurants.add("Lizzy");
        mRestaurants.add("Papa's");

        mPhoneNumber = (TextView)findViewById(R.id.field_phone);
        mPassword = (TextView)findViewById(R.id.field_password);
        mCreateUser = (Button)findViewById(R.id.create_user_button);

        mSpinnerSlide = (AppCompatSpinner) findViewById(R.id.spinner_role);
        mSpinnerRestaurants = (AppCompatSpinner) findViewById(R.id.spinner_restaurant);

        mSpinnerSlide.setAdapter(new ArrayAdapter<>(this, R.layout.spinner, roles));
        mSpinnerSlide.setSelection(1);

        mSpinnerRestaurants.setAdapter(new ArrayAdapter<>(this, R.layout.spinner, mRestaurants));
        mSpinnerRestaurants.setSelection(1);

        mCreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount(mPhoneNumber.getText().toString() + "@gmail.com",mPassword.getText().toString());

            }
        });
    }

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

//        getSelectedRole();

        showProgressDialog();

        // [START create_dbuser_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in dbuser's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser dbuser = mAuth.getCurrentUser();
                            createOtherUserProperties(dbuser);
                        } else {
                            // If sign in fails, display a message to the dbuser.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(CreateUserActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END create_dbuser_with_email]
    }

    private void createOtherUserProperties(FirebaseUser dbuser) {
        String theRole = getSelectedRole(mSpinnerSlide);
        String theRestaurant = getSelectedRestaurant(mSpinnerRestaurants);
        ExtraUserData data = new ExtraUserData(theRole,theRestaurant,dbuser.getEmail());
        dbUsers.child(dbuser.getUid()).setValue(data);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userRestaurant",data.getmRestaurant());
        editor.putString("userRole",data.getmRole());
        editor.commit();

        if(data.getmRole().equals("admin")){
            Intent i = new Intent(this,AdminWelcomeActivity.class);
            startActivity(i);
        }else if (data.getmRole().equals("user")){
            Intent i = new Intent(this,MealCategoriesActivity.class);
            startActivity(i);
        }
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = mPhoneNumber.getText().toString() + "@gmail.com";

        if (TextUtils.isEmpty(email)) {
            mPhoneNumber.setError("Required.");
            valid = false;
        }else if (email.length() < 9){
            mPhoneNumber.setError("Incomplete.");
            valid = false;
        }else if (mPhoneNumber.getText().toString().length() >  9){
            mPhoneNumber.setError("Too many numbers.");
            valid = false;
        } else {
            mPhoneNumber.setError(null);
        }
        String password = mPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPassword.setError("Required.");
            valid = false;
        } else {
            mPassword.setError(null);
        }

        return valid;
    }

    private String getSelectedRole(AppCompatSpinner spinner) {
        int index = spinner.getSelectedItemPosition();
            String u = roles[index];
        return u;
    }

    private String getSelectedRestaurant(AppCompatSpinner spinner) {
        int index = spinner.getSelectedItemPosition();
        String u = mRestaurants.get(index);
        return u;
    }
}
