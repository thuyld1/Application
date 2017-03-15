package com.android.mevabe.common.services;

/**
 * Created by leducthuy on 1/4/17.
 */

public class ConnectionResult {
    public static final int SUCCESS_CODE = 200;
    private int code;
    private String result;

    public boolean isSuccess() {
        return code == SUCCESS_CODE;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
