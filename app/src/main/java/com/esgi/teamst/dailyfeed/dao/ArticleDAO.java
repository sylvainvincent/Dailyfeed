package com.esgi.teamst.dailyfeed.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.esgi.teamst.dailyfeed.models.Article;

import java.util.Objects;

/**
 * Created by sylvainvincent on 05/05/16.
 */
public class ArticleDAO extends AbstractDAO<Article> {

    public static final String TABLE_NAME = "article";

    public static final String KEY_ID = "article_id";
    public static final String KEY_TITLE = "article_title";
    public static final String KEY_CONTENT = "article_content";
    public static final String KEY_SOURCE_ID = SourceDAO.KEY_ID;


    public static final String CREATE_TABLE = "CREATE TABLE " +
            TABLE_NAME + "(" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_TITLE + " TEXT," +
            KEY_CONTENT + " TEXT," +
            "FOREIGN KEY(" + KEY_SOURCE_ID + ") REFERENCES " +
            SourceDAO.TABLE_NAME + "(" + SourceDAO.KEY_ID + "))";

    private SQLiteDatabase database = null;
    private DatabaseHandler sqliteHelper;

    public ArticleDAO(Context context) {
        super(context);
    }

    public SQLiteDatabase open() {
        this.database = sqliteHelper.getWritableDatabase();
        return database;
    }

    public void close() {
        database.close();
    }

    @Override
    public void add(Article article) {
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, article.getmTitle());
        values.put(KEY_CONTENT,article.getmContent());
        values.put(KEY_SOURCE_ID, article.getmSource());

        database.insert(TABLE_NAME, null, values);
    }

    @Override
    public Article get(int i) {
        return null;
    }


}
