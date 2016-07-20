package com.esgi.teamst.dailyfeed.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.esgi.teamst.dailyfeed.models.Source;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sylvainvincent on 05/05/16.
 */
public class SourceDAO extends AbstractDAO<Source> {

    private static final String TAG = SourceDAO.class.getSimpleName();

    public static final String TABLE_NAME = "source";

    public static final String KEY_ID = "source_id";
    public static final String KEY_NAME = "source_name";
    public static final String KEY_URL = "source_url";
    public static final String KEY_AVAILABLE = "source_available";

    public static final String FEED_URL_1 = "feeds.feedburner.com/Phonandroid";
    public static final String FEED_URL_2 = "feeds.feedburner.com/topito/tip-top";
    public static final String FEED_URL_3 = "feeds.feedburner.com/AndroidMtApplication";

    public static final String CREATE_TABLE = "CREATE TABLE " +
            TABLE_NAME + "(" +
            KEY_ID + " INTEGER PRIMARY KEY," +
            KEY_NAME + " TEXT," +
            KEY_URL + " TEXT," +
            KEY_AVAILABLE + " INTEGER" + ");" ;

    public static final String INSERT_DEFAULT = "INSERT INTO " + TABLE_NAME + "(" + KEY_NAME + "," +  KEY_URL + "," +  KEY_AVAILABLE + ") VALUES " +
            "('Phonandroid', '" + FEED_URL_1 + "', 1) ," +
            "('Topito', '" + FEED_URL_2 + "', 1) ," +
            "('AndroidMtApplication', '" + FEED_URL_3 + "', 1)";

    public static final String[] ALL_COLUMNS = {KEY_ID,
            KEY_NAME, KEY_URL, KEY_AVAILABLE};

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
                sourceList.add(cursorToObject(cursor));
                cursor.moveToNext();

            }
        }
        return sourceList;
    }

    public List<Source> getAllAvailableSource(){
        List<Source> sourceList = null;
        Cursor cursor = getSQLiteDb().query(TABLE_NAME,
                ALL_COLUMNS,
                KEY_AVAILABLE + "=1",
                null,
                null,
                null,
                null);

        if (cursor.getCount() > 0) {
            sourceList = new ArrayList<>();
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                sourceList.add(cursorToObject(cursor));
                cursor.moveToNext();
            }
        }

        return sourceList;
    }

    @Override
    public boolean update(Source source) {
        return getSQLiteDb().update(TABLE_NAME,
                objectToContentValues(source),
                KEY_ID + "=" + source.getId(),
                null) > 0;
    }

    @Override
    public boolean delete(Source source) {
        return getSQLiteDb().delete(TABLE_NAME,
                KEY_ID + "=" + source.getId(),
                null) > 0;
    }

    @Override
    public Source cursorToObject(Cursor cursor) {
        Source source = new Source();
        source.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
        source.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
        source.setUrl(cursor.getString(cursor.getColumnIndex(KEY_URL)));
        source.setAvailable(cursor.getInt(cursor.getColumnIndex(KEY_AVAILABLE)) == 1);
        return source;
    }

    @Override
    public ContentValues objectToContentValues(Source source) {
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, source.getName());
        values.put(KEY_URL, source.getUrl());
        values.put(KEY_AVAILABLE, source.isAvailable()?1:0);
        return values;
    }

}