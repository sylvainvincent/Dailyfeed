package com.esgi.teamst.dailyfeed.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Classe
 */
public abstract class AbstractDAO<T> {

    private DatabaseHandler mDatabaseHandler;
    private SQLiteDatabase mSqLiteDatabase;

    public SQLiteDatabase getSQLiteDb() {
        return mSqLiteDatabase;
    }

    public AbstractDAO(Context context) {
        mDatabaseHandler = new DatabaseHandler(context);
    }

    public SQLiteDatabase open(){
        mSqLiteDatabase = this.mDatabaseHandler.getWritableDatabase();
        return mSqLiteDatabase;
    }

    public void close(){mDatabaseHandler.close();}

    public abstract boolean add(T object);
    public abstract T get(int id);
    public abstract boolean update(T object);
    public abstract boolean delete(T object);
    public abstract T cursorToObject(Cursor cursor);
    protected abstract ContentValues objectToContentValues(T object);

}
