package com.android.mevabe.vaccinations;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.android.mevabe.R;
import com.android.mevabe.WebViewActivity;
import com.android.mevabe.common.AppData;
import com.android.mevabe.common.Constants;
import com.android.mevabe.model.MyProfile;
import com.android.mevabe.model.VaccinationsHistoryModel;
import com.android.mevabe.model.VaccinationsPlanModel;
import com.android.mevabe.model.WebViewModel;
import com.android.mevabe.services.db.DBVacinations;
import com.android.mevabe.view.FragmentLoginRequired;
import com.android.mevabe.view.RecyclerViewSupportEmpty;
import com.facebook.Profile;

import java.util.List;

/**
 * Created by thuyld on 12/14/16.
 */
public class VaccinationsMain extends FragmentLoginRequired implements View.OnClickListener, VaccinationsPlanAdapter.IVaccinationsPlanHandler {
    private final int VACCINE_ADD_PLAN_CODE = 2017;

    // For view control
    private TextView btnHeaderSelected;
    private TextView btnHeaderPlan;
    private TextView btnHeaderHistory;

    private RecyclerViewSupportEmpty vaccinationsView;
    private VaccinationsPlanAdapter planAdapder;
    private VaccinationsHistoryAdapter historyAdapter;

    private DBVacinations dbVacinations;


    @Override
    public int getLayoutContentViewXML() {
        return R.layout.vaccinations;
    }

    @Override
    public void initView(View layoutView) {
        // Create db service
        dbVacinations = new DBVacinations();

        // Bind view
        btnHeaderPlan = (TextView) layoutView.findViewById(R.id.btn_plan);
        btnHeaderHistory = (TextView) layoutView.findViewById(R.id.btn_history);
        vaccinationsView = (RecyclerViewSupportEmpty) layoutView.findViewById(R.id.vaccinations_data_view);
        TextView emptyView = (TextView) layoutView.findViewById(R.id.empty_data);
        vaccinationsView.setEmptyView(emptyView);

        // Set layout manager
        vaccinationsView.setLayoutManager(new LinearLayoutManager(getContext()));
        planAdapder = new VaccinationsPlanAdapter(getActivity(), this);
        historyAdapter = new VaccinationsHistoryAdapter(getActivity());

        // Set default tab is plan tab
        btnHeaderPlan.setOnClickListener(this);
        btnHeaderHistory.setOnClickListener(this);
        btnHeaderSelected = btnHeaderHistory;
        onClick(btnHeaderPlan);

        // Bind data to view
        MyProfile myProfile = AppData.getMyProfile();
        if (myProfile != null) {
            // Bind vaccinations plan
            List<VaccinationsPlanModel> list = dbVacinations.getVaccinationsPlan(myProfile, null);
            planAdapder.refreshItems(list);

            // Bind vaccinations history
            List<VaccinationsHistoryModel> history = dbVacinations.getVaccinationsHistory(myProfile, null);
            historyAdapter.refreshItems(history);
        }
    }

    @Override
    public void onAccountChangeFinish(Profile profile) {
        // Refresh data only for case logged in
        if (profile != null) {
            // Refresh list vaccinations plan
            List<VaccinationsPlanModel> list = dbVacinations.getVaccinationsPlan(AppData.getMyProfile(), null);
            planAdapder.refreshItems(list);

            // Refresh list vaccinations history
            List<VaccinationsHistoryModel> history = dbVacinations.getVaccinationsHistory(AppData.getMyProfile(), null);
            historyAdapter.refreshItems(history);
        }
    }

    @Override
    public void onToolBarClicked(View v) {
        if (vaccinationsView != null) {
            vaccinationsView.smoothScrollToPosition(0);
        }
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
                vaccinationsView.setAdapter(historyAdapter);
            } else {
                vaccinationsView.setAdapter(planAdapder);
            }
        }

    }

    // ******** VaccinationsPlanAdapter.IVaccinationsPlanHandler ******** //
    @Override
    public void showVaccineInfo(VaccinationsPlanModel item) {
        // Show details of vaccine in webview
        WebViewActivity act = new WebViewActivity();
        Intent intent = new Intent(getContext(), WebViewActivity.class);
        WebViewModel info = new WebViewModel(item.getVaccinName(), item.getVaccinURL());
        intent.putExtra(Constants.INTENT_DATA, info);
        startActivity(intent);
    }

    @Override
    public void addVaccinePlan(VaccinationsPlanModel item) {
        // Open add vaccine for child view
        VaccinationsAddPlan act = new VaccinationsAddPlan();
        Intent intent = new Intent(getContext(), VaccinationsAddPlan.class);
        intent.putExtra(Constants.INTENT_DATA, item);
        startActivityForResult(intent, VACCINE_ADD_PLAN_CODE);
    }

}
