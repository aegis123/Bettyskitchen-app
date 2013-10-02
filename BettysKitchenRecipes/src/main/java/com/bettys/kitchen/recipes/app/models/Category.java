package com.bettys.kitchen.recipes.app.models;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Text;

public class Category {
    @Text(data = true)
    public String category;
}
