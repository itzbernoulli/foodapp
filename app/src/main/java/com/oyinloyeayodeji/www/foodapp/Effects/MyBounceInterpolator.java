package com.oyinloyeayodeji.www.foodapp.Effects;

import android.view.animation.Interpolator;

/**
 * Created by Ayo on 19/04/2017.
 */

public class MyBounceInterpolator implements Interpolator {

    double mAmplitude = 1;
    double mFrequency = 10;

    public MyBounceInterpolator(double mAmplitude, double mFrequency) {
        this.mAmplitude = mAmplitude;
        this.mFrequency = mFrequency;
    }

    @Override
    public float getInterpolation(float time) {
        return (float)(-1 * Math.pow(Math.E, -time/mAmplitude) * Math.cos(mFrequency * time) + 1);
    }
}
