package com.bettys.kitchen.recipes.app.models;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Root;

@Root
public class Rss {
    @Element(name = "channel", required = true)
    public Channel mChannel;

    @Attribute
    public String version;
}
