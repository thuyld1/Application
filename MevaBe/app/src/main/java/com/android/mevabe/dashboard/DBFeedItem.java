package com.android.mevabe.dashboard;

/**
 * Created by leducthuy on 12/17/16.
 */
public class DBFeedItem {
    private int type;
    private String thumbnail;
    private String title;

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
