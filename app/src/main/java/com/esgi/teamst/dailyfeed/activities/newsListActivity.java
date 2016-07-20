package com.esgi.teamst.dailyfeed.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.esgi.teamst.dailyfeed.R;
import com.esgi.teamst.dailyfeed.adapters.ArticleAdapter;
import com.esgi.teamst.dailyfeed.dao.ArticleDAO;
import com.esgi.teamst.dailyfeed.dao.SourceDAO;
import com.esgi.teamst.dailyfeed.models.Article;
import com.esgi.teamst.dailyfeed.models.Source;
import com.esgi.teamst.dailyfeed.services.ArticlesService;
import com.esgi.teamst.dailyfeed.xmlHandler.DBArticleHandler;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.List;

/**
 * Guideline utilisé : https://github.com/ribot/android-guidelines/blob/master/project_and_code_guidelines.md
 * + Idiomes : http://feanorin.developpez.com/tutoriels/android/idiomes/
 */
public class newsListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    public static final String TAG = newsListActivity.class.getSimpleName();
    public static final String EXTRA_ARTICLE_ID = "com.esgi.teamst.dailyfeed.extra.ARTICLE_ID";
    public static int mUserId;

    private ArticleAdapter mArticleAdapter;

    private FloatingActionButton mFabDisconnection;
    private FloatingActionButton mFabFavoritesList;
    private FloatingActionButton mFabFilter;
    private FloatingActionButton mFabRefresh;
    private ListView mListViewArticlesMain;
    private TextView mTextEmptyArticleList;

    private SharedPreferences mPrefs = null;
    private List<Source> sourceList;
    private SourceDAO mSourceDAO;
    private String[] mSourceNames;
    private boolean[] mSourceBoolean;
    private boolean[] mSourceBooleanTempo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_news_list);
        this.initViews();
        Intent intent = new Intent(this, ArticlesService.class);
        startService(intent);
        mUserId = getIntent().getIntExtra(MainActivity.EXTRA_USER_ID, -1);
        mPrefs = getSharedPreferences("com.esgi.teamst.dailyfeed", MODE_PRIVATE);

        mSourceDAO = new SourceDAO(this);
        mSourceDAO.open();
        sourceList = mSourceDAO.getAllSource();
        mSourceDAO.close();
        mSourceNames = new String[sourceList.size()];
        mSourceBoolean = new boolean[sourceList.size()];
        mSourceBooleanTempo = new boolean[sourceList.size()];
        int i = 0;
        for(Source source : sourceList){
            mSourceNames[i] = source.getName();
            mSourceBoolean[i] = source.isAvailable();
            mSourceBooleanTempo[i] = source.isAvailable();
            i++;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mPrefs.getBoolean("firstrun", true)) {
            // Do first run stuff here then set 'firstrun' as false
            new DBArticleHandler(mListViewArticlesMain,newsListActivity.this).execute(true);
            // using the following line to edit/commit mPrefs
            mPrefs.edit().putBoolean("firstrun", false).commit();
            // Lancer le service qui récupérer les articles périodiquement
            startService(new Intent(this, ArticlesService.class));
        }
        else {
          //  new DBArticleHandler(mListViewArticlesMain,newsListActivity.this).execute(false);
            ArticleDAO articleDAO = new ArticleDAO(this);
            articleDAO.open();
            List<Article> articles =  articleDAO.getAllAvailablesArticles();
            articleDAO.close();

            SourceDAO sourceDAO = new SourceDAO(this);
            sourceDAO.open();
            List<Source> sources = sourceDAO.getAllSource();
            sourceDAO.close();
            mArticleAdapter = new ArticleAdapter(this, articles, sources);
            if(sources != null && articles != null){
                mListViewArticlesMain.setAdapter(mArticleAdapter);
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.list_articles) {
            Log.i(TAG, "onItemClick: ");
            Article article = (Article) parent.getItemAtPosition(position);
            Intent articleActivityIntent = new Intent(newsListActivity.this, ArticleActivity.class);
            articleActivityIntent.putExtra(EXTRA_ARTICLE_ID, article.getId());
            startActivity(articleActivityIntent);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab_refresh:
                Log.i(TAG, "onClick: refresh");
                    new DBArticleHandler(mListViewArticlesMain,newsListActivity.this).execute(true);
                break;
            case R.id.fab_filter:
                AlertDialog builder = new AlertDialog.Builder(this)
                .setMultiChoiceItems(mSourceNames, mSourceBoolean, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        mSourceBooleanTempo[which] = isChecked;
                    }
                })
                .setPositiveButton(getString(android.R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mSourceDAO.open();
                        for(int i = 0; i < sourceList.size(); i++){
                            sourceList.get(i).setAvailable(mSourceBooleanTempo[i]);
                            boolean success = mSourceDAO.update(sourceList.get(i));
                            if(success){
                                new DBArticleHandler(mListViewArticlesMain,newsListActivity.this).execute(true);
                            }
                        }
                        mSourceDAO.close();
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        for(int i = 0; i < mSourceBooleanTempo.length; i++){
                             mSourceBooleanTempo[i] = mSourceBoolean[i];
                        }
                    }
                })
                .create();
                builder.show();
                break;
            case R.id.fab_disconnection:
                Intent intent = new Intent(this, MainActivity.class);
                finish();
                startActivity(intent);
                break;
            case R.id.fab_favorites_list:
                startActivity(new Intent(this, FavoritesListActivity.class));
                break;
        }
    }

    private void initViews() {
        mFabDisconnection = (FloatingActionButton) findViewById(R.id.fab_disconnection);
            mFabDisconnection.setOnClickListener(this);
        mFabFavoritesList = (FloatingActionButton) findViewById(R.id.fab_favorites_list);
            mFabFavoritesList.setOnClickListener(this);
        mFabFilter = (FloatingActionButton) findViewById(R.id.fab_filter);
            mFabFilter.setOnClickListener(this);
        mFabRefresh = (FloatingActionButton) findViewById(R.id.fab_refresh);
            mFabRefresh.setOnClickListener(this);
        mListViewArticlesMain = (ListView) findViewById(R.id.list_articles);
        mListViewArticlesMain.setOnItemClickListener(this);
        mTextEmptyArticleList = (TextView) findViewById(R.id.text_empty_article_list);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;


    }
}
