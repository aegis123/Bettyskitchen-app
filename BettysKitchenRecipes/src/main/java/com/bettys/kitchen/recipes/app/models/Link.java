package com.bettys.kitchen.recipes.app.models;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Text;

public class Link {
    @Attribute(required = false)
    public String href;

    @Attribute(required = false)
    public String rel;

    @Attribute(name = "type", required = false)
    public String contentType;

    @Text(required = false)
    public String link;
}
