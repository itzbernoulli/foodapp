package com.oyinloyeayodeji.www.foodapp;

import android.view.View;

/**
 * Created by Ayo on 24/04/2017.
 */

public interface RecyclerViewItemClickListener {
    public void onClick(View view, int position);

    public void onLongClick(View view, int position);
}
