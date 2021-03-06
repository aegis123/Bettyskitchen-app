package com.bettys.kitchen.recipes.app.providers;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.bettys.kitchen.recipes.app.R;
import com.bettys.kitchen.recipes.app.RecipeApplication;
import com.bettys.kitchen.recipes.app.db.CupboardSQLiteOpenHelper;
import com.bettys.kitchen.recipes.app.models.Category;
import com.bettys.kitchen.recipes.app.models.Channel;
import com.bettys.kitchen.recipes.app.models.Item;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

public class RecipeProvider extends ContentProvider {
    private CupboardSQLiteOpenHelper mDatabaseHelper;
    private static UriMatcher sMatcher;

    private static String mContentProviderAuth;

    private static final int CHANNEL = 0;
    private static final int CHANNELS = 1;
    private static final int ITEM = 2;
    private static final int ITEMS = 3;
    private static final int CATEGORY = 4;
    private static final int CATEGORIES = 5;

    private static final String BASE_CHANNEL = "channels";
    private static final String BASE_ITEM = "item";
    private static final String BASE_ITEMS = "items";
    private static final String BASE_CATEGORY = "categories";

    private static final Object LOCK = new Object();


    @Override
    public boolean onCreate() {
        if (sMatcher == null) {
            sMatcher = new UriMatcher(UriMatcher.NO_MATCH);
            mContentProviderAuth = getContext().getString(R.string.authority);
            sMatcher.addURI(mContentProviderAuth, BASE_CHANNEL + "/#", CHANNEL);
            sMatcher.addURI(mContentProviderAuth, BASE_CHANNEL, CHANNELS);
            sMatcher.addURI(mContentProviderAuth, BASE_ITEM + "/#", ITEM);
            sMatcher.addURI(mContentProviderAuth, BASE_ITEMS, ITEMS);
            sMatcher.addURI(mContentProviderAuth, BASE_CATEGORY + "/#", CATEGORY);
            sMatcher.addURI(mContentProviderAuth, BASE_CATEGORY, CATEGORIES);
        }
        mDatabaseHelper = new CupboardSQLiteOpenHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        synchronized (LOCK) {
            SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
            Class clz;
            switch (sMatcher.match(uri)) {
                case CHANNEL:
                case CHANNELS:
                    clz = Channel.class;
                    return cupboard().withDatabase(db).query(clz).
                            withProjection(projection).
                            withSelection(selection, selectionArgs).
                            orderBy(sortOrder).
                            getCursor();
                case ITEM:
                case ITEMS:
                    clz = Item.class;
                    Cursor cursor = cupboard().withDatabase(db).query(clz).
                            withProjection(projection).
                            withSelection(selection, selectionArgs).
                            orderBy(sortOrder).
                            getCursor();
                    Log.d(RecipeApplication.TAG, "Provider.query for items " + cursor.getCount() + " entries");
                    return cursor;
                case CATEGORY:
                case CATEGORIES:
                    clz = Category.class;
                    return cupboard().withDatabase(db).query(clz).
                            withProjection(projection).
                            withSelection(selection, selectionArgs).
                            orderBy(sortOrder).
                            getCursor();
                default:
                    throw new IllegalArgumentException("Unknown URI: " + uri);
            }
        }
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        synchronized (LOCK) {
            SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
            Class clz;
            long id = Long.getLong(uri.getLastPathSegment(), 0);
            switch (sMatcher.match(uri)) {
                case CHANNEL:
                case CHANNELS:
                    clz = Channel.class;
                    if (id == 0) {
                        id = cupboard().withDatabase(db).put(clz, values);
                    } else {
                        id = cupboard().withDatabase(db).update(clz, values);
                    }
                    return Uri.parse(mContentProviderAuth + "/" + BASE_CHANNEL + "/" + id);
                case ITEM:
                case ITEMS:
                    clz = Item.class;
                    if (id == 0) {
                        id = cupboard().withDatabase(db).put(clz, values);
                    } else {
                        id = cupboard().withDatabase(db).update(clz, values);
                    }
                    return Uri.parse(mContentProviderAuth + "/" + BASE_ITEM + "/" + id);
                case CATEGORY:
                case CATEGORIES:
                    clz = Category.class;
                    if (id == 0) {
                        id = cupboard().withDatabase(db).put(clz, values);
                    } else {
                        id = cupboard().withDatabase(db).update(clz, values);
                    }
                    return Uri.parse(mContentProviderAuth + "/" + BASE_CATEGORY + "/" + id);
                default:
                    throw new IllegalArgumentException("Unknown URI: " + uri);
            }
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        synchronized (LOCK) {
            SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
            switch (sMatcher.match(uri)) {
                case CHANNEL:
                case CHANNELS:
                    return cupboard().withDatabase(db).delete(Channel.class, selection, selectionArgs);
                case ITEM:
                case ITEMS:
                    return cupboard().withDatabase(db).delete(Item.class, selection, selectionArgs);
                default:
                    throw new IllegalArgumentException("Unknown URI: " + uri);
            }
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        synchronized (LOCK) {
            SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
            Class clz;
            switch (sMatcher.match(uri)) {
                case CHANNEL:
                case CHANNELS:
                    clz = Channel.class;
                    return cupboard().withDatabase(db).update(clz, values, selection, selectionArgs);
                case ITEM:
                case ITEMS:
                    clz = Item.class;
                    return cupboard().withDatabase(db).update(clz, values, selection, selectionArgs);
                default:
                    throw new IllegalArgumentException("Unknown URI: " + uri);
            }
        }
    }
}
