package com.oyinloyeayodeji.www.foodapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.oyinloyeayodeji.www.foodapp.Objects.ExtraUserData;

public class SignInActivity extends BaseActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;

    private static final String TAG = "EmailPassword";

    private EditText mEmailField;
    private EditText mPasswordField;

    ProgressBar mProgressBar;

    SharedPreferences sharedPreferences;

    DatabaseReference myRef,dbUsers;

    public static final String PREFS = "Restaurant";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        sharedPreferences = getSharedPreferences(PREFS,0);


        // Views
        mEmailField = (EditText) findViewById(R.id.field_email);
        mPasswordField = (EditText) findViewById(R.id.field_password);
        mProgressBar = (ProgressBar)findViewById(R.id.progress_bar_temp);

        // Buttons
        findViewById(R.id.email_sign_in_button).setOnClickListener(this);


        mAuth = FirebaseAuth.getInstance();

        myRef = FirebaseDatabase.getInstance().getReference();
        dbUsers = myRef.child("dbusers");
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent i = new Intent(SignInActivity.this,MealCategoriesActivity.class);
            startActivity(i);
        }
//        updateUI(currentUser);
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }


        showProgressDialog();
//        mProgressBar.setVisibility(View.VISIBLE);

//        interceptSignIn(email,password);

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
//                            Toast.makeText(SignInActivity.this, "Signed in " + user.getEmail(), Toast.LENGTH_SHORT).show();
                            interceptSignIn(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(SignInActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        if (!task.isSuccessful()) {
                            Toast.makeText(SignInActivity.this, R.string.auth_failed, Toast.LENGTH_SHORT).show();
//                            mStatusTextView.setText(R.string.auth_failed);
                        }
                        hideProgressDialog();
//                        mProgressBar.setVisibility(View.GONE);
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
    }

    //User as a sign in use case when i was using the offline methodology

    private void interceptSignIn(FirebaseUser user) {
        dbUsers.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ExtraUserData data = dataSnapshot.getValue(ExtraUserData.class);
                redirectUser(data);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void redirectUser(ExtraUserData data) {
        Editor editor = sharedPreferences.edit();
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

        String email = mEmailField.getText().toString() + "@gmail.com";
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Required.");
            valid = false;
        }else if (email.length() < 9){
            mEmailField.setError("Incomplete.");
            valid = false;
        }else if (mEmailField.getText().toString().length() >  9){
            mEmailField.setError("Too many numbers.");
            valid = false;
        } else {
            mEmailField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Required.");
            valid = false;
        } else {
            mPasswordField.setError(null);
        }

        return valid;
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if(i == R.id.email_sign_in_button) {
            signIn(mEmailField.getText().toString() + "@gmail.com", mPasswordField.getText().toString());
        }
    }
}
