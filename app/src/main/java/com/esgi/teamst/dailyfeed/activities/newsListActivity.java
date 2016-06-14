package com.esgi.teamst.dailyfeed.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.esgi.teamst.dailyfeed.R;
import com.esgi.teamst.dailyfeed.models.Article;
import com.esgi.teamst.dailyfeed.xmlHandler.XMLParseHandler;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * Guideline utilisé : https://github.com/ribot/android-guidelines/blob/master/project_and_code_guidelines.md
 * + Idiomes : http://feanorin.developpez.com/tutoriels/android/idiomes/
 */
public class NewsListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    public static final String TAG = NewsListActivity.class.getSimpleName();
    public static final String EXTRA_ARTICLE_ID = "com.esgi.teamst.dailyfeed.EXTRA_ARTICLE_ID";
    public static final String FEED_URL_1 = "feeds.feedburner.com/Phonandroid";
    public static final String FEED_URL_2 = "feeds.feedburner.com/topito/tip-top";
    public static final String FEED_URL_3 = "feeds.feedburner.com/AndroidMtApplication";
    protected FloatingActionButton fabDisconnection;

    ListView mListViewArticlesMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_news_list);
        this.initView();
        ArrayList<Article> articleArrayList = new ArrayList<>();
        new XMLParseHandler(mListViewArticlesMain, NewsListActivity.this)
                .execute("http://" + FEED_URL_1, "http://" + FEED_URL_2, "http://" + FEED_URL_3);
        initView();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.list_articles) {
            Log.i(TAG, "onItemClick: ");
            Article article = (Article) parent.getItemAtPosition(position);
            Intent articleActivityIntent = new Intent(NewsListActivity.this, ArticleActivity.class);
            articleActivityIntent.putExtra(EXTRA_ARTICLE_ID, article.getmId());
            startActivity(articleActivityIntent);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab_disconnection:
                Intent intent = new Intent(this, MainActivity.class);
                finish();
                startActivity(intent);
                break;
        }
    }

    private void initView() {
        fabDisconnection = (FloatingActionButton) findViewById(R.id.fab_disconnection);
            fabDisconnection.setOnClickListener(this);
        mListViewArticlesMain = (ListView) findViewById(R.id.list_articles);
    }
}
