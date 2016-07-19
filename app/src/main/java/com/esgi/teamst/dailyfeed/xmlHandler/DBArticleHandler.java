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
public class DBArticleHandler extends AsyncTask<Boolean, Void, ArrayList<Article>> {

    private static final String TAG = DBArticleHandler.class.getSimpleName();
    Context mContext;
    ArrayList<Article> showArticleList;
    ListView mlistViewArticles;
    private List<Source> sources;

    public DBArticleHandler(ListView listViewArticles,Context context) {

        mlistViewArticles = listViewArticles;
        mContext = context;
    }

    public void insertArticles(List<Article> articles) {

        ArticleDAO articleDAO = new ArticleDAO(mContext);
        articleDAO.open();

        for (Article article : articles) {
            boolean articleFav = false;
            article.setFavorite(
                    articleFav);
            boolean find = articleDAO.add(article);
            if(find) {
                Log.d("ARTICLE INSERTED","OK");
            }
        }
        articleDAO.close();
    }

    private void showArticles() {

        ArticleDAO articleDAO = new ArticleDAO(mContext);
        articleDAO.open();
        showArticleList =  articleDAO.getAllAvailablesArticles();
        articleDAO.close();
    }

    @Override
    protected ArrayList<Article> doInBackground(Boolean... params) {

        Log.d("BOOL param", String.valueOf(params[0]));

        SourceDAO sourceDAO = new SourceDAO(mContext);
        sourceDAO.open();
        sources = sourceDAO.getAllAvailableSource();

        if (params[0] == Boolean.TRUE){
            Log.i(TAG, "doInBackground: ok");
            ArrayList<Article> articlesList = new XMLParseHandler().loadXmlFeeds(sources);
            insertArticles(articlesList);
        }

        sourceDAO.close();
        showArticles();

        return showArticleList;
    }

    @Override
    protected void onPostExecute(ArrayList<Article> articleResult) {
        //Run on the UI thread after doBackground
        if(articleResult != null && sources != null){
            if(articleResult.size() > 0 && sources.size() > 0){
                mlistViewArticles.setAdapter(new ArticleAdapter(mContext,articleResult,sources));
            }
        }
    }

}
