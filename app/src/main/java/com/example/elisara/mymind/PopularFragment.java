package com.example.elisara.mymind;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by Ellu on 31.1.2017.
 */

public class PopularFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private Header header;
    RecyclerView.LayoutManager mLayoutManager;

    private String url = "https://api.nytimes.com/svc/topstories/v2/home.json?api-key=";
    private final String API_KEY = "9b55d9a601e8429d8cdcfb6f8a14c4fc";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.popular_layout, container, false);
        header = new Header();
        header.setCurrentCategory("top stories");
        recyclerView = (RecyclerView) view.findViewById(R.id.popular_recyclerview);
        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());


        new GetNYTAPI().execute(url, API_KEY);


        return view;
    }
    private class GetNYTAPI extends AsyncTask<String, String, String> {

        private String result;
        protected void onPreExecute() {
            super.onPreExecute();
            FeedList.getInstance().clearList();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                //params[0]=url , params[1]=api_key
                result = makeConnection(params[0] + params[1]);
                try {
                    makeFeedList(result);
                    System.out.println();
                } catch (JSONException e) {
                    Log.e("JSONException1", "Error: " + e.toString());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            recyclerViewAdapter = new RecyclerViewAdapter(getContext(), header, FeedList.getInstance().getFeedList());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(recyclerViewAdapter);
        }

        public String makeConnection(String urli) throws IOException {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            URL url = new URL(urli);
            connection = (HttpURLConnection) url.openConnection();
            if (connection.getResponseCode() == 200) {
                connection.connect();

                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                result = buffer.toString();
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return result;
        }

        public void makeFeedList(String result) throws JSONException {
            //dataList = new ArrayList<String>();
            FeedItem feedItem;
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                feedItem = new FeedItem(object.getString("title"), "", "The New York Times", "", object.getString("url"), "", /**object.getString("published_date"*/ "Wed, 4 Jul 2001 12:08:56 -0700");
                if (FeedList.getInstance().getFeedList() != null) {
                    if (FeedList.getInstance().getFeedList().size() == 0)
                        FeedList.getInstance().addFeedItem(feedItem);
                    else {
                        boolean titleFound = false;
                        for (int k = 0; k < FeedList.getInstance().getFeedList().size(); k++) {
                            if (FeedList.getInstance().getFeedList().get(k).title.equalsIgnoreCase(feedItem.title)) {
                                titleFound = true;
                            }
                        }
                        if (titleFound == false) {
                            FeedList.getInstance().getFeedList().add(0, feedItem);
                        }
                    }
                }
            }
        }

    }

}
