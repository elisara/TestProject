package com.example.elisara.mymind;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.text.ParseException;
import java.util.List;

/**
 * Created by Ellu on 5.2.2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter {

    private List<FeedItem> feedList;
    private DateConverter dateConverter = new DateConverter();
    private ChannelFeedFragment channelFeedFragment;
    private Header header;
    private Context context;

    public RecyclerViewAdapter(Context context, Header header,List<FeedItem> feedList) {
        this.feedList = feedList;
        this.header = header;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        if(viewType == 0){
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_header, parent, false);
            holder = new HeaderHolder(itemView);
        }
        else{
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_list_item, parent, false);
            holder = new MyViewHolder(itemView);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final FeedItem feedItem = this.feedList.get(position);
        channelFeedFragment = new ChannelFeedFragment();
        if(holder.getItemViewType() == 0){
            final HeaderHolder headerHolder = (HeaderHolder) holder;
            final Resources res = context.getResources();
            headerHolder.channelName.setText(header.getCurrentCategory().toUpperCase() + " CHANNEL");
            headerHolder.headerImage.setImageDrawable(channelFeedFragment.imageByCategory(header.getCurrentCategory(), context));

            if(header.getCurrentCategory().equalsIgnoreCase("top stories")){
                headerHolder.followBtn.setVisibility(View.INVISIBLE);
                headerHolder.followerCount.setVisibility(View.INVISIBLE);
            }

            if(PreferenceManager.getDefaultSharedPreferences(context).getBoolean(header.getCurrentCategory(), true)) {
                Drawable drawable = res.getDrawable(R.drawable.following_bg);
                headerHolder.followBtn.setBackground(drawable);
                headerHolder.followBtn.setText("Following");
            }
            else {
                Drawable drawable = res.getDrawable(R.drawable.follow_bg);
                headerHolder.followBtn.setBackground(drawable);
                headerHolder.followBtn.setText("Follow");
            }

            headerHolder.followBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(headerHolder.followBtn.getText().equals("Following")) {
                        headerHolder.followBtn.setText("Follow");
                        Drawable drawable = res.getDrawable(R.drawable.follow_bg);
                        headerHolder.followBtn.setBackground(drawable);

                        PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(header.getCurrentCategory(), false).apply();
                    }
                    else{
                        headerHolder.followBtn.setText("Following");
                        Drawable drawable = res.getDrawable(R.drawable.following_bg);
                        headerHolder.followBtn.setBackground(drawable);

                        PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(header.getCurrentCategory(), true).apply();

                    }
                }
            });

        }
        else{
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            myViewHolder.title.setText(feedItem.title);
            myViewHolder.source.setText(feedItem.source);
            try {
                String shortDate = dateConverter.convertStringToDate(feedItem.date);
                myViewHolder.date.setText(shortDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArticleDialogFragment articleDialogFragment = new ArticleDialogFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("article", feedItem);
                    articleDialogFragment.setArguments(bundle);
                    ((MainActivity) context).setFragment(articleDialogFragment);
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return feedList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, source, date;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            source = (TextView) view.findViewById(R.id.source);
            date = (TextView) view.findViewById(R.id.pub_date);
        }
    }

    public static class HeaderHolder extends RecyclerView.ViewHolder {

        public ImageView headerImage;
        public TextView channelName, followerCount, followBtn;

        public HeaderHolder(View view) {
            super(view);
            headerImage = (ImageView) view.findViewById(R.id.header_image);
            channelName = (TextView) view.findViewById(R.id.channel_name);
            followBtn = (TextView) view.findViewById(R.id.follow_btn);
            followerCount = (TextView) view.findViewById(R.id.number_of_followers);

        }
    }
}

