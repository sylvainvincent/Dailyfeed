package com.esgi.teamst.dailyfeed.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.esgi.teamst.dailyfeed.R;
import com.esgi.teamst.dailyfeed.dao.ArticleDAO;
import com.esgi.teamst.dailyfeed.dao.ArticleFavoriteDAO;
import com.esgi.teamst.dailyfeed.models.Article;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */
public class ArticleActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = ArticleActivity.class.getSimpleName();
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

    private ArticleFavoriteDAO mArticleFavoriteDAO;
    private ArticleDAO mArticleDAO;
    private int mArticleId;
    private boolean mFromFavorites;
    private boolean mArticleFavoriteFind;
    private Article mArticle;
    private boolean favorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_article);
        this.initViews();
        mArticleId = getIntent().getIntExtra(newsListActivity.EXTRA_ARTICLE_ID, -1);
        mFromFavorites = getIntent().getBooleanExtra(FavoritesListActivity.EXTRA_FROM_FAVORITES, favorite);
        if(mArticleId != -1){
            mArticleDAO = new ArticleDAO(this);
            mArticleDAO.open();
            mArticle = mArticleDAO.get(mArticleId);
            mArticleDAO.close();

            mArticleFavoriteDAO = new ArticleFavoriteDAO(this);
            mArticleFavoriteDAO.open();
            mArticleFavoriteFind = mArticleFavoriteDAO.get(newsListActivity.mUserId, mArticleId);
            mArticleFavoriteDAO.close();

            this.fillLayout();
        }



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_save:
                if(!favorite){
                    ArticleFavoriteDAO articleFavoriteDAO = new ArticleFavoriteDAO(this);
                    articleFavoriteDAO.open();
                    boolean addFavorite = articleFavoriteDAO.add(newsListActivity.mUserId, mArticleId);
                    articleFavoriteDAO.close();

                    if(addFavorite){
                        Picasso.with(this).load(R.drawable.ic_action_star_filled).into(mFabSave);
                        favorite = true;

                    }else{
                        Snackbar.make(mCoordinatorEventDetail, getString(R.string.error_article_is_not_save),Snackbar.LENGTH_SHORT).show();
                    }

                }else{
                    ArticleFavoriteDAO articleFavoriteDAO = new ArticleFavoriteDAO(this);
                    articleFavoriteDAO.open();
                    boolean deleteFavorite = articleFavoriteDAO.delete(newsListActivity.mUserId, mArticleId);
                    articleFavoriteDAO.close();

                    if(deleteFavorite){
                        Picasso.with(this).load(R.drawable.ic_action_star_empty).into(mFabSave);
                        favorite = false;
                    }else{
                        Snackbar.make(mCoordinatorEventDetail, getString(R.string.error_article_is_not_delete),Snackbar.LENGTH_SHORT).show();
                    }
                }
                // si l'activité précendent est FavoritesListActivity alors on actualise la liste
                if(mFromFavorites){
                    setResult(RESULT_OK);
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
                    sendIntent.putExtra(Intent.EXTRA_TEXT, mArticle.getArticleUrl());
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


        mTextArticleDescription.setText(Html.fromHtml(mArticle.getContent()));
        mTextArticleDate.setText(mArticle.getPublishedDate());
        Picasso.with(this).load(mArticle.getThumbnailLink()).placeholder(R.drawable.article_picture).into(mImageArticle);
        if(mArticleFavoriteFind){
            Picasso.with(this).load(R.drawable.ic_action_star_filled).into(mFabSave);
            favorite = true;
        }

    }
}
