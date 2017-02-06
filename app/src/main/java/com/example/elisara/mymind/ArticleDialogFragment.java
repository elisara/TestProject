package com.example.elisara.mymind;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import org.jsoup.nodes.Element;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by Ellu on 1.2.2017.
 */

public class ArticleDialogFragment extends DialogFragment {

    private FeedItem article;
    private TextView articleTextView, titleView, dateView, sourceView;
    private ArrayList<Element> paragraphList;
    private String shortDate;
    private DateConverter dateConverter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.article_layout, container, false);
        article = (FeedItem) getArguments().getSerializable("article");
        dateConverter = new DateConverter();

        //modify the date type
        try {
           shortDate = dateConverter.convertStringToDate(article.date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //get paragraphs from the article's url as html
        try{
           paragraphList = new JsoupParser().execute(article.url).get();
        }catch (Exception e){

        }

        titleView = (TextView) view.findViewById(R.id.article_title);
        titleView.setText(article.title);

        dateView = (TextView) view.findViewById(R.id.dateview);
        dateView.setText(shortDate);

        sourceView = (TextView) view.findViewById(R.id.sourceview);
        sourceView.setText(article.source);

        articleTextView = (TextView) view.findViewById(R.id.article_text);
        if(paragraphList.size() > 0) {
            for(Element p : paragraphList){
                if(!p.text().equalsIgnoreCase("advertisement")) {
                    articleTextView.append(p.text() + "\n\n");
                }
            }
        }

        return view;
    }




}


