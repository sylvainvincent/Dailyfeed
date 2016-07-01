package com.esgi.teamst.dailyfeed.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.esgi.teamst.dailyfeed.models.Source;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Created by sylvainvincent on 05/05/16.
 */
public class SourceDAO extends AbstractDAO<Source> {

    public static final String TABLE_NAME = "source";

    public static final String KEY_ID = "source_id";
    public static final String KEY_NAME = "source_name";
    public static final String KEY_URL = "source_url";

    public static final String FEED_URL_1 = "feeds.feedburner.com/Phonandroid";
    public static final String FEED_URL_2 = "feeds.feedburner.com/topito/tip-top";
    public static final String FEED_URL_3 = "feeds.feedburner.com/AndroidMtApplication";

    public static final String CREATE_TABLE = "CREATE TABLE " +
            TABLE_NAME + "(" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_NAME + " TEXT," +
            KEY_URL + " TEXT" + ");" ;

    public static final String INSERT_DEFAULT = "INSERT INTO " + TABLE_NAME + " (" + KEY_NAME + "," +  KEY_URL + ") VALUES " +
            "('Phonandroid','" + FEED_URL_1 + "') ," +
            "('Topito','" + FEED_URL_2 + "') ," +
            "('Topito','" + FEED_URL_3 + "')" ;


    public static final String[] ALL_COLUMNS = {KEY_ID,
            KEY_NAME, KEY_URL};

    public SourceDAO(Context context) {
        super(context);
    }

    @Override
    public boolean add(Source source) {
        return getSQLiteDb().insert(TABLE_NAME,
                null,
                objectToContentValues(source)) >= 0;
    }

    @Override
    public Source get(int id) {
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

    public List<Source> getAllSource(){
        List<Source> sourceList = null;
        Cursor cursor = getSQLiteDb().query(TABLE_NAME,
                ALL_COLUMNS,
                null,
                null,
                null,
                null,
                null);

        if (cursor.getCount() > 0) {
            sourceList = new ArrayList<>();
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                Source source = new Source();
                source.setmUrl(cursorToObject(cursor).getmUrl());
                source.setmId(cursorToObject(cursor).getmId());
                source.setmName(cursorToObject(cursor).getmName());
                cursor.moveToNext();
                sourceList.add(source);
            }
        }
        return sourceList;
    }

    @Override
    public boolean update(Source source) {
        return getSQLiteDb().update(TABLE_NAME,
                objectToContentValues(source),
                KEY_ID + "=" + source.getmId(),
                null) > 0;
    }

    @Override
    public boolean delete(Source source) {
        return getSQLiteDb().delete(TABLE_NAME,
                KEY_ID + "=" + source.getmId(),
                null) > 0;
    }

    @Override
    public Source cursorToObject(Cursor cursor) {
        Source source = new Source();
        source.setmId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
        source.setmName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
        source.setmUrl(cursor.getString(cursor.getColumnIndex(KEY_URL)));
        return source;
    }

    @Override
    public ContentValues objectToContentValues(Source source) {
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, source.getmName());
        return values;
    }

}
