package com.esgi.teamst.dailyfeed.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.esgi.teamst.dailyfeed.R;
import com.esgi.teamst.dailyfeed.models.Article;
import com.esgi.teamst.dailyfeed.xmlHandler.DBArticleHandler;
import com.esgi.teamst.dailyfeed.xmlHandler.DisplayListHandler;
import com.esgi.teamst.dailyfeed.xmlHandler.XMLParseHandler;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * Guideline utilisé : https://github.com/ribot/android-guidelines/blob/master/project_and_code_guidelines.md
 * + Idiomes : http://feanorin.developpez.com/tutoriels/android/idiomes/
 */
public class newsListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    public static final String TAG = newsListActivity.class.getSimpleName();
    public static final String EXTRA_ARTICLE_ID = "com.esgi.teamst.dailyfeed.EXTRA_ARTICLE_ID";
    public static int mUserId;
    protected FloatingActionButton mFabDisconnection;
    protected FloatingActionButton mFabFavoritesList;
    ListView mListViewArticlesMain;
    SharedPreferences prefs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_news_list);
        this.initViews();
        mUserId = getIntent().getIntExtra(MainActivity.EXTRA_USER_ID, -1);
        prefs = getSharedPreferences("com.esgi.teamst.dailyfeed", MODE_PRIVATE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (prefs.getBoolean("firstrun", true)) {
            // Do first run stuff here then set 'firstrun' as false
            new DBArticleHandler(mListViewArticlesMain,newsListActivity.this).execute(true);
            // using the following line to edit/commit prefs
            prefs.edit().putBoolean("firstrun", false).commit();
        }
        else {
            new DBArticleHandler(mListViewArticlesMain,newsListActivity.this).execute(false);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.list_articles) {
            Log.i(TAG, "onItemClick: ");
            Article article = (Article) parent.getItemAtPosition(position);
            Intent articleActivityIntent = new Intent(newsListActivity.this, ArticleActivity.class);
            articleActivityIntent.putExtra(EXTRA_ARTICLE_ID, article.getId());
            startActivity(articleActivityIntent);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.fab_disconnection:
                intent = new Intent(this, MainActivity.class);
                finish();
                startActivity(intent);
                break;
            case R.id.fab_favorites_list:
                startActivity(new Intent(this, FavoritesListActivity.class));
                break;
        }
    }

    private void initViews() {
        mFabDisconnection = (FloatingActionButton) findViewById(R.id.fab_disconnection);
            mFabDisconnection.setOnClickListener(this);
        mFabFavoritesList = (FloatingActionButton) findViewById(R.id.fab_favorites_list);
            mFabFavoritesList.setOnClickListener(this);
        mListViewArticlesMain = (ListView) findViewById(R.id.list_articles);
        mListViewArticlesMain.setOnItemClickListener(this);
    }
}
