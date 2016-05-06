package com.esgi.teamst.dailyfeed.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.esgi.teamst.dailyfeed.models.Source;

/**
 * Created by sylvainvincent on 05/05/16.
 */
public class SourceDAO extends AbstractDAO<Source> {

    public static final String TABLE_NAME = "source";

    public static final String KEY_ID = "source_id";
    public static final String KEY_NAME = "source_name";

    public static final String CREATE_TABLE = "CREATE TABLE " +
            TABLE_NAME + "(" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_NAME + " TEXT)";

    public static final String[] ALL_COLUMNS = {KEY_ID, KEY_NAME};

    public SourceDAO(Context context) {
        super(context);
    }

    @Override
    public void add(Source source) {
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, source.getmName());

        getSqliteDb().insert(TABLE_NAME, null, values);
    }

    @Override
    public Source get(int id) {
        Cursor mCursor = getSqliteDb().query(true, TABLE_NAME, ALL_COLUMNS, KEY_ID + "=" + id,
                null, null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
            return cursorToObject(mCursor);
        } else {
            return null;
        }
    }

    @Override
    public boolean update(int id, Source source) {
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, source.getmName());

        return getSqliteDb().update(TABLE_NAME, values, KEY_ID + "=" + id, null) > 0;
    }

    @Override
    public boolean delete(int id, Source source) {
        return getSqliteDb().delete(TABLE_NAME, KEY_ID + "=" + id, null) > 0;
    }

    @Override
    public Source cursorToObject(Cursor cursor) {
        Source source = new Source();
        source.setmId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
        source.setmName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
        return source;
    }


}
