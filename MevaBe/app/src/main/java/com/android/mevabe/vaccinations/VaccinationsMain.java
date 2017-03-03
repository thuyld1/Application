package com.android.mevabe.vaccinations;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.mevabe.R;
import com.android.mevabe.WebViewActivity;
import com.android.mevabe.common.Constants;
import com.android.mevabe.model.VaccinationsHistoryModel;
import com.android.mevabe.model.VaccinationsPlanModel;
import com.android.mevabe.model.WebViewModel;
import com.android.mevabe.view.FragmentLoginRequired;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by thuyld on 12/14/16.
 */
public class VaccinationsMain extends FragmentLoginRequired implements View.OnClickListener, VaccinationsPlanAdapter.IVaccinationsPlanHandler {
    private TextView btnHeaderSelected;
    private TextView btnHeaderPlan;
    private TextView btnHeaderHistory;

    private RecyclerView lichTiemView;
    private VaccinationsPlanAdapter planAdapder;
    private VaccinationsHistoryAdapter historyAdapter;

    @Override
    public int getLayoutContentViewXML() {
        return R.layout.vaccinations;
    }

    @Override
    public void initView(View layoutView) {
        // Bind view
        btnHeaderPlan = (TextView) layoutView.findViewById(R.id.btn_plan);
        btnHeaderHistory = (TextView) layoutView.findViewById(R.id.btn_history);
        lichTiemView = (RecyclerView) layoutView.findViewById(R.id.vaccinations_data_view);

        // Set layout manager
        lichTiemView.setLayoutManager(new LinearLayoutManager(getContext()));
        planAdapder = new VaccinationsPlanAdapter(getActivity(), this);
        historyAdapter = new VaccinationsHistoryAdapter(getActivity());

        // Set default tab is plan tab
        btnHeaderPlan.setOnClickListener(this);
        btnHeaderHistory.setOnClickListener(this);
        btnHeaderSelected = btnHeaderHistory;
        onClick(btnHeaderPlan);

        List<VaccinationsPlanModel> list = new ArrayList<>();
        VaccinationsPlanModel item = null;
        for (int i = 0; i < 100; i++) {
            item = new VaccinationsPlanModel(null, "Quinvacen", "3-5T", "Phòng quai bị, Rubela, thuỷ đậu, sốt phát ban", "http://dantri.com.vn");
            list.add(item);
        }
        planAdapder.refreshItems(list);

        List<VaccinationsHistoryModel> listHistory = new ArrayList<>();
        VaccinationsHistoryModel itemHistory = null;
        long date = Calendar.getInstance().getTimeInMillis();
        for (int i = 0; i < 100; i++) {
            itemHistory = new VaccinationsHistoryModel(null, "Quinvacen", date, "Phòng quai bị, Rubela, thuỷ đậu, sốt phát ban");
            itemHistory.setInjectionStatus(i % 4);
            listHistory.add(itemHistory);
        }
        historyAdapter.refreshItems(listHistory);
    }

    // ********* HEADER CONTROL *********** //
    @Override
    public void onClick(View v) {
        if (!v.equals(btnHeaderSelected)) {
            btnHeaderSelected.setTextColor(Color.BLACK);
            btnHeaderSelected = (TextView) v;
            btnHeaderSelected.setTextColor(getResources().getColor(R.color.colorPrimary));

            // Case user select history injection
            if (btnHeaderSelected.equals(btnHeaderHistory)) {
                lichTiemView.setAdapter(historyAdapter);
            } else {
                lichTiemView.setAdapter(planAdapder);
            }
        }

    }

    // ******** VaccinationsPlanAdapter.IVaccinationsPlanHandler ******** //
    @Override
    public void onItemClick(VaccinationsPlanModel item) {
        WebViewActivity act = new WebViewActivity();
        Intent intent = new Intent(getContext(), WebViewActivity.class);
        WebViewModel info = new WebViewModel(item.getVaccinName(), item.getVaccinURL());
        intent.putExtra(Constants.INTENT_DATA, info);
        startActivity(intent);
    }

}
