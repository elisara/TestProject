package com.example.elisara.mymind;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by Ellu on 6.2.2017.
 */

public class JsoupParser extends AsyncTask<String, String, ArrayList<Element>>{


    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<Element> doInBackground(String... params) {

        ArrayList<Element> paragraphList = new ArrayList<>();
        try {
            String url = params[0];
            Document doc = null;
            try {
                doc = Jsoup.connect(url).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Elements paragraphs = doc.select("p");
            for(Element p : paragraphs)
                paragraphList.add(p);
        }
        catch (Exception e) {

        }

        return paragraphList;
    }

    @Override
    protected void onPostExecute(ArrayList result) {
        super.onPostExecute(result);



    }
}
