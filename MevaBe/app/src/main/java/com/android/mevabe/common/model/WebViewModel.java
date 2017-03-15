package com.android.mevabe.common.model;

/**
 * Created by thuyld on 3/3/17.
 */

public class WebViewModel extends BaseModel {
    private String title;
    private String url;

    public WebViewModel(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
