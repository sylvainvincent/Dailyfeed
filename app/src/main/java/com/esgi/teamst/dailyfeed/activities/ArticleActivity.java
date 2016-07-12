package com.esgi.teamst.dailyfeed.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.esgi.teamst.dailyfeed.R;
import com.esgi.teamst.dailyfeed.dao.ArticleDAO;
import com.esgi.teamst.dailyfeed.dao.ArticleFavoriteDAO;
import com.esgi.teamst.dailyfeed.models.Article;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

/**
 *
 */
public class ArticleActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_ARTICLE_URL = "com.esgi.teamst.dailyfeed.EXTRA_ARTICLE_URL";

    private ImageView mImageArticle;
    private Toolbar mToolbarArticle;
    private CollapsingToolbarLayout mCollapsingToolbar;
    private AppBarLayout mAppBarLayout;
    private TextView mTextEventTitle;
    private TextView mTextArticleDescription;
    private TextView mTextArticleSource;
    private TextView mTextArticleDate;
    private NestedScrollView scroll;
    private FloatingActionButton mFabSave;
    private FloatingActionButton mFabShare;
    private FloatingActionButton mFabWeb;
    private CoordinatorLayout mCoordinatorEventDetail;

    private ArticleDAO articleDAO;
    private int mArticleId;
    private Article mArticle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_article);
        this.initViews();
        mArticleId = getIntent().getIntExtra(newsListActivity.EXTRA_ARTICLE_ID, -1);
        if(mArticleId != -1){
            articleDAO = new ArticleDAO(this);
            articleDAO.open();
            mArticle = articleDAO.get(mArticleId);
            articleDAO.close();
            this.fillLayout();
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_save:
                if(!mArticle.isFavorite()){
                    /*mArticle.setFavorite(true);
                    articleDAO.open();
                    boolean update = articleDAO.update(mArticle);
                    articleDAO.close();*/
                    ArticleFavoriteDAO articleFavoriteDAO = new ArticleFavoriteDAO(this);
                    articleFavoriteDAO.open();
                    boolean update = articleFavoriteDAO.add(newsListActivity.mUserId, mArticleId);
                    articleDAO.close();

                    if(update){
                        Picasso.with(this).load(R.drawable.ic_action_star_filled).into(mFabSave);
                    }else{
                        mArticle.setFavorite(false);
                    }
                }else{
                    mArticle.setFavorite(false);
                    articleDAO.open();
                    boolean update = articleDAO.update(mArticle);
                    articleDAO.close();
                    if(update){
                        Picasso.with(this).load(R.drawable.ic_action_star_empty).into(mFabSave);
                    }else{
                        mArticle.setFavorite(true);
                    }
                }

                break;
            case R.id.fab_web:
                //// TODO: 09/06/16 intent vers un webview (site de l'article)
                if(mArticle.getArticleUrl() != null){
                    try{
                        Intent intent = new Intent(this, WebArticleActivity.class);
                        intent.putExtra(EXTRA_ARTICLE_URL,mArticle.getArticleUrl());
                        startActivity(intent);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else{
                    Snackbar.make(mCoordinatorEventDetail, getString(R.string.error_article_url_is_not_exist),Snackbar.LENGTH_SHORT).show();
                }

                break;
            case R.id.fab_share:
                if(mArticle != null) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "test");
                    sendIntent.setType("text/plain");
                    startActivity(sendIntent);
                }
                break;
        }
    }

    private void initViews() {
        mImageArticle = (ImageView) findViewById(R.id.image_article);
        mToolbarArticle = (Toolbar) findViewById(R.id.toolbar_article);
        mCollapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        mTextEventTitle = (TextView) findViewById(R.id.text_event_title);
        mTextArticleDescription = (TextView) findViewById(R.id.text_article_description);
        mTextArticleSource = (TextView) findViewById(R.id.text_article_source);
        mTextArticleDate = (TextView) findViewById(R.id.text_article_date);
        scroll = (NestedScrollView) findViewById(R.id.scroll);
        mFabSave = (FloatingActionButton) findViewById(R.id.fab_save);
        mFabSave.setOnClickListener(ArticleActivity.this);
        mFabShare = (FloatingActionButton) findViewById(R.id.fab_share);
        mFabShare.setOnClickListener(this);
        mFabWeb = (FloatingActionButton) findViewById(R.id.fab_web);
        mFabWeb.setOnClickListener(this);
        mCoordinatorEventDetail = (CoordinatorLayout) findViewById(R.id.coordinator_event_detail);
    }

    private void fillLayout(){
        mTextEventTitle.setText(mArticle.getTitle());
        mTextArticleDescription.setText(mArticle.getContent());
        mTextArticleDate.setText(mArticle.getPublishedDate());
        if(mArticle.isFavorite()){
            Picasso.with(this).load(R.drawable.ic_action_star_filled).into(mFabSave);
        }
    }
}
