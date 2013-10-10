package com.bettys.kitchen.recipes.app.models;

import android.net.Uri;

import org.simpleframework.xml.Text;

public class Category {
    public static final Uri CATEGORIES_URI = Uri.parse("content://com.bettys.kitchen.recipes.app.providers/categories");

    public long _id;

    @Text(data = true)
    public String category;

    @Override
    public String toString() {
        return "_id=" + _id + " category=" + category;
    }
}
