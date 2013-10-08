package com.bettys.kitchen.recipes.app.syncadapters;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.bettys.kitchen.recipes.app.Constants;
import com.bettys.kitchen.recipes.app.RecipeApplication;
import com.bettys.kitchen.recipes.app.Utils.DateFormatTransformer;
import com.bettys.kitchen.recipes.app.Utils.Prefs;
import com.bettys.kitchen.recipes.app.db.CupboardSQLiteOpenHelper;
import com.bettys.kitchen.recipes.app.interfaces.BettysKitchenService;
import com.bettys.kitchen.recipes.app.models.Item;
import com.bettys.kitchen.recipes.app.models.Rss;
import com.mobprofs.retrofit.converters.SimpleXmlConverter;

import org.simpleframework.xml.transform.RegistryMatcher;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
        // Maybe you have to correct this or use another / no Locale
        DateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);

        RegistryMatcher matcher = new RegistryMatcher();
        matcher.bind(Date.class, new DateFormatTransformer(format));

        // Create a very simple REST RecipeListCursorAdapter which points the GitHub API endpoint.
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setServer(SyncAdapter.SERVER_URL).setConverter(new SimpleXmlConverter(matcher))
                .build();

        BettysKitchenService server = restAdapter.create(BettysKitchenService.class);
        Rss feed = server.getFeed();
        List<String> categoriesSaved = new ArrayList<String>();
        if (feed != null & feed.mChannel != null && feed.mChannel.items != null && !feed.mChannel.items.isEmpty()) {
            Date lastUpdated = new Date(Prefs.getLong(Constants.PREFS_UPDATE_DATE, 0));
            Log.d(RecipeApplication.TAG, "LastBuildDate: " + feed.mChannel.lastBuildDate.toString());
            Log.d(RecipeApplication.TAG, "LastUpdated: " + lastUpdated.toString());
            if(feed.mChannel.lastBuildDate.before(lastUpdated)) {
                return;
            }
            Log.d(RecipeApplication.TAG, "Feed is newer then last update");

            CupboardSQLiteOpenHelper mDatabaseHelper = new CupboardSQLiteOpenHelper(getContext());
            SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
            db.beginTransaction();
            try {
                for (Item item : feed.mChannel.items) {
                    if(item.pubDate.before(new Date(Prefs.getLong(Constants.PREFS_UPDATE_DATE, 0)))) {
                        Log.d(RecipeApplication.TAG, "item is older then last update so dont save");
                        continue;
                    }
                    cupboard().withDatabase(db).put(item);
                }
                db.setTransactionSuccessful();
            } finally {
                Prefs.putLong(Constants.PREFS_UPDATE_DATE, System.currentTimeMillis());
                db.endTransaction();
            }
        }

    }
}
