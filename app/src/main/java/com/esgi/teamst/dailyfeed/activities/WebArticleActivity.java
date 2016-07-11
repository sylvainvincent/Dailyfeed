package com.esgi.teamst.dailyfeed.activities;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import com.esgi.teamst.dailyfeed.R;

/**
 *
 */
public class WebArticleActivity extends Activity {

    private WebView mWebArticle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_article_web);
        this.initViews();
        String articleUrl = getIntent().getStringExtra(ArticleActivity.EXTRA_ARTICLE_URL);
        try{
            mWebArticle.loadUrl(articleUrl);
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    private void initViews() {
        mWebArticle = (WebView) findViewById(R.id.web_article);
    }
}
