package com.esgi.teamst.dailyfeed.activities;

import android.app.Activity;
import android.os.Bundle;

import com.esgi.teamst.dailyfeed.dao.UserDAO;

/**
 * Created by sylvainvincent on 12/06/16.
 */
public class DashboardActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserDAO userDAO = new UserDAO(this);

    }
}
