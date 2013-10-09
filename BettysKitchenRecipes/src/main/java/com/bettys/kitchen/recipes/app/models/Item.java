package com.bettys.kitchen.recipes.app.models;

import android.net.Uri;

import com.bettys.kitchen.recipes.app.R;
import com.bettys.kitchen.recipes.app.RecipeApplication;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;

import java.util.Date;
import java.util.List;

@NamespaceList({
    @Namespace(prefix = "content", reference = "http://purl.org/rss/1.0/modules/content/"),
    @Namespace(prefix = "wfw", reference = "http://wellformedweb.org/CommentAPI/"),
    @Namespace(prefix = "dc", reference = "http://purl.org/dc/elements/1.1/"),
    @Namespace(reference = "http://purl.org/rss/1.0/modules/slash/", prefix = "slash")
})
public class Item {
    public static final String FIELD_ID = "_id";
    public static final String FIELD_TITLE = "title";
    public static final String FIELD_LINK = "link";
    public static final String FIELD_COMMENTS = "comments";
    public static final String FIELD_PUBDATE = "pubDate";
    public static final String FIELD_AUTHOR = "creator";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_CONTENT = "encoded";
    public static final String FIELD_COMMENTRSS = "commentRss";
    public static final String FIELD_NUMBEROFCOMMENTS = "numberOfComments";

    public static final Uri ITEM_URI = Uri.parse("content://" + RecipeApplication.getContext().getString(R.string.authority) + "/items");

    public Long _id;

    @Element(name = "title")
    public String title;

    @Element(name = "link")
    public String link;

    @Element(name = "guid")
    public String guid;

    @Element(name = "pubDate")
    public Date pubDate;

    @ElementList(name = "category", inline = true)
    public transient List<Category> categories;

    @Element(name = "creator")
    public String creator;

    @Element(name = "description")
    public String description;

    @Element(name = "encoded", data = true)
    public Content encoded;

    @Element(name = "commentRss")
    public String commentRss;

    @Override
    public String toString() {
        return title + "\n" + creator + "\n" + pubDate + "\n";
    }

    public String log() {
        return title;
    }

    public static Uri getItemUri(long id) {
        return Uri.parse("content://" + RecipeApplication.getContext().getString(R.string.authority) + "/item/" + id);
    }
}
