package com.android.mevabe.common.model;

import java.io.Serializable;

/**
 * Created by thuyld on 3/22/17.
 */

public class DoctorInfo extends BaseModel implements Serializable {
    private long code;
    private String name;
    private String avatar;
    private String phone;
    private String des;

    // For display view info
    private boolean isFavorite;

    public DoctorInfo() {
    }

    public DoctorInfo(long code, String name, String avatar, String phone, String des) {
        this.code = code;
        this.name = name;
        this.avatar = avatar;
        this.phone = phone;
        this.des = des;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
