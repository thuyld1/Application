package com.android.mevabe.model;

import com.android.mevabe.common.Constants;

/**
 * Created by leducthuy on 1/6/17.
 */

public class BaseModel {
    private int viewType = Constants.FEED_VIEW_TYPE_LEFT;

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }
}
