package com.esgi.teamst.dailyfeed.dao;

import android.content.Context;

import com.esgi.teamst.dailyfeed.models.Source;

import java.util.Objects;

/**
 * Created by sylvainvincent on 05/05/16.
 */
public class SourceDAO extends AbstractDAO {

    public static final String TABLE_NAME = "source";

    public static final String KEY_ID = "source_id";
    public static final String KEY_NAME = "source_name";

    public static final String CREATE_TABLE = "CREATE TABLE " +
            TABLE_NAME + "(" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_NAME + " TEXT)";

    public SourceDAO(Context context) {
        super(context);
    }

    @Override
    public void add(Object object) {

    }

    @Override
    public Object get(int i) {
        return null;
    }
}
