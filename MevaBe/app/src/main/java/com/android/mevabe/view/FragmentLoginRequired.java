package com.android.mevabe.view;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.mevabe.R;
import com.facebook.CallbackManager;
import com.facebook.Profile;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

/**
 * Created by thuyld on 12/14/16.
 */
public abstract class FragmentLoginRequired extends FragmentBase {
    protected boolean hasCreatedView = false;
    private CallbackManager callbackManager;
    private View contentView;
    private View loginView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        hasCreatedView = true;
        View layoutView = super.onCreateView(inflater, container, savedInstanceState);


        // Bind view
        contentView = layoutView.findViewById(R.id.content_view);
        loginView = layoutView.findViewById(R.id.login_view);

        // Bind login button
        LoginButton loginButton = (LoginButton) layoutView.findViewById(R.id.login_button);
        loginButton.setFragment(this);
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email"));
        callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, null);


        // Update UI when account has change
        onAccountChange(Profile.getCurrentProfile());

        return layoutView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (callbackManager != null) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    /**
     * Update UI when account has changed
     *
     * @param profile Profile
     */
    private void changeUIForAccountChange(Profile profile) {
        // Show content in case login already
        if (profile != null) {
            contentView.setVisibility(View.VISIBLE);
            loginView.setVisibility(View.GONE);
        } else {
            contentView.setVisibility(View.GONE);
            loginView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Handle in case user log in/log out
     *
     * @param profile Profile
     */
    public void onAccountChange(Profile profile) {
        // Update UI when account has change
        if (hasCreatedView) {
            changeUIForAccountChange(profile);
        }
    }


}

