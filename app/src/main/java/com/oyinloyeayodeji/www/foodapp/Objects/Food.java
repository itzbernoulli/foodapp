package com.oyinloyeayodeji.www.foodapp.Objects;

import java.io.Serializable;

/**
 * Created by Ayo on 17/04/2017.
 */

public class Food implements Serializable{

    private String mImageUrl;
    private  String mName;
    private String mDescription;
    private int mAmount;
    private int mQuantity;

    public Food(){}

    public Food(String mImageUrl, String mName, String mDescription, int mAmount) {
        this.mImageUrl = mImageUrl;
        this.mName = mName;
        this.mDescription = mDescription;
        this.mAmount = mAmount;
        this.mQuantity = 1;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public int getmAmount() {
        return mAmount;
    }

    public void setmAmount(int mAmount) {
        this.mAmount = mAmount;
    }

    public int getmQuantity(){
        return mQuantity;
    }

    public void addToQuantity(){
        this.mQuantity += 1;
    }

    public void removeFromQuantity(){
        if(this.mQuantity > 1){
            this.mQuantity -= 1;
        }
    }

}
