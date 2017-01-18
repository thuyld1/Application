package com.android.mevabe.profile;

import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mevabe.R;
import com.android.mevabe.common.AppConfig;
import com.android.mevabe.common.utils.AppUtil;
import com.android.mevabe.view.FragmentLoginRequired;
import com.facebook.Profile;
import com.squareup.picasso.Picasso;

/**
 * Created by thuyld on 12/14/16.
 */
public class ProfileMain extends FragmentLoginRequired {
    private ImageView avatar;
    private TextView profileName;


    @Override
    public int getLayoutContentViewXML() {
        return R.layout.profile;
    }

    @Override
    public void initView(View layoutView) {
        avatar = (ImageView) layoutView.findViewById(R.id.avatar);
        profileName = (TextView) layoutView.findViewById(R.id.profile_name);
    }


    @Override
    public void onAccountChange(Profile profile) {
        super.onAccountChange(profile);

        // Update profile information incase login successfully
        if (profile != null) {
            int width = AppUtil.dpToPx(getContext(), 180);
            Uri avatarPath = profile.getProfilePictureUri(width, width);
            Log.i(AppConfig.LOG_TAG, "Profile avatar path:  " + avatarPath);
            if (avatarPath != null) {
                Picasso.with(getContext()).load(avatarPath)
                        .error(R.drawable.profile_avatar)
                        .placeholder(R.drawable.profile_avatar)
                        .into(avatar);
            }
            profileName.setText(profile.getName());
        }
    }


}
