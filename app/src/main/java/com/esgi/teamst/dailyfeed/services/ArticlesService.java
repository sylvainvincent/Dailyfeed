package com.esgi.teamst.dailyfeed.services;

import android.app.KeyguardManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.esgi.teamst.dailyfeed.dao.ArticleDAO;
import com.esgi.teamst.dailyfeed.models.Article;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by sylvainvincent on 09/07/16.
 */
public class ArticlesService extends Service {

    private static final String TAG = ArticlesService.class.getSimpleName();
    private ArticleDAO mArticleDao;
    private String mProgrammingId;
    private Timer timer;
    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            test();
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(getApplicationContext(), "oncreate", Toast.LENGTH_LONG).show();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "running", Toast.LENGTH_LONG).show();
        Log.i(TAG, "onStartCommand: running");
        mProgrammingId = intent.getStringExtra("id");
        if (timer == null) {
            timer = new Timer();
            // On commence dans 10 secondes et on actualise toutes les minutes
            timer.schedule(timerTask, 10000, 60000);
        } else {
            timer.cancel();
            timerTask.cancel();
            timer.schedule(timerTask, 10000, 60000);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(this, "test", Toast.LENGTH_LONG).show();
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: destroy");
        try {
            timer.cancel();
            timerTask.cancel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void test() {
        Log.i(TAG, "test: TEST");

        // TODO: 12/07/16 Actualiser la liste avec les nouveaux articles
    }
}
