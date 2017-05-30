package com.oyinloyeayodeji.www.foodapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.oyinloyeayodeji.www.foodapp.Objects.Food;
import com.oyinloyeayodeji.www.foodapp.R;

import java.util.ArrayList;

/**
 * Created by Ayo on 17/04/2017.
 */

public class FoodSelectionAdapter extends ArrayAdapter<Food>{

    Context context;
    LayoutInflater inflater;
    int layoutResourceId;
    float imageWidth;

    public FoodSelectionAdapter(Context context, int layoutResourceId, ArrayList<Food> foodItems) {
        super(context, layoutResourceId, foodItems);
        this.context = context;
        this.layoutResourceId = layoutResourceId;

        float width = ((Activity)context).getWindowManager().getDefaultDisplay().getWidth();
        float margin = (int)convertDpToPixel(10f, (Activity)context);
        // two images, three margins of 10dips
        imageWidth = ((width - (3 * margin)) / 2);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FrameLayout frameView = (FrameLayout) convertView;
        ItemHolder holder;
        Food item = getItem(position);

        if (frameView == null) {
            holder = new ItemHolder();
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            frameView = (FrameLayout) inflater.inflate(layoutResourceId, parent, false);
            ImageView itemImage = (ImageView)frameView.findViewById(R.id.item_image);
            TextView nameText = (TextView)frameView.findViewById(R.id.food_name);
            TextView priceText = (TextView)frameView.findViewById(R.id.food_price);
            TextView descriptionText = (TextView)frameView.findViewById(R.id.food_description);
//            TextView quantityText = (TextView)frameView.findViewById(R.id.quantity);
            holder.itemImage = itemImage;
            holder.itemNameText = nameText;
            holder.itemPriceText = priceText;
            holder.itemDescriptionText = descriptionText;
//            holder.itemQuantity = quantityText;
        } else {
            holder = (ItemHolder) frameView.getTag();
        }

        frameView.setTag(holder);
//        setImageBitmap(item.getmImageResourceId(), holder.itemImage);
        holder.itemNameText.setText(item.getmName());
        holder.itemPriceText.setText("GH"+"\u20B5"+" "+ (item.getmAmount()));
        holder.itemDescriptionText.setText(item.getmDescription());
//        holder.itemImageResource = item.getmImageResourceId();
//        holder.itemQuantity.setText(item.getmQuantity());
        return frameView;
    }

    public static class ItemHolder
    {
        ImageView itemImage;
        public TextView itemNameText;
        public TextView itemPriceText;
        public TextView itemDescriptionText;
        public int itemImageResource;
    }

    // resize the image proportionately so it fits the entire space
    private void setImageBitmap(Integer item, ImageView imageView){
        Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), item);
        float i = ((float) imageWidth) / ((float) bitmap.getWidth());
        float imageHeight = i * (bitmap.getHeight());
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) imageView.getLayoutParams();
        params.height = (int) imageHeight;
        params.width = (int) imageWidth;
        imageView.setLayoutParams(params);
        imageView.setImageResource(item);
    }

    public static float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi/160f);
        return px;
    }

}
