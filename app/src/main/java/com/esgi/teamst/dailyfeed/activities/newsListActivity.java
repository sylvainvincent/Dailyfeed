package com.esgi.teamst.dailyfeed.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.esgi.teamst.dailyfeed.R;
import com.esgi.teamst.dailyfeed.adapters.ArticleAdapter;
import com.esgi.teamst.dailyfeed.models.Article;
import com.esgi.teamst.dailyfeed.xmlHandler.XMLParseHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Guideline utilisé : https://github.com/ribot/android-guidelines/blob/master/project_and_code_guidelines.md
 * + Idiomes : http://feanorin.developpez.com/tutoriels/android/idiomes/
 */
public class newsListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public static final String TAG = newsListActivity.class.getSimpleName();
    public static final String EXTRA_ARTICLE_ID = "com.esgi.teamst.dailyfeed.EXTRA_ARTICLE_ID";

    ListView mlistViewArticles;
    private List<Article> articleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);
        this.initViews();

        articleList = new ArrayList<Article>();

        try {
            articleList = new XMLParseHandler().execute("http://feeds.feedburner.com/Phonandroid?format=xml").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        mlistViewArticles.setAdapter(new ArticleAdapter(newsListActivity.this, articleList));
        mlistViewArticles.setOnItemClickListener(this);

    }

    private void initViews(){
        mlistViewArticles = (ListView) findViewById(R.id.list_articles);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.list_articles) {
            Log.i(TAG, "onItemClick: ");
            Article article = (Article) parent.getItemAtPosition(position);
            Intent acticleActivityIntent = new Intent(newsListActivity.this, ArticleActivity.class);
            acticleActivityIntent.putExtra(EXTRA_ARTICLE_ID,article.getmId());
            startActivity(acticleActivityIntent);
        }
    }
}
