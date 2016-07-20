package com.esgi.teamst.dailyfeed.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.esgi.teamst.dailyfeed.R;
import com.esgi.teamst.dailyfeed.adapters.ArticleAdapter;
import com.esgi.teamst.dailyfeed.dao.ArticleFavoriteDAO;
import com.esgi.teamst.dailyfeed.dao.SourceDAO;
import com.esgi.teamst.dailyfeed.models.Article;
import com.esgi.teamst.dailyfeed.models.Source;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sylvainvincent on 11/07/16.
 */
public class FavoritesListActivity extends Activity implements AdapterView.OnItemClickListener {

    private static final String TAG = FavoritesListActivity.class.getSimpleName();
    public static final String EXTRA_ARTICLE_ID = "com.esgi.teamst.dailyfeed.extra.ARTICLE_ID";
    public static final String EXTRA_FROM_FAVORITES = "com.esgi.teamst.dailyfeed.extra.FROM_FAVORITES";
    public static final int REQUEST_ARTICLE = 1;

    private ListView mListFavoritesArticles;
    private TextView mTextEmptyList;

    private ArticleFavoriteDAO mArticleFavoriteDAO;
    private ArticleAdapter mArticleAdapter;
    private List<Article> mArticles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_article_list);
        this.initViews();
        mArticleFavoriteDAO = new ArticleFavoriteDAO(this);
        mArticleFavoriteDAO.open();
        mArticles = mArticleFavoriteDAO.getAllFavoritesArticles(newsListActivity.mUserId);
        mArticleFavoriteDAO.close();

        SourceDAO sourceDAO = new SourceDAO(this);
        sourceDAO.open();
        List<Source> sources = sourceDAO.getAllSource();
        sourceDAO.close();
        if(mArticles != null && sources != null){
            mArticleAdapter = new ArticleAdapter(this, mArticles, sources);
            mListFavoritesArticles.setAdapter(mArticleAdapter);
            mListFavoritesArticles.setOnItemClickListener(this);
        }else{
            mTextEmptyList.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.list_favorites_articles) {
            Log.i(TAG, "onItemClick: ");
            Article article = (Article) parent.getItemAtPosition(position);
            Intent articleActivityIntent = new Intent(FavoritesListActivity.this, ArticleActivity.class);
            articleActivityIntent.putExtra(EXTRA_ARTICLE_ID, article.getId());
            articleActivityIntent.putExtra(EXTRA_FROM_FAVORITES, true);
            startActivityForResult(articleActivityIntent, REQUEST_ARTICLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_ARTICLE){
            if(resultCode == RESULT_OK){
                mArticleFavoriteDAO.open();
                mArticles = mArticleFavoriteDAO.getAllFavoritesArticles(newsListActivity.mUserId);
                mArticleFavoriteDAO.close();
                if(mArticles != null){
                    mArticleAdapter.refreshList(mArticles);
                }else{
                    mArticleAdapter = new ArticleAdapter(this, new ArrayList<Article>(), new ArrayList<Source>());
                    mListFavoritesArticles.setAdapter(mArticleAdapter);
                    mTextEmptyList.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private void initViews(){
        mListFavoritesArticles = (ListView) findViewById(R.id.list_favorites_articles);
        mTextEmptyList = (TextView) findViewById(R.id.text_empty_favorite_article_list);
    }



}
