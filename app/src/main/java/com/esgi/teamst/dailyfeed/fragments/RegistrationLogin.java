package com.esgi.teamst.dailyfeed.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.esgi.teamst.dailyfeed.R;

/**
 * Created by sylvainvincent on 29/05/16.
 */
public class RegistrationLogin extends Fragment {

    private TextView textView;

    public static RegistrationLogin newInstance(String title){
        RegistrationLogin fragment = new RegistrationLogin();
        Bundle args = new Bundle();
        // args.putString(TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_registration, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
