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
import com.bettys.kitchen.recipes.app.models.Channel;
import com.bettys.kitchen.recipes.app.models.Item;
import com.bettys.kitchen.recipes.app.models.Rss;

import java.util.List;

import retrofit.RestAdapter;


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
        // Create a very simple REST adapter which points the GitHub API endpoint.
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setServer(SERVER_URL)
                .build();
        BettysKitchenService server = restAdapter.create(BettysKitchenService.class);

        Rss feed = server.getFeed();

        if (feed != null) {
            Channel chan = feed.mChannel;
            if (chan != null) {
                Log.d(RecipeApplication.TAG, chan.toString());
                List<Item> items = chan.items;
                if (items != null && !items.isEmpty()) {
                    for (Item item : items) {
                        if(item != null) {
                            Log.d(RecipeApplication.TAG, item.toString());
                        }
                    }
                }
            }
        }
    }
}
