package com.example.elisara.mymind;

import java.io.Serializable;

/**
 * Created by Ellu on 31.1.2017.
 */

public class FeedItem implements Serializable {

    String title, description, source, category, url, imageUrl, date;

    public FeedItem(String title, String description, String source, String category, String url, String imageUrl, String date){
        this.title = title;
        this.description = description;
        this.source = source;
        this.category = category;
        this.url = url;
        this.imageUrl = imageUrl;
        this.date = date;

    }

    public FeedItem(){

    }
}
