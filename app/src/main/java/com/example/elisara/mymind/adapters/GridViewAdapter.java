package com.example.elisara.mymind.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.elisara.mymind.R;

import java.util.ArrayList;

/**
 * Gridview adapter to show the gridviews on FollowingFragment and ExploreFragment
 */

public class GridViewAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> list;
    private RelativeLayout relativeLayout;
    private TextView categoryName;
    private Drawable drawable;

    public GridViewAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View myView = convertView;
        Typeface neue = Typeface.createFromAsset(context.getAssets(), "fonts/helvetica-neue.ttf/HelveticaNeue.ttf");

        if (myView == null) {
            myView = LayoutInflater.from(context).inflate(R.layout.gridview_item, parent, false);

        } else {
            myView = convertView;
        }

        relativeLayout = (RelativeLayout) myView.findViewById(R.id.gridview_item_frame);
        int width =  (context.getResources().getDisplayMetrics().widthPixels)/2;
        int height = width;
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width, height);
        relativeLayout.setLayoutParams(layoutParams);

        categoryName = (TextView) myView.findViewById(R.id.gridview_item_text);
        categoryName.setText(list.get(position).toUpperCase());
        categoryName.setTypeface(neue);

        Resources res = context.getResources();

        if(list.get(position).equalsIgnoreCase("SCIENCE"))
            drawable = res.getDrawable(R.drawable.science2);
        else if(list.get(position).equalsIgnoreCase("AUTO"))
            drawable = res.getDrawable(R.drawable.auto2);
        else if(list.get(position).equalsIgnoreCase("ENTERTAINMENT"))
            drawable = res.getDrawable(R.drawable.entertainment2);
        else if(list.get(position).equalsIgnoreCase("ENVIRONMENT"))
            drawable = res.getDrawable(R.drawable.environment2);
        else if(list.get(position).equalsIgnoreCase("FASHION"))
            drawable = res.getDrawable(R.drawable.fashion2);
        else if(list.get(position).equalsIgnoreCase("FINANCE"))
            drawable = res.getDrawable(R.drawable.finance2);
        else if(list.get(position).equalsIgnoreCase("TECHNOLOGY"))
            drawable = res.getDrawable(R.drawable.technology2);
        else if(list.get(position).equalsIgnoreCase("TRAVEL"))
            drawable = res.getDrawable(R.drawable.travel2);

        relativeLayout.setBackground(drawable);

        return myView;
    }

    @Override public int getCount () {
        return list.size();
    }


    @Override public Object getItem ( int position){
        return position;
    }

    @Override public long getItemId ( int position){
        return position;
    }

}
