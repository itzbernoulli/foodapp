package com.oyinloyeayodeji.www.foodapp.Objects;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.ServerValue;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Ayo on 20/04/2017.
 */

public class FoodOrder implements Serializable {

    String orderNumber;
    int orderAmount;
    int numberOfMeals;
    Object orderTime;
    Boolean pending;
    ArrayList<Food> orders;

    //Add a pending or order fulfilled field

    public FoodOrder(){}

    public FoodOrder(String orderNumber, int orderAmount, int numberOfMeals, ArrayList<Food> orders) {
        this.orderNumber = orderNumber;
        this.orders = orders;
        this.orderAmount = orderAmount;
        this.numberOfMeals = numberOfMeals;
        this.orderTime = ServerValue.TIMESTAMP;
    }

    public int getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(int orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public ArrayList<Food> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Food> orders) {
        this.orders = orders;
    }

    public int getNumberOfMeals() {
        numberOfMeals = 0;
        for (Food meal: getOrders()){
            numberOfMeals += meal.getmQuantity();
        }
        return numberOfMeals;
    }

    public void setNumberOfMeals(int numberOfMeals) {
        this.numberOfMeals = numberOfMeals;
    }

    @Exclude
    public long getCreatedTimeStampLoong(){
        return (long)orderTime;
    }
}
