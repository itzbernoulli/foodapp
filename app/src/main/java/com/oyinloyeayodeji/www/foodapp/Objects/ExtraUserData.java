package com.oyinloyeayodeji.www.foodapp.Objects;

/**
 * Created by Ayo on 30/04/2017.
 */

public class ExtraUserData {
    private String mRole;
    private String mRestaurant;
    private String mEmail;

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public ExtraUserData() {
    }

    public ExtraUserData(String mRole, String mRestaurant, String mEmail) {
        this.mRole = mRole;
        this.mRestaurant = mRestaurant;
        this.mEmail = mEmail;
    }

    public String getmRole() {
        return mRole;
    }

    public void setmRole(String mRole) {
        this.mRole = mRole;
    }

    public String getmRestaurant() {
        return mRestaurant;
    }

    public void setmRestaurant(String mRestaurant) {
        this.mRestaurant = mRestaurant;
    }

}
