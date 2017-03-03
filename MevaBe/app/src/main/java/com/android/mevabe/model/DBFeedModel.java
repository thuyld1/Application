package com.android.mevabe.model;

import java.io.Serializable;

/**
 * Created by leducthuy on 12/17/16.
 */
public class DBFeedModel extends BaseModel implements Serializable {
    private int piority;
    private String thumb;
    private String title;
    private String des;
    private String url;

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

    public int getPiority() {
        return piority;
    }

    public void setPiority(int piority) {
        this.piority = piority;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
