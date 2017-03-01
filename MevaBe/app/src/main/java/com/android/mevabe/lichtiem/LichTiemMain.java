package com.android.mevabe.lichtiem;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.mevabe.R;
import com.android.mevabe.view.FragmentLoginRequired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thuyld on 12/14/16.
 */
public class LichTiemMain extends FragmentLoginRequired implements View.OnClickListener {
    private TextView btnHeaderSelected;
    private TextView btnHeaderPlan;
    private TextView btnHeaderHistory;

    private RecyclerView lichTiemView;
    private LichTiemPlanAdapter planAdapder;

    @Override
    public int getLayoutContentViewXML() {
        return R.layout.lich_tiem;
    }

    @Override
    public void initView(View layoutView) {
        // Bind view
        btnHeaderPlan = (TextView) layoutView.findViewById(R.id.btn_plan);
        btnHeaderHistory = (TextView) layoutView.findViewById(R.id.btn_history);
        lichTiemView = (RecyclerView) layoutView.findViewById(R.id.lich_tiem_data_view);

        // Set layout manager
        lichTiemView.setLayoutManager(new LinearLayoutManager(getContext()));
        planAdapder = new LichTiemPlanAdapter(getActivity());
        lichTiemView.setAdapter(planAdapder);


        // Set default tab is plan tab
        btnHeaderSelected = btnHeaderHistory;
        btnHeaderPlan.setOnClickListener(this);
        btnHeaderHistory.setOnClickListener(this);
        onClick(btnHeaderPlan);


        List<LichTiemModel> list = new ArrayList<>();
        LichTiemModel item = null;
        for (int i = 0; i < 100; i++) {
            item = new LichTiemModel(null, "Quinvacen", "3-5T", "Phòng quai bị, Rubela, thuỷ đậu, sốt phát ban");
            list.add(item);
        }
        planAdapder.refreshItems(list);
    }

    // ********* HEADER CONTROL *********** //
    @Override
    public void onClick(View v) {
        if (!v.equals(btnHeaderSelected)) {
            btnHeaderSelected.setTextColor(Color.BLACK);
            btnHeaderSelected = (TextView) v;
            btnHeaderSelected.setTextColor(getResources().getColor(R.color.colorPrimary));
        }

    }

}
