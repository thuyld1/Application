package com.android.mevabe.profile;

import android.app.Dialog;
import android.content.Context;

/**
 * Created by thuyld on 1/18/17.
 */

public class AddChildDialog extends Dialog {
    public AddChildDialog(Context context) {
        super(context);
    }

    public AddChildDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected AddChildDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    private void initView() {

    }
}
