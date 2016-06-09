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

import java.util.ArrayList;

/**
 * Guideline utilisé : https://github.com/ribot/android-guidelines/blob/master/project_and_code_guidelines.md
 * + Idiomes : http://feanorin.developpez.com/tutoriels/android/idiomes/
 */
public class newsListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public static final String TAG = newsListActivity.class.getSimpleName();
    public static final String EXTRA_ARTICLE_ID = "com.esgi.teamst.dailyfeed.EXTRA_ARTICLE_ID";
    public static final String FEED_URL_1 = "feeds.feedburner.com/Phonandroid";
    public static final String FEED_URL_2 = "feeds.feedburner.com/topito/tip-top";
    public static final String FEED_URL_3 = "feeds.feedburner.com/AndroidMtApplication";

    ListView mListViewArticlesMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);
        this.initViews();
        ArrayList<Article> articleArrayList = new ArrayList<>();
        new XMLParseHandler(mListViewArticlesMain,newsListActivity.this)
                .execute("http://"+FEED_URL_1,"http://"+FEED_URL_2,"http://"+FEED_URL_3);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.list_articles) {
            Log.i(TAG, "onItemClick: ");
            Article article = (Article) parent.getItemAtPosition(position);
            Intent articleActivityIntent = new Intent(newsListActivity.this, ArticleActivity.class);
            articleActivityIntent.putExtra(EXTRA_ARTICLE_ID,article.getmId());
            startActivity(articleActivityIntent);
        }
    }

    private void initViews(){
        mListViewArticlesMain = (ListView) findViewById(R.id.list_articles);
    }
}
