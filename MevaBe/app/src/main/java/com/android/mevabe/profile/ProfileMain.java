package com.android.mevabe.profile;

import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mevabe.R;
import com.android.mevabe.common.AppConfig;
import com.android.mevabe.common.utils.AppUtil;
import com.android.mevabe.model.MyProfile;
import com.android.mevabe.model.ProfileChildModel;
import com.android.mevabe.services.db.DBProfile;
import com.android.mevabe.view.FragmentLoginRequired;
import com.facebook.Profile;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by thuyld on 12/14/16.
 */
public class ProfileMain extends FragmentLoginRequired implements View.OnClickListener, AddChildDialog.IAddChildDialogCallback {
    private MyProfile myProfile;
    private ImageView avatar;
    private TextView profileName;

    private RecyclerView childListView;
    private ProfileChildrenViewAdapter childViewAdapter;
    private ImageView addChildButton;
    private DBProfile dbService;


    @Override
    public int getLayoutContentViewXML() {
        return R.layout.profile;
    }

    @Override
    public void initView(View layoutView) {
        // Map data
        myProfile = getMyProfile();
        dbService = new DBProfile();

        // Bind view
        avatar = (ImageView) layoutView.findViewById(R.id.avatar);
        profileName = (TextView) layoutView.findViewById(R.id.profile_name);
        childListView = (RecyclerView) layoutView.findViewById(R.id.profile_children_view);
        addChildButton = (ImageView) layoutView.findViewById(R.id.children_add_button);

        // Set layout manager
        childListView.setLayoutManager(new LinearLayoutManager(getContext()));
        childViewAdapter = new ProfileChildrenViewAdapter(getActivity());
        childListView.setAdapter(childViewAdapter);
        childViewAdapter.refreshItems(myProfile.getChildren());

        addChildButton.setOnClickListener(this);

        // Bind child for view
        if (myProfile != null && myProfile.getMyPro() != null) {
            // Get list child from DB
            List<ProfileChildModel> res = dbService.getMyChildren(myProfile.getMyPro().getId());
            myProfile.setChildren(res);

            // Bind to view
            childViewAdapter.refreshItems(res);
        }

    }

    @Override
    public void onAccountChange(Profile profile) {
        super.onAccountChange(profile);

        // Update profile information incase login successfully
        if (hasCreatedView && profile != null) {
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


    @Override
    public void onClick(View v) {
        // Handle case add child button has clicked
        if (v.equals(addChildButton)) {
            // Show popup to add child
            AddChildDialog dialog = new AddChildDialog(getContext(), this);
            dialog.show();
        }
    }

    // ****** IAddChildDialogCallback ******* //
    @Override
    public void onAddChildFinish(ProfileChildModel child) {
        // Add to list children
        myProfile.getChildren().add(child);
        childViewAdapter.appendItem(child);

        // Save new child to DB
        dbService.addChild(myProfile.getMyPro(), child);
    }
}
