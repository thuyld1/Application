package com.android.mevabe.dashboard;

import com.android.mevabe.model.BaseModel;

/**
 * Created by leducthuy on 12/17/16.
 */
public class DBFeedItem extends BaseModel {
    private int piority;
    private String thumb;
    private String title;
    private String des;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }
}
