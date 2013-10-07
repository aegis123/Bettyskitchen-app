package com.bettys.kitchen.recipes.app.activities;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.bettys.kitchen.recipes.app.R;
import com.bettys.kitchen.recipes.app.RecipeApplication;
import com.bettys.kitchen.recipes.app.interfaces.BettysKitchenService;
import com.bettys.kitchen.recipes.app.models.Item;
import com.bettys.kitchen.recipes.app.models.Rss;
import com.bettys.kitchen.recipes.app.syncadapters.SyncAdapter;
import com.mobprofs.retrofit.converters.SimpleXmlConverter;

import java.util.ArrayList;
import java.util.List;

import retrofit.RestAdapter;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

public class MainActivity extends Activity {
    // The account name
    public static final String ACCOUNT = "dummyaccount";
    // Instance fields
    Account mAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAccount = CreateSyncAccount(this);
        Log.d(RecipeApplication.TAG, "MainActivity started onCreate()");
        Log.d(RecipeApplication.TAG, mAccount.name.toString());
        Log.d(RecipeApplication.TAG, mAccount.toString());
        Bundle b = new Bundle();
        b.putString(ContentResolver.SYNC_EXTRAS_EXPEDITED, "");
        server();
        //getContentResolver().requestSync(mAccount, getString(R.string.authority), b);
    }

    private void server() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                Log.d(RecipeApplication.TAG, "SyncAdapter.onPerformSync()");

                // Create a very simple REST adapter which points the GitHub API endpoint.
                RestAdapter restAdapter = new RestAdapter.Builder()
                        .setServer(SyncAdapter.SERVER_URL).setConverter(new SimpleXmlConverter())
                        .build();

                BettysKitchenService server = restAdapter.create(BettysKitchenService.class);
                Rss feed = server.getFeed();
                List<String> categoriesSaved = new ArrayList<String>();
                if (feed != null & feed.mChannel != null && feed.mChannel.items != null && !feed.mChannel.items.isEmpty()) {
                    for (Item item : feed.mChannel.items) {
                        Log.d(RecipeApplication.TAG, "Saving item: " + item.log());
                        Uri uri = cupboard().withContext(RecipeApplication.getContext()).put(Item.ITEM_URI, item);
                        Log.d(RecipeApplication.TAG, uri.toString());
                        // TODO save Categories
                /*long itemId = Long.getLong(uri.getLastPathSegment(), 0);
                for(Category category : item.categories) {
                    if(!categoriesSaved.contains(category.category)) {
                        Uri categoryUri = cupboard().withContext(getContext()).put(Category.CATEGORIES_URI, category);
                        long categoryId = Long.getLong(uri.getLastPathSegment(), 0);
                        category._id = categoryId;
                        categoriesSaved.add(category.category);
                        Log.d(RecipeApplication.TAG, "Saving category: " + category.toString());
                    }
                }*/
                    }
                }
                return null;
            }
        }.execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * Create a new dummy account for the sync adapter
     *
     * @param context The application context
     */
    public static Account CreateSyncAccount(Context context) {
        // Create the account type and default account
        Account newAccount = new Account(
                ACCOUNT, context.getString(R.string.accountType));
        // Get an instance of the Android account manager
        AccountManager accountManager =
                (AccountManager) context.getSystemService(
                        ACCOUNT_SERVICE);
        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
        if (accountManager.addAccountExplicitly(newAccount, null, null)) {
            /*
             * If you don't set android:syncable="true" in
             * in your <provider> element in the manifest,
             * then call context.setIsSyncable(account, AUTHORITY, 1)
             * here.
             */
        } else {
            /*
             * The account exists or some other error occurred. Log this, report it,
             * or handle it internally.
             */
        }
        return newAccount;
    }
}
