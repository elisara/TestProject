package com.example.elisara.mymind;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.elisara.mymind.helpers.DateConverter;
import com.example.elisara.mymind.helpers.FeedItem;
import com.example.elisara.mymind.parsers.JsoupParser;

import org.jsoup.nodes.Element;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * Show one article
 *
 */

public class ArticleFragment extends Fragment {

    private FeedItem article;
    private TextView articleTextView, titleView, dateView, categoryView, sourceView;
    private ArrayList<Element> paragraphList;
    private String shortDate, category;
    private DateConverter dateConverter;
    private boolean fromPopular;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.article_layout, container, false);
        article = (FeedItem) getArguments().getSerializable("article");
        fromPopular = getArguments().getBoolean("popular");
        category = getArguments().getString("category");

        dateConverter = new DateConverter();
        Typeface bold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/helvetica-bold.otf/Helvetica-Bold.otf");
        Typeface neue = Typeface.createFromAsset(getActivity().getAssets(), "fonts/helvetica-neue.ttf/HelveticaNeue.ttf");

        //modify the date type
        if(!fromPopular) {
            try {
                shortDate = dateConverter.convertStringToDate(article.date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        else{
            try {
                shortDate = dateConverter.convertStringToDate2(article.date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        //get paragraphs from the article's url as html
        try{
           paragraphList = new JsoupParser().execute(article.url).get();
        }catch (Exception e){

        }
        articleTextView = (TextView) view.findViewById(R.id.article_text);
        articleTextView.setTypeface(neue);
        if(paragraphList.size() > 0) {
            for(Element p : paragraphList){
                if(!p.text().equalsIgnoreCase("advertisement")) {
                    articleTextView.append(p.text() + "\n\n");
                }
            }
        }

        titleView = (TextView) view.findViewById(R.id.article_title);
        titleView.setText(article.title);
        titleView.setTypeface(bold);

        dateView = (TextView) view.findViewById(R.id.dateview);
        dateView.setText(shortDate);
        dateView.setTypeface(neue);

        sourceView = (TextView) view.findViewById(R.id.sourceview);
        sourceView.setText(article.source);
        dateView.setTypeface(neue);

        categoryView = (TextView) view.findViewById(R.id.article_category);
        categoryView.setText(category.toUpperCase());
        categoryView.setTypeface(neue);

        return view;
    }




}


