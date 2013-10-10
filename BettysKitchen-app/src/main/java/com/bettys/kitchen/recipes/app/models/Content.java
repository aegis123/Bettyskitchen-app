package com.bettys.kitchen.recipes.app.models;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dylan on 9-10-13.
 */
public class Content {
    public String content;
    public List<String> links;

    public Content parse(String value) {
        Document doc = Jsoup.parse(value);
        Elements images = doc.select("p > a > img[src^=http://]");
        links = new ArrayList<String>(images.size());
        for (Element img : images) {
            links.add(img.attr("src"));
        }
        return this;
    }

    public String write(Content value) {
        return content;
    }
}
