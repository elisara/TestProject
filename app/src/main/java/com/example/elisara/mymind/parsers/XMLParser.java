package com.example.elisara.mymind.parsers;

import android.os.AsyncTask;

import com.example.elisara.mymind.helpers.FeedItem;
import com.example.elisara.mymind.helpers.FeedList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *Parse category feeds from The New York Times XML
 */

public class XMLParser extends AsyncTask<String, String, String>{

    private String title = "title";
    private String link = "link";
    private String description = "description";
    private String source = "source";
    private String pubDate = "pubDate";
    private String urlString = null;
    private XmlPullParserFactory xmlFactoryObject;
    public volatile boolean parsingComplete = true;
    private FeedItem feedItem;

    public XMLParser(String url){
        this.urlString = url;
    }
    public XMLParser(){
    }

    public String getTitle(){
        return title;
    }

    public String getLink(){
        return link;
    }

    public String getDescription(){
        return description;
    }

    public String getSource(){
        return source;
    }

    public String getPubDate(){
        return pubDate;
    }

    public void parseXMLAndStoreIt(XmlPullParser myParser) {
        int event;
        String text=null;

        try {
            event = myParser.getEventType();

            while (event != XmlPullParser.END_DOCUMENT) {
                String name=myParser.getName();
                switch (event){
                    case XmlPullParser.START_TAG:
                        break;

                    case XmlPullParser.TEXT:
                        text = myParser.getText();
                        break;

                    case XmlPullParser.END_TAG:

                        if (name.equals("title")) {
                            title = text;
                        } else if (name.equals("link")) {
                            link = text;
                        } else if (name.equals("description")) {
                            description = text;
                        } else if (name.equals("pubDate")) {
                            pubDate = text;
                        } else {
                        }

                        if(name.equals("item")){
                            feedItem = new FeedItem(title, description, "The New York Times", "category", link, link, pubDate);
                            FeedList.getInstance().addFeedItem(feedItem);
                        }

                        break;
                }
                event = myParser.next();
            }
            parsingComplete = false;
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void onPreExecute() {
        super.onPreExecute();
        FeedList.getInstance().clearList();
    }

    @Override
    protected String doInBackground(String... params) {

        try {
            URL url = new URL(params[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);

            // Starts the query
            conn.connect();
            InputStream stream = conn.getInputStream();

            xmlFactoryObject = XmlPullParserFactory.newInstance();
            XmlPullParser myparser = xmlFactoryObject.newPullParser();

            myparser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            myparser.setInput(stream, null);

            parseXMLAndStoreIt(myparser);
            stream.close();
        }

        catch (Exception e) {
        }

        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

    }

}
