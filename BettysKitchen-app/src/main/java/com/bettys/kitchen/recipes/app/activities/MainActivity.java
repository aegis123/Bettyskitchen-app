package com.bettys.kitchen.recipes.app.activities;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ListView;

import com.bettys.kitchen.recipes.app.R;
import com.bettys.kitchen.recipes.app.RecipeApplication;
import com.bettys.kitchen.recipes.app.adapters.RecipeListCursorAdapter;
import com.bettys.kitchen.recipes.app.models.Item;

public class MainActivity extends FragmentActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    // The account name
    public static final String ACCOUNT = "dummyaccount";
    // Sync adapter periodic sync time
    public static final long MILLISECONDS_PER_SECOND = 1000L;
    public static final long SECONDS_PER_MINUTE = 60L;
    public static final long SYNC_INTERVAL_IN_MINUTES = 60L;
    public static final long SYNC_INTERVAL_IN_HOURS = 24L;
    public static final long SYNC_INTERVAL = SYNC_INTERVAL_IN_HOURS * SYNC_INTERVAL_IN_MINUTES * SECONDS_PER_MINUTE * MILLISECONDS_PER_SECOND;

    // Instance fields
    private Account mAccount;
    private ListView mListView;
    private RecipeListCursorAdapter mAdapter;
    private MenuItem refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.listView);
        mAdapter = new RecipeListCursorAdapter(this, null, 0);
        mListView.setAdapter(mAdapter);


        mAccount = CreateSyncAccount(this);
        /*
         * Turn on periodic syncing
         */
        ContentResolver.addPeriodicSync(mAccount, getString(R.string.authority), new Bundle(), SYNC_INTERVAL);
        getSupportLoaderManager().initLoader(
            R.id.loader_activity_list, null, this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.action_refresh:
                // Pass the settings flags by inserting them in a bundle
                Bundle settingsBundle = new Bundle();
                settingsBundle.putBoolean(
                    ContentResolver.SYNC_EXTRAS_MANUAL, true);
                settingsBundle.putBoolean(
                    ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
                /*
                 * Request the sync for the default account, authority, and
                 * manual sync settings
                 */
                ContentResolver.requestSync(mAccount, getString(R.string.authority), settingsBundle);
                item.setVisible(false);
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        refresh = menu.findItem(R.id.action_refresh);
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * Create a new dummy account for the sync RecipeListCursorAdapter
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

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.d(RecipeApplication.TAG, "onCreateLoader");
        setBusy(true);
        String[] projection = {
            Item.FIELD_ID,
            Item.FIELD_TITLE,
            Item.FIELD_PUBDATE,
            Item.FIELD_AUTHOR
        };
        CursorLoader cursorLoader = new CursorLoader(this,
            Item.ITEM_URI, projection, null, null, null);
        return cursorLoader;
    }

    private void setBusy(boolean visisble) {
        if (refresh != null) {
            refresh.setVisible(!visisble);
        }
        setProgressBarIndeterminateVisibility(visisble);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.d(RecipeApplication.TAG, "DB has " + data.getCount() + "entries");
        mAdapter.swapCursor(data);
        setBusy(false);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
}
