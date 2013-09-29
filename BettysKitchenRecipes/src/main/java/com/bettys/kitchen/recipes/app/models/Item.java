package com.bettys.kitchen.recipes.app.models;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.ElementUnion;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;

import java.net.URL;
import java.util.Date;
import java.util.List;

@NamespaceList({
        @Namespace(prefix="content", reference="http://purl.org/rss/1.0/modules/content/"),
        @Namespace(prefix="wfw", reference="http://wellformedweb.org/CommentAPI/"),
        @Namespace(prefix="dc", reference="http://purl.org/dc/elements/1.1/"),
        @Namespace(prefix="slash", reference="http://purl.org/rss/1.0/modules/slash/")
})
public class Item {
    @Element(name = "title")
    public String mTitle;
    @Element(name = "title")
    public String mLink;
    @Element(name = "title")
    public String mComments;
    @Element(name = "title")
    public String mPubDate;
    @Namespace(reference="http://purl.org/dc/elements/1.1/")
    @Element(name = "title")
    public String mAuthor;
    @Element(name = "description")
    public String mDescription;
    @Namespace(reference="http://purl.org/rss/1.0/modules/content/")
    @Element(name = "encoded")
    public String mContent;
    @Namespace(reference="http://wellformedweb.org/CommentAPI/")
    @Element(name = "commentRss")
    public String mCommentRss;
    @Namespace(reference="http://purl.org/rss/1.0/modules/slash/")
    @Element(name = "slash:comments")
    public int mNumberOfComments;
}
