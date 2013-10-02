package com.bettys.kitchen.recipes.app.syncadapters;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import android.util.Log;

import com.bettys.kitchen.recipes.app.RecipeApplication;
import com.bettys.kitchen.recipes.app.interfaces.BettysKitchenService;
import com.bettys.kitchen.recipes.app.models.Item;
import com.bettys.kitchen.recipes.app.models.Rss;
import com.mobprofs.retrofit.converters.SimpleXmlConverter;

import retrofit.RestAdapter;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;


public class SyncAdapter extends AbstractThreadedSyncAdapter {

    public static String SERVER_URL = "http://bettyskitchen.nl";

    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }

    public SyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        Log.d(RecipeApplication.TAG, "SyncAdapter.onPerformSync()");

        // Create a very simple REST adapter which points the GitHub API endpoint.
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setServer(SyncAdapter.SERVER_URL).setConverter(new SimpleXmlConverter())
                .build();

        BettysKitchenService server = restAdapter.create(BettysKitchenService.class);
        Rss feed = server.getFeed();
        if (feed != null & feed.mChannel != null && feed.mChannel.items != null && !feed.mChannel.items.isEmpty()) {
            for (Item item : feed.mChannel.items) {
                Log.d(RecipeApplication.TAG, "Saving item: " + item.log());
                cupboard().withContext(getContext()).put(Item.ITEM_URI, item);
            }
        }

    }
}
