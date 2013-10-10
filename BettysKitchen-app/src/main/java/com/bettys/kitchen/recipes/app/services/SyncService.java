package com.bettys.kitchen.recipes.app.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.bettys.kitchen.recipes.app.syncadapters.SyncAdapter;

/**
 * Created by Dylan on 30-9-13.
 */
public class SyncService extends Service {
    // Storage for an instance of the sync RecipeListCursorAdapter
    private static SyncAdapter sSyncAdapter = null;
    // Object to use as a thread-safe lock
    private static final Object sSyncAdapterLock = new Object();

    @Override
    public void onCreate() {
        super.onCreate();
        /*
         * Create the sync RecipeListCursorAdapter as a singleton.
         * Set the sync RecipeListCursorAdapter as syncable
         * Disallow parallel syncs
         */
        synchronized (sSyncAdapterLock) {
            if (sSyncAdapter == null) {
                sSyncAdapter = new SyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return sSyncAdapter.getSyncAdapterBinder();
    }
}
