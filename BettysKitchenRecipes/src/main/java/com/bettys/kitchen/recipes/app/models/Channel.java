package com.bettys.kitchen.recipes.app.models;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Root;

import java.net.URL;
import java.util.Date;
import java.util.List;

@NamespaceList({
        @Namespace(prefix="atom", reference="http://www.w3.org/2005/Atom"),
        @Namespace(prefix="sy", reference="http://purl.org/rss/1.0/modules/syndication/")
})
public class Channel {
    public static final String FIELD_TITLE = "title";
    public static final String FIELD_LINK = "link";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_LASTUPDATE = "lastBuildDate";
    public static final String FIELD_LANGUAGE = "language";

    @Element(name = "title", required = true)
    public String mTitle;
    @Element(name = "link", required = true)
    public String mLink;
    @Element(name = "description", required = true)
    public String mDescription;
    @Element(name = "lastBuildDate", required = true)
    public String mLastBuildDate;
    @Element(name = "language", required = true)
    public String mLanguage;
    @ElementList(name = "item", required = true)
    public List<Item> mItems;
}
