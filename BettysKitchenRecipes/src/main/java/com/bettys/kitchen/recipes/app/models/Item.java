package com.bettys.kitchen.recipes.app.models;

import android.net.Uri;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

import java.util.List;

@NamespaceList({
        @Namespace(prefix = "content", reference = "http://purl.org/rss/1.0/modules/content/"),
        @Namespace(prefix = "wfw", reference = "http://wellformedweb.org/CommentAPI/"),
        @Namespace(prefix = "dc", reference = "http://purl.org/dc/elements/1.1/"),
        @Namespace(reference = "http://purl.org/rss/1.0/modules/slash/", prefix = "slash")
})
public class Item {
    public static final String FIELD_TITLE = "title";
    public static final String FIELD_LINK = "link";
    public static final String FIELD_COMMENTS = "comments";
    public static final String FIELD_PUBDATE = "pubDate";
    public static final String FIELD_AUTHOR = "author";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_CONTENT = "content";
    public static final String FIELD_COMMENTRSS = "commentRss";
    public static final String FIELD_NUMBEROFCOMMENTS = "numberOfComments";

    public static final Uri ITEM_URI = Uri.parse("content://com.bettys.kitchen.recipes.app.providers/items");

    public long _id;

    @Element(name = "title")
    public String title;

    @Element(name = "link")
    public String link;

    @Element(name = "guid")
    public String guid;

    @Element(name = "pubDate")
    public String pubDate;

    @ElementList(name = "category", inline = true)
    public transient List<Category> categories;

    @Element(name = "creator")
    public String creator;

    @Element(name = "description")
    public String description;

    @Element(name = "encoded", data=true)
    public String encoded;

    @Element(name = "commentRss")
    public String commentRss;

    @Override
    public String toString() {
        return title + "\n" + creator + "\n" + pubDate + "\n";
    }

    public String log() {
        return title;
    }
}
