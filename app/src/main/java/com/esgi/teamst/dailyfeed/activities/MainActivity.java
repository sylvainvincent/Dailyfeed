package com.esgi.teamst.dailyfeed.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.esgi.teamst.dailyfeed.R;
import com.esgi.teamst.dailyfeed.adapters.ArticleAdapter;
import com.esgi.teamst.dailyfeed.models.Article;

import java.util.ArrayList;
/**
 * Guideline utilisé : https://github.com/ribot/android-guidelines/blob/master/project_and_code_guidelines.md
 * + Idiomes : http://feanorin.developpez.com/tutoriels/android/idiomes/
 */
public class MainActivity extends AppCompatActivity {

    ListView mlistViewArticles;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.initViews();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        ArrayList<Article> articleArrayList = new ArrayList<>();
        articleArrayList.add(new Article(1,"test","test2",1));
        articleArrayList.add(new Article(2,"bonjour","test2",1));
        mlistViewArticles.setAdapter(new ArticleAdapter(this, articleArrayList));
    }

    private void initViews(){
        mlistViewArticles = (ListView) findViewById(R.id.list_articles);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
}

