package com.bettys.kitchen.recipes.app.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.bettys.kitchen.recipes.app.R;
import com.bettys.kitchen.recipes.app.activities.RecipeActivity;
import com.bettys.kitchen.recipes.app.models.Item;

import java.text.SimpleDateFormat;
import java.util.Locale;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

public class RecipeListCursorAdapter extends CursorAdapter {

    private final LayoutInflater mLayoutInflater;
    private final Context mContext;

    public RecipeListCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    public RecipeListCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.li_item, parent, false);
        return view;
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        final Item item = cupboard().withCursor(cursor).get(Item.class);
        TextView title = (TextView) view.findViewById(R.id.tv_title);
        TextView author = (TextView) view.findViewById(R.id.tv_author);
        TextView pubDate = (TextView) view.findViewById(R.id.tv_pubdate);
        title.setText(item.title);
        author.setText(item.creator);
        String format = "HH:mm dd-MM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        pubDate.setText(sdf.format(item.pubDate));

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RecipeActivity.class);
                intent.putExtra(Item.FIELD_ID, item._id);
                context.startActivity(intent);
            }
        });
    }
}
