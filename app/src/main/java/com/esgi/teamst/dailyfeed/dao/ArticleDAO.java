package com.esgi.teamst.dailyfeed.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.esgi.teamst.dailyfeed.models.Article;

/**
 * Created by sylvainvincent on 05/05/16.
 */
public class ArticleDAO extends AbstractDAO<Article> {

    public static final String TABLE_NAME = "article";

    public static final String KEY_ID = "article_id";
    public static final String KEY_TITLE = "article_title";
    public static final String KEY_CONTENT = "article_content";
    public static final String KEY_THUMBNAIL_LINK = "article_thumbnail_link";
    public static final String KEY_PUBLISHED_DATE = "article_published_date";
    public static final String KEY_IS_FAVORITE = "article_favorite";
    public static final String KEY_SOURCE_ID = SourceDAO.KEY_ID;

    public static final String CREATE_TABLE = "CREATE TABLE " +
            TABLE_NAME + "(" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_TITLE + " TEXT," +
            KEY_CONTENT + " TEXT," +
            KEY_THUMBNAIL_LINK + " TEXT," +
            KEY_PUBLISHED_DATE + " TEXT," +
            KEY_IS_FAVORITE + " INTEGER," +
            KEY_SOURCE_ID + " INTEGER," +
            "FOREIGN KEY(" + KEY_SOURCE_ID + ") REFERENCES " +
            SourceDAO.TABLE_NAME + "(" + SourceDAO.KEY_ID + "))";

    public static final String[] ALL_COLUMNS = {KEY_ID,
            KEY_TITLE,
            KEY_CONTENT,
            KEY_THUMBNAIL_LINK,
            KEY_PUBLISHED_DATE,
            KEY_IS_FAVORITE,
            KEY_SOURCE_ID};

    public ArticleDAO(Context context) {
        super(context);
    }

    @Override
    public boolean add(Article article) {
        return getSQLiteDb().insert(TABLE_NAME,
                null,
                objectToContentValues(article)) >= 0;
    }

    @Override
    public Article get(int id) {
        Cursor mCursor = getSQLiteDb().query(true,
                TABLE_NAME,
                ALL_COLUMNS,
                KEY_ID + "=" + id,
                null,
                null,
                null,
                null,
                null);

        if (mCursor.getCount() > 0) {
            mCursor.moveToFirst();
            return cursorToObject(mCursor);
        } else {
            return null;
        }
    }

    @Override
    public boolean update(Article article) {
        return getSQLiteDb().update(TABLE_NAME,
                objectToContentValues(article),
                KEY_ID + "=" + article.getmId(),
                null) > 0;
    }


    @Override
    public boolean delete(Article article) {
        return getSQLiteDb().delete(TABLE_NAME,
                KEY_ID + "=" + article.getmId(),
                null) > 0;
    }

    @Override
    public Article cursorToObject(Cursor cursor) {
        Article article = new Article();
        article.setmId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
        article.setmTitle(cursor.getString(cursor.getColumnIndex(KEY_TITLE)));
        article.setmContent(cursor.getString(cursor.getColumnIndex(KEY_CONTENT)));
        article.setmThumbnailLink(cursor.getString(cursor.getColumnIndex(KEY_THUMBNAIL_LINK)));
        article.setmPublishedDate(cursor.getString(cursor.getColumnIndex(KEY_PUBLISHED_DATE)));
        article.setmIsFavorite(cursor.getInt(cursor.getColumnIndex(KEY_IS_FAVORITE)) > 0);
        article.setmSourceId(cursor.getInt(cursor.getColumnIndex(KEY_SOURCE_ID)));
        return article;
    }

    @Override
    public ContentValues objectToContentValues(Article article) {
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, article.getmTitle());
        values.put(KEY_CONTENT,article.getmContent());
        values.put(KEY_THUMBNAIL_LINK,article.getmContent());
        values.put(KEY_PUBLISHED_DATE,article.getmContent());
        values.put(KEY_IS_FAVORITE,article.getmContent());
        values.put(KEY_SOURCE_ID, article.getmSourceId());
        return values;
    }

}
