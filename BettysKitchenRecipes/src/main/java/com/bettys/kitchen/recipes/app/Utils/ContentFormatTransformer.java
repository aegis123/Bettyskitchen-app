package com.bettys.kitchen.recipes.app.Utils;

import com.bettys.kitchen.recipes.app.models.Content;

import org.simpleframework.xml.transform.Transform;

public class ContentFormatTransformer implements Transform<Content> {
    private Content content;

    public ContentFormatTransformer(Content content) {
        this.content = content;
    }

    @Override
    public Content read(String value) throws Exception {
        return content.parse(value);
    }

    @Override
    public String write(Content value) throws Exception {
        return  content.write(value);
    }
}
