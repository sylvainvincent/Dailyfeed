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

import java.util.ArrayList;
import java.util.Date;

/**
 * Guideline utilisé : https://github.com/ribot/android-guidelines/blob/master/project_and_code_guidelines.md
 * + Idiomes : http://feanorin.developpez.com/tutoriels/android/idiomes/
 */
public class newsListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public static final String TAG = newsListActivity.class.getSimpleName();
    public static final String EXTRA_ARTICLE_ID = "com.esgi.teamst.dailyfeed.EXTRA_ARTICLE_ID";

    ListView mlistViewArticles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);
        this.initViews();
        ArrayList<Article> articleArrayList = new ArrayList<>();
        articleArrayList.add(new Article(1, "test", "test2", "http://img.bfmtv.com/ressources/img/logo/logo-01net-gris.png", new Date(), 1));
        articleArrayList.add(new Article(2, "bonjour", "test2", "http://img.bfmtv.com/ressources/img/logo/logo-01net-gris.png", new Date(), 1));
        mlistViewArticles.setAdapter(new ArticleAdapter(this, articleArrayList));
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

