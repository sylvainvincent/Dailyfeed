package com.esgi.teamst.dailyfeed.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.esgi.teamst.dailyfeed.models.Article;
import com.esgi.teamst.dailyfeed.models.Source;

/**
 * Created by sylvainvincent on 05/05/16.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "dailyfeed";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SourceDAO.CREATE_TABLE);
        db.execSQL(SourceDAO.INSERT_DEFAULT);
        db.execSQL(ArticleDAO.CREATE_TABLE);
        db.execSQL(UserDAO.CREATE_TABLE);
        db.execSQL(ArticleFavoriteDAO.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SourceDAO.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ArticleDAO.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + UserDAO.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ArticleFavoriteDAO.TABLE_NAME);

        this.onCreate(db);
    }
}
