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

/**
 * Created by tracysablon on 18/06/16.
 */
public class DBArticleHandler extends AsyncTask<String, Void, ArrayList<Article>> {

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
    protected ArrayList<Article> doInBackground(String... params) {

        SourceDAO sourceDAO = new SourceDAO(context);
        sourceDAO.open();
        sources = sourceDAO.getAllSource();
        ArrayList<Article> articlesList = new XMLParseHandler().loadXmlFeeds(sources);
        sourceDAO.close();
        insertArticles(articlesList);
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
