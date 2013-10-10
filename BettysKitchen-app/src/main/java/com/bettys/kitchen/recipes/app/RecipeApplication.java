package com.bettys.kitchen.recipes.app;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.bettys.kitchen.recipes.app.models.Item;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;


public class RecipeApplication extends Application {
    public static final String TAG = "BettysKitchen.nl";

    private static Context mContext;

    static {
        Log.d(RecipeApplication.TAG, "Register classes for DB");
        // Register our models.
        cupboard().register(Item.class);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // Hold a reference to ourself; this is safe because the Application class is a singleton
        mContext = this.getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }
}
