package com.bettys.kitchen.recipes.app.models;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;

import java.util.List;

@NamespaceList({
        @Namespace(reference = "http://www.w3.org/2005/Atom", prefix = "atom"),
        @Namespace(reference = "http://purl.org/rss/1.0/modules/syndication/", prefix = "sy")
})
public class Channel {
    public static final String FIELD_TITLE = "title";
    public static final String FIELD_LINK = "link";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_LASTUPDATE = "lastBuildDate";
    public static final String FIELD_LANGUAGE = "language";

    @Element(name = "title", required = true)
    public String title;

    @ElementList(entry = "link", inline = true, required = false)
    public List<Link> links;

    @Element(name = "description", required = true)
    public String description;

    @Element(name = "lastBuildDate", required = true)
    public String lastBuildDate;

    @Element(name = "language", required = true)
    public String language;

    @Element(name = "updatePeriod")
    public String updatePeriod;

    @Element(name = "updateFrequency")
    public int updateFrequency;

    @Element(name = "generator")
    public String generator;


    @ElementList(name = "item", required = true, inline = true)
    public List<Item> items;

    @Override
    public String toString() {
        return title + "\n" + lastBuildDate + "\n";
    }
}
