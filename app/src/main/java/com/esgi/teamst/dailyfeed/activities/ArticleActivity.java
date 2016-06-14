package com.esgi.teamst.dailyfeed.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.esgi.teamst.dailyfeed.R;
import com.esgi.teamst.dailyfeed.dao.ArticleDAO;
import com.esgi.teamst.dailyfeed.models.Article;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

/**
 * Created by sylvainvincent on 09/05/16.
 */
public class ArticleActivity extends AppCompatActivity implements View.OnClickListener {

    protected ImageView imageArticle;
    protected Toolbar toolbarArticle;
    protected CollapsingToolbarLayout collapsingToolbar;
    protected AppBarLayout appBarLayout;
    protected TextView textEventTitle;
    protected TextView textArticleDescription;
    protected TextView textArticleSource;
    protected TextView textArticleDate;
    protected NestedScrollView scroll;
    protected FloatingActionButton fabSave;
    protected FloatingActionButton fabShare;
    protected FloatingActionButton fabWeb;
    protected CoordinatorLayout coordinatorEventDetail;
    int articleId;
    Article article;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_article);
        initView();
        articleId = getIntent().getIntExtra(NewsListActivity.EXTRA_ARTICLE_ID, -1);
        if(articleId != -1){
            ArticleDAO articleDAO = new ArticleDAO(this);
            articleDAO.open();
            article = articleDAO.get(articleId);
            articleDAO.close();
        }


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_save:
                Picasso.with(this).load(R.drawable.ic_action_star_10).into(fabSave);
                break;
            case R.id.fab_web:
                //// TODO: 09/06/16 intent vers un webview (site de l'article)
                break;
            case R.id.fab_share:
                if(article != null) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "test");
                    sendIntent.setType("text/plain");
                    startActivity(sendIntent);
                }
                break;
        }
    }


    private void initView() {
        imageArticle = (ImageView) findViewById(R.id.image_article);
        toolbarArticle = (Toolbar) findViewById(R.id.toolbar_article);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        textEventTitle = (TextView) findViewById(R.id.text_event_title);
        textArticleDescription = (TextView) findViewById(R.id.text_article_description);
        textArticleSource = (TextView) findViewById(R.id.text_article_source);
        textArticleDate = (TextView) findViewById(R.id.text_article_date);
        scroll = (NestedScrollView) findViewById(R.id.scroll);
        fabSave = (FloatingActionButton) findViewById(R.id.fab_save);
        fabSave.setOnClickListener(ArticleActivity.this);
        fabShare = (FloatingActionButton) findViewById(R.id.fab_share);
        fabWeb = (FloatingActionButton) findViewById(R.id.fab_web);
        coordinatorEventDetail = (CoordinatorLayout) findViewById(R.id.coordinator_event_detail);
    }
}
