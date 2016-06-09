package com.esgi.teamst.dailyfeed.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.esgi.teamst.dailyfeed.models.User;

/**
 * Created by sylvainvincent on 29/05/16.
 */
public class UserDAO extends AbstractDAO<User> {

    private static final String TAG = UserDAO.class.getSimpleName();

    public static final String TABLE_NAME = "user";

    public static final String KEY_ID = "user_id";
    public static final String KEY_FIRST_NAME = "user_first_name";
    public static final String KEY_LAST_NAME = "user_last_name";
    public static final String KEY_EMAIL = "user_email";
    public static final String KEY_PASSWORD = "user_password";

    public static final String CREATE_TABLE = "CREATE TABLE " +
            TABLE_NAME + "(" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_FIRST_NAME + " TEXT," +
            KEY_LAST_NAME + " TEXT," +
            KEY_EMAIL + " TEXT UNIQUE," +
            KEY_PASSWORD + " TEXT)";

    public static final String[] ALL_COLUMNS = {KEY_ID,
            KEY_FIRST_NAME,
            KEY_LAST_NAME,
            KEY_EMAIL,
            KEY_PASSWORD};


    public UserDAO(Context context) {
        super(context);
    }

    @Override
    public boolean add(User user) {
        return getSQLiteDb().insert(TABLE_NAME,
                null,
                objectToContentValues(user)) >= 0;
    }

    @Override
    public User get(int id) {
        Cursor mCursor = getSQLiteDb().query(true,
                TABLE_NAME,
                ALL_COLUMNS,
                KEY_ID + "=" + id,
                null,
                null,
                null,
                null,
                null);

        if (mCursor != null) {
            mCursor.moveToFirst();
            return cursorToObject(mCursor);
        } else {
            return null;
        }
    }

    public User get(String email, String password) {
        Cursor mCursor = getSQLiteDb().query(TABLE_NAME,
                ALL_COLUMNS,
                KEY_EMAIL + " = ? AND " + KEY_PASSWORD + " = ?",
                new String[]{email, password},
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
    public boolean update(User user) {
        return getSQLiteDb().update(TABLE_NAME,
                objectToContentValues(user),
                KEY_ID + "=" + user.getmId(),
                null) > 0;
    }

    @Override
    public boolean delete(User user) {
        return getSQLiteDb().delete(TABLE_NAME,
                KEY_ID + "=" + user.getmId(),
                null) > 0;
    }

    @Override
    public User cursorToObject(Cursor cursor) {
        User user = new User();
        user.setmId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
        user.setmFirstName(cursor.getString(cursor.getColumnIndex(KEY_FIRST_NAME)));
        user.setmLastName(cursor.getString(cursor.getColumnIndex(KEY_LAST_NAME)));
        user.setmEmail(cursor.getString(cursor.getColumnIndex(KEY_EMAIL)));
        user.setPassword(cursor.getString(cursor.getColumnIndex(KEY_PASSWORD)));
        return user;
    }

    @Override
    public ContentValues objectToContentValues(User user) {
        ContentValues values = new ContentValues();
        values.put(KEY_FIRST_NAME, user.getmFirstName());
        values.put(KEY_LAST_NAME, user.getmLastName());
        values.put(KEY_EMAIL, user.getmEmail());
        values.put(KEY_PASSWORD, user.getPassword());
        return values;
    }


}
