package com.example.elisara.mymind;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

/**
 * Show all the categories from resources in recycler view
 */

public class ExploreFragment extends Fragment {

    private GridView gridView;
    private GridViewAdapter gridViewAdapter;
    private ArrayList<String> categoryList;
    private ChannelFeedFragment channelFeedFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.following_layout, container, false);
        categoryList = new ArrayList<>();

        String[] categoryArray = getResources().getStringArray(R.array.all_categories_array);
        for(String a : categoryArray){
            categoryList.add(a);
        }

        channelFeedFragment = new ChannelFeedFragment();

        gridView = (GridView) view.findViewById(R.id.gridview);
        gridViewAdapter = new GridViewAdapter(getContext(),categoryList);
        gridView.setAdapter(gridViewAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                String category = categoryList.get(position).toLowerCase();
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
