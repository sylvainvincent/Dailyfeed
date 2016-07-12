package com.esgi.teamst.dailyfeed.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.esgi.teamst.dailyfeed.R;
import com.esgi.teamst.dailyfeed.dao.UserDAO;
import com.esgi.teamst.dailyfeed.models.User;

import java.util.List;

/**
 * Created by sylvainvincent on 29/05/16.
 */
public class LoginFragment extends Fragment {

    private static final String TAG = LoginFragment.class.getSimpleName();
    private View rootView;
    private AutoCompleteTextView mAutoCompleteEmail;
    private EditText mEditPassword;
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
        UserDAO userDAO = new UserDAO(getActivity());
        userDAO.open();
        List<String> emails = userDAO.getAllEmail();
        userDAO.close();
        if(emails != null){
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,emails);
            mAutoCompleteEmail.setAdapter(adapter);
            mAutoCompleteEmail.setThreshold(1); // Minimum de lettre pour afficher les suggestions
        }
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLoginFragmentCallback != null) {
                    if (isFormValid()) {
                        UserDAO userDAO = new UserDAO(getActivity());
                        try {
                            userDAO.open();
                            User user = userDAO.get(mAutoCompleteEmail.getText().toString(), mEditPassword.getText().toString());
                            if (user != null) {
                                mLoginFragmentCallback.connection(user.getId());
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
        mAutoCompleteEmail = (AutoCompleteTextView) rootView.findViewById(R.id.autocomplete_email);
        mEditPassword = (EditText) rootView.findViewById(R.id.edit_password);
        mButtonLogin = (Button) rootView.findViewById(R.id.button_login);
        frameFragmentLogin = (FrameLayout) rootView.findViewById(R.id.frame_fragment_login);
    }

    private boolean isFormValid(){

        boolean isValid = false;

        if(mAutoCompleteEmail.getText().toString().equals("")
                || mEditPassword.getText().toString().equals("")){
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
