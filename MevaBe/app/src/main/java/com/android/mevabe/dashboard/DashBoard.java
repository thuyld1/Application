package com.android.mevabe.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.mevabe.BaseFragment;
import com.android.mevabe.R;

/**
 * Created by thuyld on 12/14/16.
 */
public class DashBoard extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(
                R.layout.dashboard, container, false);
    }
}
