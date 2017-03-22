package com.android.mevabe.profile;

import android.content.DialogInterface;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mevabe.MainActivity;
import com.android.mevabe.R;
import com.android.mevabe.common.AppData;
import com.android.mevabe.common.utils.AppUtil;
import com.android.mevabe.common.utils.DialogUtil;
import com.android.mevabe.common.utils.LogUtil;
import com.android.mevabe.common.model.MyProfile;
import com.android.mevabe.common.model.ProfileChildModel;
import com.android.mevabe.common.db.DBProfile;
import com.android.mevabe.common.view.FragmentLoginRequired;
import com.android.mevabe.common.view.RecyclerViewSupportEmpty;
import com.facebook.Profile;
import com.squareup.picasso.Picasso;

/**
 * Created by thuyld on 12/14/16.
 */
public class ProfileMain extends FragmentLoginRequired implements View.OnClickListener, AddChildDialog
        .IAddChildDialogCallback, EditChildDialog.IEditChildDialogCallback, ProfileChildrenViewAdapter.IProfileChildrenViewHandler {
    private ImageView avatar;
    private TextView profileName;

    private RecyclerViewSupportEmpty childListView;
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
        dbService = new DBProfile();

        // Bind view
        avatar = (ImageView) layoutView.findViewById(R.id.avatar);
        profileName = (TextView) layoutView.findViewById(R.id.profile_name);
        childListView = (RecyclerViewSupportEmpty) layoutView.findViewById(R.id.profile_children_view);
        profileName = (TextView) layoutView.findViewById(R.id.profile_name);
        TextView emptyChildView = (TextView) layoutView.findViewById(R.id.child_empty_view);
        addChildButton = (ImageView) layoutView.findViewById(R.id.children_add_button);

        // Set add child button listener
        addChildButton.setOnClickListener(this);

        // Set layout manager
        childListView.setLayoutManager(new LinearLayoutManager(getContext()));
        childListView.setEmptyView(emptyChildView);
        childViewAdapter = new ProfileChildrenViewAdapter(getActivity(), this);
        childListView.setAdapter(childViewAdapter);

        // Bind child for view
        MyProfile myProfile = AppData.getMyProfile();
        if (myProfile != null && myProfile.getMyPro() != null) {
            // Bind to view
            childViewAdapter.refreshItems(myProfile.getChildren());
        }

    }

    @Override
    public void onAccountChangeFinish(Profile profile) {
        // Refresh data only for case logged in
        if (profile != null) {
            // Update profile information in case login successfully
            int width = AppUtil.dpToPx(getContext(), 100);
            Uri avatarPath = profile.getProfilePictureUri(width, width);
            LogUtil.debug("Profile avatar path:  " + avatarPath);
            if (avatarPath != null) {
                Picasso.with(getContext()).load(avatarPath)
                        .error(R.drawable.common_placeholder)
                        .placeholder(R.drawable.common_placeholder)
                        .into(avatar);
            }
            profileName.setText(profile.getName());

            // Refresh list children
            childViewAdapter.refreshItems(AppData.getMyProfile().getChildren());
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
        AppData.getMyProfile().getChildren().add(child);
        childViewAdapter.appendItem(child);

        // Save new child to DB
        dbService.addChild(AppData.getMyProfile().getMyPro(), child);

        // Notify to other views which related to child information
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).notifyChildChange();
        }
    }

    // ****** IEditChildDialogCallback ******* //
    @Override
    public void onEditChildFinish(ProfileChildModel child) {
        // Refresh child info
        childViewAdapter.notifyDataSetChanged();

        // Update child to DB
        dbService.updateChild(child);

        // Notify to other views which related to child information
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).notifyChildChange();
        }
    }

    // ****** IProfileChildrenViewHandler ******* //
    @Override
    public void onItemDelete(final ProfileChildModel child) {
        // Show confirm dialog
        String message = String.format(getString(R.string.child_delete_confirm), child.getName());
        DialogUtil.showYesCancel(getContext(), message, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Delete from list children
                AppData.getMyProfile().getChildren().remove(child);
                childViewAdapter.removeItem(child);

                // Delete child from DB
                dbService.deleteChild(child.getId());

                // Notify to other views which related to child information
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).notifyChildChange();
                }
            }
        });

    }

    @Override
    public void onItemEdit(ProfileChildModel child) {
        // Show popup to edit child
        EditChildDialog dialog = new EditChildDialog(getContext(), child, this);
        dialog.show();
    }
}
