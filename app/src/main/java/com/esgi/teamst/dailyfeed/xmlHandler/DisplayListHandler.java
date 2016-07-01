package com.esgi.teamst.dailyfeed.xmlHandler;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.esgi.teamst.dailyfeed.adapters.ArticleAdapter;
import com.esgi.teamst.dailyfeed.dao.ArticleDAO;
import com.esgi.teamst.dailyfeed.models.Article;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by tracysablon on 26/06/16.
 */
public class DisplayListHandler extends AsyncTask<String, Void, ArrayList<Article>>{

    ListView mlistViewArticles;
    Context context;

    public DisplayListHandler() {}


    public DisplayListHandler(ListView mlistViewArticles, Context context) {

        this.mlistViewArticles = mlistViewArticles;
        this.context  = context;
    }

    @Override
    protected ArrayList<Article> doInBackground(String... params) {
        //new XMLParseHandler(context).loadXmlFeeds(context);
        //showArticles();
        //Log.d("TEST show size", String.valueOf(showArticleList.size()));
        //Log.d("TEST show articleObj", String.valueOf(showArticleList.get(0).getmTitle()));
        //return showArticleList;
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Article> articleResult) {
        //Run on the UI thread after doBackground
        Log.d("Articles size onPost: ", String.valueOf(articleResult.size()));
        //mlistViewArticles.setAdapter(new ArticleAdapter(context,articleResult));
    }

}
