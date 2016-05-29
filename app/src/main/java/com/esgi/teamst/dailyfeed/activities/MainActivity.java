package com.esgi.teamst.dailyfeed.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.esgi.teamst.dailyfeed.R;
import com.esgi.teamst.dailyfeed.fragments.LoginFragment;
import com.esgi.teamst.dailyfeed.fragments.RegistrationLogin;

/**
 * Created by sylvainvincent on 29/05/16.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener,LoginFragment.LoginFragmentCallback {

    public static final String TAG = MainActivity.class.getSimpleName();
    protected TextView mTextActionChange;
    protected TextView mTitleMain;
    private FrameLayout mFragmentMain;
    private LoginFragment mLoginFragment;
    private RegistrationLogin mRegistrationLogin;
    private boolean mChange = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);
        initView();
        mLoginFragment = new LoginFragment();
        mRegistrationLogin = new RegistrationLogin();
        getFragmentManager().beginTransaction().replace(R.id.frame_container_main, mLoginFragment).commit();


    }

    private void initView() {
        mFragmentMain = (FrameLayout) findViewById(R.id.frame_container_main);
        mTextActionChange = (TextView) findViewById(R.id.text_action_change);
        mTextActionChange.setOnClickListener(MainActivity.this);
        mTitleMain = (TextView) findViewById(R.id.title_main);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.text_action_change) {
            if (!mChange) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frame_container_main, mRegistrationLogin);
                ft.commit();
                mChange = true;
                mTitleMain.setText(R.string.title_subscription);
                mTextActionChange.setText(R.string.action_to_login);
            } else {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frame_container_main, mLoginFragment);
                ft.commit();
                mChange = false;
                mTitleMain.setText(R.string.title_login);
                mTextActionChange.setText(R.string.action_to_subscription);
            }
        }
    }

    @Override
    public void connection() {
        Log.i(TAG, "connection: réussie");
        startActivity(new Intent(MainActivity.this,NewsListActivity.class));
    }
}
