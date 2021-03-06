package com.esgi.teamst.dailyfeed.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.esgi.teamst.dailyfeed.models.Article;
import com.esgi.teamst.dailyfeed.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sylvainvincent on 11/07/16.
 */
public class ArticleFavoriteDAO{

    private DatabaseHandler mDatabaseHandler;
    private SQLiteDatabase mSqLiteDatabase;

    public static final String TABLE_NAME = "favorite_article";

    public static final String KEY_USER_ID = UserDAO.KEY_ID;
    public static final String KEY_ARTICLE_ID = ArticleDAO.KEY_ID;

    public static final String[] ALL_COLUMNS = {KEY_USER_ID,
            KEY_ARTICLE_ID};

    public static final String CREATE_TABLE = "CREATE TABLE " +
            TABLE_NAME + "(" +
            KEY_USER_ID + " INTEGER," +
            KEY_ARTICLE_ID + " INTEGER," +
            "PRIMARY KEY (" + KEY_USER_ID + "," + KEY_ARTICLE_ID + ")," +
            "FOREIGN KEY(" + KEY_USER_ID + ") REFERENCES " +
            UserDAO.TABLE_NAME + "(" + UserDAO.KEY_ID + ")," +
            "FOREIGN KEY(" + KEY_ARTICLE_ID + ") REFERENCES " +
            ArticleDAO.TABLE_NAME + "(" + ArticleDAO.KEY_ID + "))";

    public SQLiteDatabase getSQLiteDb() {
        return mSqLiteDatabase;
    }

    public SQLiteDatabase open(){
        mSqLiteDatabase = this.mDatabaseHandler.getWritableDatabase();
        return mSqLiteDatabase;
    }

    public void close(){mDatabaseHandler.close();}

    public ArticleFavoriteDAO(Context context) {
        mDatabaseHandler = new DatabaseHandler(context);
    }

    public boolean add(int userId, int articleId) {
        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID, userId);
        values.put(KEY_ARTICLE_ID, articleId);
        return getSQLiteDb().insert(TABLE_NAME,
                null,
                values) >= 0;
    }

    public boolean get(int userId, int articleId) {

        boolean find = false;

        Cursor mCursor = getSQLiteDb().query(true,
                TABLE_NAME,
                ALL_COLUMNS,
                KEY_ARTICLE_ID + "=" + articleId + " AND " + KEY_USER_ID + "=" + userId,
                null,
                null,
                null,
                null,
                null);

        if (mCursor.getCount() > 0) {
            find = true;
        }

        return find;
    }

    public List<Article> getAllFavoritesArticles(int userId){
        ArrayList<Article> articlesList = null;
        Cursor cursor = getSQLiteDb().rawQuery("SELECT * FROM " + TABLE_NAME + "," + ArticleDAO.TABLE_NAME +
                " WHERE " + ArticleDAO.TABLE_NAME + "." + ArticleDAO.KEY_ID + " =  " + ArticleFavoriteDAO.TABLE_NAME + "." + ArticleFavoriteDAO.KEY_ARTICLE_ID +
                " AND " + ArticleFavoriteDAO.KEY_USER_ID + " = " + userId, null);

        if (cursor.getCount() > 0) {
            articlesList = new ArrayList<>();
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                articlesList.add(cursorToArticle(cursor));
                cursor.moveToNext();
            }

        }

        return articlesList;
    }

    public boolean update(int userId, int articleId) {
        return false;
    }

    public boolean delete(int userId, int articleId) {
        return getSQLiteDb().delete(TABLE_NAME,
                KEY_USER_ID + " = " + userId +
                " AND " +
                KEY_ARTICLE_ID + " = " + articleId,
                null) > 0;
    }

    public Article cursorToArticle(Cursor cursor) {
        Article article = new Article();
        article.setId(cursor.getInt(cursor.getColumnIndex(ArticleDAO.KEY_ID)));
        article.setTitle(cursor.getString(cursor.getColumnIndex(ArticleDAO.KEY_TITLE)));
        article.setContent(cursor.getString(cursor.getColumnIndex(ArticleDAO.KEY_CONTENT)));
        article.setArticleUrl(cursor.getString(cursor.getColumnIndex(ArticleDAO.KEY_URL)));
        article.setThumbnailLink(cursor.getString(cursor.getColumnIndex(ArticleDAO.KEY_THUMBNAIL_LINK)));
        article.setPublishedDate(cursor.getString(cursor.getColumnIndex(ArticleDAO.KEY_PUBLISHED_DATE)));
        article.setFavorite(cursor.getInt(cursor.getColumnIndex(ArticleDAO.KEY_IS_FAVORITE)) > 0);
        article.setSourceId(cursor.getInt(cursor.getColumnIndex(ArticleDAO.KEY_SOURCE_ID)));
        return article;
    }


}
