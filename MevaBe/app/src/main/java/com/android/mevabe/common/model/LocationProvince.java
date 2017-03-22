package com.android.mevabe.common.model;

import java.io.Serializable;

/**
 * Created by thuyld on 3/22/17.
 */

public class LocationProvince extends BaseModel implements Serializable {
    private long code;
    private String title;

    public LocationProvince() {
    }

    public LocationProvince(long code, String title) {
        this.code = code;
        this.title = title;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
