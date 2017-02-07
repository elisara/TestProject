package com.example.elisara.mymind;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Feed lists by categories to a rescyclerview
 * Method to choose the correct image for the header
 */

public class ChannelFeedFragment extends Fragment {

    private String category;
    private Drawable drawable;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private Header header;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        category = getArguments().getString("category");
        View view = inflater.inflate(R.layout.channel_feed_layout, container, false);
        header = new Header();
        header.setCurrentCategory(category);

        System.out.println("FEEDISSÄ LISTAN KOKO: " + FeedList.getInstance().getFeedList().size());

        recyclerView = (RecyclerView) view.findViewById(R.id.feed_recyclerview);
        recyclerViewAdapter = new RecyclerViewAdapter(getContext(), header, FeedList.getInstance().getFeedList());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();

        return view;
    }

    public Drawable imageByCategory(String category, Context context){
        Resources res = context.getResources();
        if(category.toLowerCase().contains("science")){
            drawable = res.getDrawable(R.drawable.science_bg);
        }
        else if(category.toLowerCase().contains("fashion")){
            drawable = res.getDrawable(R.drawable.fashion2);
        }
        else if(category.toLowerCase().contains("finance")){
            drawable = res.getDrawable(R.drawable.finance2);
        }
        else if(category.toLowerCase().contains("travel")){
            drawable = res.getDrawable(R.drawable.travel2);
        }
        else if(category.toLowerCase().contains("entertainment")){
            drawable = res.getDrawable(R.drawable.entertainment2);
        }
        else if(category.toLowerCase().contains("environment")){
            drawable = res.getDrawable(R.drawable.environment2);
        }
        else if(category.toLowerCase().contains("technology")){
            drawable = res.getDrawable(R.drawable.technology2);
        }
        else if(category.toLowerCase().contains("auto")){
            drawable = res.getDrawable(R.drawable.auto2);
        }
        else{
            drawable = res.getDrawable(R.drawable.bg2x);
        }
        return drawable;

    }

}
