package com.esgi.teamst.dailyfeed.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.esgi.teamst.dailyfeed.R;
import com.esgi.teamst.dailyfeed.dao.UserDAO;
import com.esgi.teamst.dailyfeed.models.User;

/**
 * Created by sylvainvincent on 29/05/16.
 */
public class LoginFragment extends Fragment {

    private static final String TAG = LoginFragment.class.getSimpleName();
    protected View rootView;
    protected EditText emailField;
    protected EditText passwordField;
    private Button mButtonLogin;
    private LoginFragmentCallback mLoginFragmentCallback;

    public static LoginFragment newInstance(String title) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        // args.putString(TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_login, container, false);
        initView(rootView);
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLoginFragmentCallback != null) {
                    UserDAO userDAO = new UserDAO(getActivity());
                    try{
                        userDAO.open();
                        User user = userDAO.search(emailField.getText().toString(), passwordField.getText().toString());
                        if (user != null) {
                            mLoginFragmentCallback.connection();
                        }
                        userDAO.close();
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            }
        });
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof LoginFragmentCallback) {
            mLoginFragmentCallback = (LoginFragmentCallback) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mLoginFragmentCallback = null;
    }

    private void initView(View rootView) {
        emailField = (EditText) rootView.findViewById(R.id.email_field);
        passwordField = (EditText) rootView.findViewById(R.id.password_field);
        mButtonLogin = (Button) rootView.findViewById(R.id.button_login);
    }

    public interface LoginFragmentCallback {
        void connection();
    }
}
