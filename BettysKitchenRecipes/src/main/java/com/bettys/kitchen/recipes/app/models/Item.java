package com.bettys.kitchen.recipes.app.models;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;

@NamespaceList({
        @Namespace(prefix = "content", reference = "http://purl.org/rss/1.0/modules/content/"),
        @Namespace(prefix = "wfw", reference = "http://wellformedweb.org/CommentAPI/"),
        @Namespace(prefix = "dc", reference = "http://purl.org/dc/elements/1.1/"),
        @Namespace(prefix = "slash", reference = "http://purl.org/rss/1.0/modules/slash/")
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

    @Element(name = "title")
    public String mTitle;
    @Element(name = "link")
    public String mLink;
    @Element(name = "comments")
    public String mComments;
    @Element(name = "pubDate")
    public String mPubDate;
    @Namespace(reference = "http://purl.org/dc/elements/1.1/")
    @Element(name = "creator")
    public String mAuthor;
    @Element(name = "description")
    public String mDescription;
    @Namespace(reference = "http://purl.org/rss/1.0/modules/content/")
    @Element(name = "encoded")
    public String mContent;
    @Namespace(reference = "http://wellformedweb.org/CommentAPI/")
    @Element(name = "commentRss")
    public String mCommentRss;
    @Namespace(reference = "http://purl.org/rss/1.0/modules/slash/")
    @Element(name = "comments")
    public int mNumberOfComments;

    @Override
    public String toString() {
        return mTitle + "\n" + mAuthor + "\n" + mPubDate + "\n";
    }
}
