package com.esgi.teamst.dailyfeed.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.esgi.teamst.dailyfeed.R;
import com.esgi.teamst.dailyfeed.Util;
import com.esgi.teamst.dailyfeed.dao.UserDAO;
import com.esgi.teamst.dailyfeed.models.User;

/**
 * Created by sylvainvincent on 29/05/16.
 */
public class LoginFragment extends Fragment {

    private static final String TAG = LoginFragment.class.getSimpleName();
    private View rootView;
    private EditText emailField;
    private EditText passwordField;
    private FrameLayout frameFragmentLogin;
    private Button mButtonLogin;
    private LoginFragmentCallback mLoginFragmentCallback;

 /*   public static LoginFragment newInstance(String title) {
         fragment = new LoginFragment();
        Bundle args = new Bundle();
        // args.putString(TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_login, container, false);
        initView(rootView);
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLoginFragmentCallback != null) {
                    if (isFormValid()) {
                        UserDAO userDAO = new UserDAO(getActivity());
                        try {
                            userDAO.open();
                            User user = userDAO.get(emailField.getText().toString(), passwordField.getText().toString());
                            if (user != null) {
                                mLoginFragmentCallback.connection(user.getmId());
                            }else{
                                Snackbar.make(frameFragmentLogin, getString(R.string.error_email_or_password_are_incorrect), Snackbar.LENGTH_INDEFINITE).show();
                            }
                            userDAO.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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
        frameFragmentLogin = (FrameLayout) rootView.findViewById(R.id.frame_fragment_login);
    }

    private boolean isFormValid(){

        boolean isValid = false;

        if(emailField.getText().toString().equals("")
                || passwordField.getText().toString().equals("")){
            Snackbar.make(frameFragmentLogin, getString(R.string.error_empty_fields),Snackbar.LENGTH_INDEFINITE).show();
        }else{
            isValid = true;
        }

        return isValid;
    }

    public interface LoginFragmentCallback {
        void connection(int userId);
    }
}
