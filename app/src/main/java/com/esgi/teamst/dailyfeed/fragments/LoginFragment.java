package com.esgi.teamst.dailyfeed.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.esgi.teamst.dailyfeed.R;

/**
 * Created by sylvainvincent on 29/05/16.
 */
public class LoginFragment extends Fragment {

    private Button mButtonLogin;
    private LoginFragmentCallback mLoginFragmentCallback;

    public static LoginFragment newInstance(String title){
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
       // args.putString(TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mButtonLogin = (Button) view.findViewById(R.id.button_login);
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mLoginFragmentCallback != null){
                    mLoginFragmentCallback.connection();
                }
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof LoginFragmentCallback){
            mLoginFragmentCallback = (LoginFragmentCallback) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mLoginFragmentCallback = null;
    }

    public interface LoginFragmentCallback{
        void connection();
    }
}
