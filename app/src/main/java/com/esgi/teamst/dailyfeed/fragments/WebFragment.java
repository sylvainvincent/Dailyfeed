package com.esgi.teamst.dailyfeed.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.esgi.teamst.dailyfeed.R;

/**
 * Created by sylvainvincent on 11/07/16.
 */
public class WebFragment extends Fragment{

    protected View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_article_web, container, false);
        return rootView;
    }
}
