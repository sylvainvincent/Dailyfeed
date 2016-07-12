package com.esgi.teamst.dailyfeed.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.esgi.teamst.dailyfeed.R;
import com.esgi.teamst.dailyfeed.fragments.LoginFragment;
import com.esgi.teamst.dailyfeed.fragments.RegistrationFragment;

/**
 *
 * Guideline utilisé : https://github.com/ribot/android-guidelines/blob/master/project_and_code_guidelines.md
 *
 * + Idiomes : http://feanorin.developpez.com/tutoriels/android/idiomes/
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener,LoginFragment.LoginFragmentCallback,RegistrationFragment.RegistrationFragmentCallback {

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String EXTRA_USER_ID = "com.esgi.teamst.dailyfeed.activities.MainActivity.EXTRA_USER_ID";
    private RegistrationFragment mRegistrationFragment;
    private LoginFragment mLoginFragment;
    private TextView mTextActionChange;
    private TextView mTitleMain;
    private Toolbar toolbar;
    private boolean mLoginFragmentIsForeground = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.initView();
        setSupportActionBar(toolbar);
        mTextActionChange.setOnClickListener(this);
        mLoginFragment = new LoginFragment();
        mRegistrationFragment = new RegistrationFragment();
        getFragmentManager().beginTransaction().replace(R.id.frame_container_main, mLoginFragment).commit();

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.text_action_change) {
            changeFragment();
        }
    }

    @Override
    public void connection(int userId) {
        Log.i(TAG, "connection: réussie");
        Intent intent = new Intent(MainActivity.this, newsListActivity.class);
        intent.putExtra(EXTRA_USER_ID, userId);
        startActivity(intent);
    }

    private void initView() {
        mTextActionChange = (TextView) findViewById(R.id.text_action_change);
        mTitleMain = (TextView) findViewById(R.id.title_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    private void changeFragment(){
        if (!mLoginFragmentIsForeground) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.frame_container_main, mRegistrationFragment);
            ft.commit();
            mLoginFragmentIsForeground = true;
            mTitleMain.setText(R.string.title_subscription);
            mTextActionChange.setText(R.string.action_to_login);
        } else {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.frame_container_main, mLoginFragment);
            ft.commit();
            mLoginFragmentIsForeground = false;
            mTitleMain.setText(R.string.title_login);
            mTextActionChange.setText(R.string.action_to_subscription);
        }
    }

    @Override
    public void switchFragment() {
        mLoginFragmentIsForeground = true;
        changeFragment();
    }
}