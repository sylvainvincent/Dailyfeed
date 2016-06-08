package com.esgi.teamst.dailyfeed.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.esgi.teamst.dailyfeed.R;
import com.esgi.teamst.dailyfeed.Util;
import com.esgi.teamst.dailyfeed.dao.UserDAO;
import com.esgi.teamst.dailyfeed.models.User;

/**
 * Created by sylvainvincent on 29/05/16.
 */
public class RegistrationFragment extends Fragment implements View.OnClickListener{

    protected View rootView;
    protected EditText lastnameField;
    protected EditText firstnameField;
    protected EditText emailField;
    protected EditText passwordField;
    protected Button subscribeButton;
    protected FrameLayout frameFragment;
    private TextView textView;

    public static RegistrationFragment newInstance(String title) {
        RegistrationFragment fragment = new RegistrationFragment();
        Bundle args = new Bundle();
        // args.putString(TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_registration, container, false);
        initView(rootView);
        subscribeButton.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void initView(View rootView) {
        lastnameField = (EditText) rootView.findViewById(R.id.lastname_field);
        firstnameField = (EditText) rootView.findViewById(R.id.firstname_field);
        emailField = (EditText) rootView.findViewById(R.id.email_field);
        passwordField = (EditText) rootView.findViewById(R.id.password_field);
        subscribeButton = (Button) rootView.findViewById(R.id.subscribe_button);
        frameFragment = (FrameLayout) rootView.findViewById(R.id.frame_fragment_registration);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.subscribe_button:
                if(isFormValid()){
                    User user = new User();
                    user.setmFirstName(firstnameField.getText().toString());
                    user.setmLastName(lastnameField.getText().toString());
                    user.setmEmail(emailField.getText().toString());
                    user.setPassword(passwordField.getText().toString());
                    UserDAO userDAO = new UserDAO(getActivity());
                    userDAO.open();
                    boolean find = userDAO.add(user);
                    userDAO.close();
                    if(find) {
                        Snackbar.make(frameFragment, getString(R.string.success_subscribe),Snackbar.LENGTH_INDEFINITE).show();
                    }else{
                        Snackbar.make(frameFragment, getString(R.string.error_subscribe),Snackbar.LENGTH_INDEFINITE).show();
                    }
                }
                break;
        }
    }

    private boolean isFormValid(){

        boolean isValid = false;

        if(lastnameField.getText().toString().equals("")
                || firstnameField.getText().toString().equals("")
                || emailField.getText().toString().equals("")
                || passwordField.getText().toString().equals("")){
            Snackbar.make(frameFragment, getString(R.string.error_empty_fields),Snackbar.LENGTH_INDEFINITE).show();
        }else if(!Util.isEmailAddress(emailField.getText().toString())){
            Snackbar.make(frameFragment, getString(R.string.error_email_is_not_valid),Snackbar.LENGTH_INDEFINITE).show();
        }else if(!Util.isString(lastnameField.getText().toString()) || !Util.isString(lastnameField.getText().toString())){
            Snackbar.make(frameFragment, getString(R.string.error_text_is_not_alpha),Snackbar.LENGTH_INDEFINITE).show();
        }else{
            isValid = true;
        }

        return isValid;
    }
}
