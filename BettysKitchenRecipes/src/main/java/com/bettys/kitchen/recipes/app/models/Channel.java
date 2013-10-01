package com.bettys.kitchen.recipes.app.models;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

import java.util.List;

public class Channel {
    public static final String FIELD_TITLE = "title";
    public static final String FIELD_LINK = "link";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_LASTUPDATE = "lastBuildDate";
    public static final String FIELD_LANGUAGE = "language";

    @Element(name = "title", required = true)
    public String title;
    @Element(name = "link", required = false)
    public String link;
    @Element(name = "description", required = true)
    public String description;
    @Element(name = "lastBuildDate", required = true)
    public String lastBuildDate;
    @Element(name = "language", required = true)
    public String language;
    @ElementList(name = "item", required = true)
    public List<Item> items;

    @Override
    public String toString() {
        return title + "\n" + lastBuildDate + "\n";
    }
}
