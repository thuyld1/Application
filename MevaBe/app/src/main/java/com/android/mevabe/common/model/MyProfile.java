package com.android.mevabe.common.model;

import com.facebook.Profile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by thuyld on 1/20/17.
 */
public class MyProfile implements Serializable {
    private List<ProfileChildModel> children;
    private Profile myPro;
    private int filter;

    /**
     * Constructor
     */
    public MyProfile(Profile loginProfile) {
        myPro = loginProfile;
        children = new ArrayList<>();
    }

    /**
     * Find child by child ID
     *
     * @param childID long
     * @return ProfileChildModel
     */
    public ProfileChildModel getChild(long childID) {
        for (ProfileChildModel child : children) {
            if (child.getId() == childID) {
                return child;
            }
        }

        return null;
    }

    /**
     * Get filter child
     *
     * @return ProfileChildModel
     */
    public ProfileChildModel getFilterChild() {
        if (filter >= 0 && filter < children.size()) {
            return children.get(filter);
        }

        return null;
    }

    /**
     * Reset list children of profile
     */
    public void resetChild() {
        if (children != null) {
            children.clear();
        }
        filter = -1;
    }

    public void setChildren(List<ProfileChildModel> children) {
        this.children = children;
        filter = -1;
    }

    public List<ProfileChildModel> getChildren() {
        return children;
    }

    public com.facebook.Profile getMyPro() {
        return myPro;
    }

    public void setMyPro(com.facebook.Profile myPro) {
        this.myPro = myPro;
    }

    public int getFilter() {
        return filter;
    }

    public void setFilter(int filter) {
        this.filter = filter;
    }
}
