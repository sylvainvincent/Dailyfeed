package com.esgi.teamst.dailyfeed.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.esgi.teamst.dailyfeed.models.User;

/**
 * Created by sylvainvincent on 29/05/16.
 */
public class UserDAO extends AbstractDAO<User> {

    public static final String TABLE_NAME = "source";

    public static final String KEY_ID = "article_id";
    public static final String KEY_EMAIL = "article_title";
    public static final String KEY_PASSWORD = "article_content";

    public static final String CREATE_TABLE = "CREATE TABLE " +
            TABLE_NAME + "(" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_EMAIL + " TEXT," +
            KEY_PASSWORD + " TEXT)";

    public static final String[] ALL_COLUMNS = {KEY_ID, KEY_EMAIL, KEY_PASSWORD};


    public UserDAO(Context context) {
        super(context);
    }

    @Override
    public void add(User user) {
        ContentValues values = new ContentValues();
        values.put(KEY_EMAIL, user.getmEmail());
        values.put(KEY_PASSWORD, user.getPassword());

        getSqliteDb().insert(TABLE_NAME, null, values);
    }

    @Override
    public User get(int id) {
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
    public boolean update(int id, User user) {
        ContentValues values = new ContentValues();
        values.put(KEY_EMAIL, user.getmEmail());
        values.put(KEY_PASSWORD, user.getPassword());

        return getSqliteDb().update(TABLE_NAME, values, KEY_ID + "=" + id, null) > 0;
    }

    @Override
    public boolean delete(int id, User object) {
        return getSqliteDb().delete(TABLE_NAME, KEY_ID + "=" + id, null) > 0;
    }

    @Override
    public User cursorToObject(Cursor cursor) {
        return null;
    }
}
