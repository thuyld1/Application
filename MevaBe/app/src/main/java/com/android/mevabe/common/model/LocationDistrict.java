package com.android.mevabe.common.model;

import java.io.Serializable;

/**
 * Created by thuyld on 3/22/17.
 */

public class LocationDistrict extends BaseModel implements Serializable {
    private long code;
    private long pCode;
    private String title;

    // For view control
    private boolean isSelected;

    public LocationDistrict() {
    }

    public LocationDistrict(long code, long pCode, String title) {
        this.code = code;
        this.pCode = pCode;
        this.title = title;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public long getpCode() {
        return pCode;
    }

    public void setpCode(long pCode) {
        this.pCode = pCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
