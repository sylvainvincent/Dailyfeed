package com.esgi.teamst.dailyfeed.activities;

import android.app.Activity;
import android.os.Bundle;

import com.esgi.teamst.dailyfeed.dao.UserDAO;

/**
 *
 */
public class DashboardActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserDAO userDAO = new UserDAO(this);

    }
}
