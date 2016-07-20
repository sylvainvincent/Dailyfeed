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
import com.esgi.teamst.dailyfeed.util.Util;
import com.esgi.teamst.dailyfeed.dao.UserDAO;
import com.esgi.teamst.dailyfeed.models.User;

/**
 * Created by sylvainvincent on 29/05/16.
 */
public class RegistrationFragment extends Fragment implements View.OnClickListener{

    protected View rootView;
    protected EditText mLastNameField;
    protected EditText mFirstNameField;
    protected EditText mEmailField;
    protected EditText mPasswordField;
    protected Button mSubscribeButton;
    protected FrameLayout mFrameFragment;
    private RegistrationFragmentCallback mRegistrationFragmentCallback;

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
        initViews(rootView);
        mSubscribeButton.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void initViews(View rootView) {
        mLastNameField = (EditText) rootView.findViewById(R.id.lastname_field);
        mFirstNameField = (EditText) rootView.findViewById(R.id.firstname_field);
        mEmailField = (EditText) rootView.findViewById(R.id.autocomplete_email);
        mPasswordField = (EditText) rootView.findViewById(R.id.edit_password);
        mSubscribeButton = (Button) rootView.findViewById(R.id.subscribe_button);
        mFrameFragment = (FrameLayout) rootView.findViewById(R.id.frame_fragment_registration);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.subscribe_button:
               // todo ajouté isFormValid() dans la version final
                if(isFormValid()){
                    User user = new User();
                    user.setFirstName(mFirstNameField.getText().toString());
                    user.setLastName(mLastNameField.getText().toString());
                    user.setEmail(mEmailField.getText().toString());
                    user.setPassword(mPasswordField.getText().toString());
                    UserDAO userDAO = new UserDAO(getActivity());
                    userDAO.open();
                    boolean find = userDAO.add(user);
                    userDAO.close();
                    if(find) {
                        Snackbar.make(mFrameFragment, getString(R.string.success_subscribe),Snackbar.LENGTH_INDEFINITE).show();
                        mFirstNameField.setText("");
                        mLastNameField.setText("");
                        mEmailField.setText("");
                        mPasswordField.setText("");
                        mRegistrationFragmentCallback.switchFragment();
                    }else{
                        Snackbar.make(mFrameFragment, getString(R.string.error_subscribe),Snackbar.LENGTH_INDEFINITE).show();
                    }
                }
                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof RegistrationFragmentCallback){
            mRegistrationFragmentCallback = (RegistrationFragmentCallback) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mRegistrationFragmentCallback = null;
    }

    private boolean isFormValid(){

        boolean isValid = false;

        if(mLastNameField.getText().toString().equals("")
                || mFirstNameField.getText().toString().equals("")
                || mEmailField.getText().toString().equals("")
                || mPasswordField.getText().toString().equals("")){
            Snackbar.make(mFrameFragment, getString(R.string.error_empty_fields),Snackbar.LENGTH_INDEFINITE).show();
        }else if(!Util.isEmailAddress(mEmailField.getText().toString())){
            Snackbar.make(mFrameFragment, getString(R.string.error_email_is_not_valid),Snackbar.LENGTH_INDEFINITE).show();
        }else if(!Util.isString(mLastNameField.getText().toString()) || !Util.isString(mLastNameField.getText().toString())){
            Snackbar.make(mFrameFragment, getString(R.string.error_text_is_not_alpha),Snackbar.LENGTH_INDEFINITE).show();
        }else{
            isValid = true;
        }

        return isValid;
    }

    public interface RegistrationFragmentCallback {
        void switchFragment();
    }
}
