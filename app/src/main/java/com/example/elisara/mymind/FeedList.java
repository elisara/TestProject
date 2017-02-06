package com.example.elisara.mymind;

import java.util.ArrayList;

/**
 * Created by Ellu on 1.2.2017.
 */

public class FeedList {


    private ArrayList<FeedItem> feedList;

    private static FeedList ourInstance = new FeedList();

    public static FeedList getInstance() {
        return ourInstance;
    }

    private FeedList() {
        this.feedList = new ArrayList<>();
    }

    public void addFeedItem(FeedItem feedItem){
        feedList.add(feedItem);
    }

    public ArrayList<FeedItem> getFeedList(){
        return feedList;
    }

    public void clearList(){
        feedList.clear();
    }
}
