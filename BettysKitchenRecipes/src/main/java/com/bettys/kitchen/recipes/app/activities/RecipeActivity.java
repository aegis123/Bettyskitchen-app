package com.bettys.kitchen.recipes.app.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.widget.TextView;

import com.bettys.kitchen.recipes.app.R;
import com.bettys.kitchen.recipes.app.RecipeApplication;
import com.bettys.kitchen.recipes.app.models.Item;

import java.text.SimpleDateFormat;
import java.util.Locale;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

public class RecipeActivity extends FragmentActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private long mRecipeId;
    private TextView mTitle, mAuthor, mPubDate, mContent;
    private Item mItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            getActionBar().setHomeButtonEnabled(true);
        }
        mTitle = (TextView) findViewById(R.id.tv_recipe_title);
        mAuthor = (TextView) findViewById(R.id.tv_recipe_author);
        mPubDate = (TextView) findViewById(R.id.tv_recipe_pubdate);
        mContent = (TextView) findViewById(R.id.tv_recipe_content);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(Item.FIELD_ID)) {
            mRecipeId = intent.getLongExtra(Item.FIELD_ID, 0L);
        }
        getSupportLoaderManager().initLoader(
            R.id.loader_activity_list, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {
            Item.FIELD_ID,
            Item.FIELD_TITLE,
            Item.FIELD_PUBDATE,
            Item.FIELD_AUTHOR,
            Item.FIELD_CONTENT
        };

        String selection = "_id=?";
        String[] selectionArgs = {"" + mRecipeId};
        CursorLoader cursorLoader = new CursorLoader(this,
            Item.getItemUri(mRecipeId), projection, selection, selectionArgs, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        Log.d(RecipeApplication.TAG, "number of entries returned: " + cursor.getCount());
        if (cursor != null && cursor.getCount() > 0) {
            mItem = cupboard().withCursor(cursor).get(Item.class);
            Log.d(RecipeApplication.TAG, mItem.title);
            Log.d(RecipeApplication.TAG, mItem.creator);
            Log.d(RecipeApplication.TAG, mItem.pubDate.toString());
            Log.d(RecipeApplication.TAG, mItem.encoded);
            mTitle.setText(mItem.title);
            mAuthor.setText(mItem.title);
            String format = "HH:mm dd-MM-yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
            mPubDate.setText(sdf.format(mItem.pubDate));
            Spanned content = Html.fromHtml(mItem.encoded);
            mContent.setText(content);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }
}
