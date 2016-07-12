package com.esgi.teamst.dailyfeed.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.esgi.teamst.dailyfeed.R;
import com.esgi.teamst.dailyfeed.dao.ArticleDAO;
import com.esgi.teamst.dailyfeed.dao.ArticleFavoriteDAO;
import com.esgi.teamst.dailyfeed.models.Article;

import java.util.List;

/**
 * Created by sylvainvincent on 11/07/16.
 */
public class FavoritesListActivity extends Activity {

    private static final String TAG = FavoritesListActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_article_list);
        ArticleFavoriteDAO articleDAO = new ArticleFavoriteDAO(this);
        articleDAO.open();
        List<Article> articles = articleDAO.getAll(newsListActivity.mUserId);
        if(articles != null){
            for(Article article : articles){
                Log.i(TAG, "onCreate: " + article.toString());
            }
        }

    }
}
