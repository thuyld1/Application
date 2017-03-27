package com.android.mevabe.common.model;

import java.io.Serializable;

/**
 * Created by leducthuy on 3/27/17.
 */

public class Specialization extends BaseModel implements Serializable {
    private long code;
    private String title;

    // For view control
    private boolean isSelected;

    public Specialization(long code, String title) {
        this.code = code;
        this.title = title;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
