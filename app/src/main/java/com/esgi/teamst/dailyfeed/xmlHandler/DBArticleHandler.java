package com.esgi.teamst.dailyfeed.xmlHandler;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.esgi.teamst.dailyfeed.adapters.ArticleAdapter;
import com.esgi.teamst.dailyfeed.dao.ArticleDAO;
import com.esgi.teamst.dailyfeed.dao.SourceDAO;
import com.esgi.teamst.dailyfeed.models.Article;
import com.esgi.teamst.dailyfeed.models.Source;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by tracysablon on 18/06/16.
 */
public class DBArticleHandler extends AsyncTask<Boolean, Void, ArrayList<Article>> {

    Context context;
    ArrayList<Article> showArticleList;
    ListView mlistViewArticles;
    private List<Source> sources;

    public DBArticleHandler(ListView mlistViewArticles,Context context) {

        this.mlistViewArticles = mlistViewArticles;
        this.context = context;
    }

    public void insertArticles(List<Article> articles) {

        ArticleDAO articleDAO = new ArticleDAO(context);
        articleDAO.open();

        for (Article article : articles) {
            boolean articleFav = false;
            article.setmIsFavorite(articleFav);
            boolean find = articleDAO.add(article);
            if(find) {
                Log.d("ARTICLE INSERTED","OK");
            }
        }
        articleDAO.close();
    }

    private void showArticles() {

        ArticleDAO articleDAO = new ArticleDAO(context);
        articleDAO.open();
        showArticleList =  articleDAO.getAllArticles();
        articleDAO.close();
    }

    @Override
    protected ArrayList<Article> doInBackground(Boolean... params) {

        Log.d("BOOL param", String.valueOf(params[0]));

        SourceDAO sourceDAO = new SourceDAO(context);
        sourceDAO.open();
        sources = sourceDAO.getAllSource();

        if (params[0] == Boolean.TRUE){
            ArrayList<Article> articlesList = new XMLParseHandler().loadXmlFeeds(sources);
            insertArticles(articlesList);
        }

        sourceDAO.close();
        showArticles();
        Log.d("TEST show size", String.valueOf(showArticleList.size()));

        return showArticleList;
    }

    @Override
    protected void onPostExecute(ArrayList<Article> articleResult) {
        //Run on the UI thread after doBackground
        Log.d("Articles size onPost: ", String.valueOf(articleResult.size()));
        mlistViewArticles.setAdapter(new ArticleAdapter(context,articleResult,sources));
    }




}
