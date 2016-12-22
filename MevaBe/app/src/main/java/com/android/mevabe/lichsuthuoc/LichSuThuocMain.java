package com.android.mevabe.lichsuthuoc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.mevabe.common.Screen;
import com.android.mevabe.R;

/**
 * Created by thuyld on 12/14/16.
 */
public class LichSuThuocMain extends Screen {
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(
                R.layout.lich_su_thuoc, container, false);
    }
}
