package com.esgi.teamst.dailyfeed.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Classe
 */
public abstract class AbstractDAO<T> {

    private DatabaseHandler databaseHandler;
    private SQLiteDatabase sqLiteDatabase;

    public SQLiteDatabase getSqliteDb() {
        return sqLiteDatabase;
    }

    public AbstractDAO(Context context) {
        this.databaseHandler = new DatabaseHandler(context);
    }
    public SQLiteDatabase open(){
        sqLiteDatabase = this.databaseHandler.getWritableDatabase();
        return sqLiteDatabase;
    }

    public void close(){
        sqLiteDatabase.close();
    }

    public abstract void add(T object);
    public abstract T get(int i);
}
