package com.example.elisara.mymind;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

/**
 * Created by Ellu on 31.1.2017.
 */

public class FollowingFragment extends Fragment {

    private GridView gridView;
    private GridViewAdapter gridViewAdapter;
    private ArrayList<String> categoryList;
    private ChannelFeedFragment channelFeedFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.following_layout, container, false);
        categoryList = new ArrayList<>();


        if(PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("science", true))
            categoryList.add("science");
        if(PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("auto", true))
            categoryList.add("auto");
        if(PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("entertainment", true))
            categoryList.add("entertainment");
        if(PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("environment", true))
            categoryList.add("environment");
        if(PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("fashion", true))
            categoryList.add("fashion");
        if(PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("finance", true))
            categoryList.add("finance");
        if(PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("technology", true))
            categoryList.add("technology");
        if(PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("travel", true))
            categoryList.add("travel");

        channelFeedFragment = new ChannelFeedFragment();

        gridView = (GridView) view.findViewById(R.id.gridview);
        gridViewAdapter = new GridViewAdapter(getContext(),categoryList);
        gridView.setAdapter(gridViewAdapter);

        gridViewAdapter.notifyDataSetChanged();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                String category = categoryList.get(position);
                getNYTXml(category);

                Bundle bundle = new Bundle();
                bundle.putString("category", categoryList.get(position).toLowerCase());
                channelFeedFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("sf")
                        .replace(R.id.frag_container, channelFeedFragment).commit();
            }
        });

        return view;
    }

    public void getNYTXml(String category){
        //Making the category's first letter to be in uppercase
        category.toLowerCase();
        char c[] = category.toCharArray();
        c[0] = Character.toUpperCase(c[0]);
        category = new String(c);

        if(category.equalsIgnoreCase("FASHION"))
            category = "FashionandStyle";
        else if(category.equalsIgnoreCase("AUTO"))
            category = "Automobiles";
        else if(category.equalsIgnoreCase("FINANCE"))
            category = "Economy";
        else if(category.equalsIgnoreCase("ENTERTAINMENT"))
            category = "Music";

        try {
            new XMLParser().execute("http://rss.nytimes.com/services/xml/rss/nyt/"+category+".xml").get();
        }
        catch (Exception e){
        }

    }
}
