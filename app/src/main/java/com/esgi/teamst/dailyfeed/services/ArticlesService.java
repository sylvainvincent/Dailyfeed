package com.esgi.teamst.dailyfeed.services;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.esgi.teamst.dailyfeed.dao.ArticleDAO;
import com.esgi.teamst.dailyfeed.dao.SourceDAO;
import com.esgi.teamst.dailyfeed.models.Article;
import com.esgi.teamst.dailyfeed.models.Source;
import com.esgi.teamst.dailyfeed.xmlHandler.XMLParseHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by sylvainvincent on 09/07/16.
 */
public class ArticlesService extends Service {

    private static final String TAG = ArticlesService.class.getSimpleName();

    private List<Source> mSources;
    private Timer mTimer;
    TimerTask mTimerTask = new TimerTask() {
        @Override
        public void run() {
            getArticles();
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        mTimer = new Timer();
        mTimer.schedule(mTimerTask, 10000, 60000);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void getArticles() {
        Log.i(TAG, "test: TEST");
        SourceDAO sourceDAO = new SourceDAO(this);
        sourceDAO.open();
        mSources = sourceDAO.getAllSource();

        ArrayList<Article> articlesList = new XMLParseHandler().loadXmlFeeds(mSources);
        insertArticles(articlesList);
    }

    public void insertArticles(List<Article> articles) {

        ArticleDAO articleDAO = new ArticleDAO(this);
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
}
