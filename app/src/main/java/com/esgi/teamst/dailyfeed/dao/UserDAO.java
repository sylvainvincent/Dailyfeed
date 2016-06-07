package com.esgi.teamst.dailyfeed.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.esgi.teamst.dailyfeed.models.User;

/**
 * Created by sylvainvincent on 29/05/16.
 */
public class UserDAO extends AbstractDAO<User> {

    public static final String TABLE_NAME = "user";

    public static final String KEY_ID = "user_id";
    public static final String KEY_FIRSTNAME = "user_firstname";
    public static final String KEY_LASTNAME = "user_lastname";
    public static final String KEY_EMAIL = "user_email";
    public static final String KEY_PASSWORD = "user_password";

    public static final String CREATE_TABLE = "CREATE TABLE " +
            TABLE_NAME + "(" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_FIRSTNAME + " TEXT," +
            KEY_LASTNAME + " TEXT," +
            KEY_EMAIL + " TEXT UNIQUE," +
            KEY_PASSWORD + " TEXT)";

    public static final String[] ALL_COLUMNS = {KEY_ID, KEY_FIRSTNAME, KEY_LASTNAME, KEY_EMAIL, KEY_PASSWORD};


    public UserDAO(Context context) {
        super(context);
    }

    @Override
    public boolean add(User user) {
        ContentValues values = new ContentValues();
        values.put(KEY_FIRSTNAME, user.getmFirstName());
        values.put(KEY_LASTNAME, user.getmLastName());
        values.put(KEY_EMAIL, user.getmEmail());
        values.put(KEY_PASSWORD, user.getPassword());

       return getSqliteDb().insert(TABLE_NAME, null, values) >= 0;
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
        User user = new User();
        user.setmId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
        user.setmFirstName(cursor.getString(cursor.getColumnIndex(KEY_FIRSTNAME)));
        user.setmLastName(cursor.getString(cursor.getColumnIndex(KEY_LASTNAME)));
        user.setmEmail(cursor.getString(cursor.getColumnIndex(KEY_EMAIL)));
        user.setPassword(cursor.getString(cursor.getColumnIndex(KEY_PASSWORD)));
        return user;
    }

    public User search(String email, String password){
        Cursor mCursor = getSqliteDb().query(true, TABLE_NAME, ALL_COLUMNS, KEY_EMAIL + " = '" + email
                        + "' AND " + KEY_PASSWORD + " = '" + password +"'",
                        null, null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
            return cursorToObject(mCursor);
        } else {
            return null;
        }
    }

    public boolean isExist(String email){
        Cursor mCursor = getSqliteDb().query(true, TABLE_NAME, ALL_COLUMNS, KEY_EMAIL + " = '" + email
                        + "'",
                null, null, null, null, null);

        if (mCursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }
}
