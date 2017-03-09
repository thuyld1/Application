package com.android.mevabe.lichsuthuoc;

import android.view.View;

import com.android.mevabe.R;
import com.android.mevabe.view.FragmentLoginRequired;
import com.facebook.Profile;

/**
 * Created by thuyld on 12/14/16.
 */
public class LichSuThuocMain extends FragmentLoginRequired {
    @Override
    public int getLayoutContentViewXML() {
        return R.layout.lich_su_thuoc;
    }

    @Override
    public void initView(View layoutView) {

    }

    @Override
    public void onAccountChangeFinish(Profile profile) {
        // Refresh data only for case logged in
        if (profile != null) {

        }
    }
}
