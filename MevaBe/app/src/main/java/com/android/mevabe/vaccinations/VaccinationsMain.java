package com.android.mevabe.vaccinations;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.android.mevabe.R;
import com.android.mevabe.common.AppData;
import com.android.mevabe.common.Constants;
import com.android.mevabe.common.model.MyProfile;
import com.android.mevabe.common.model.VaccinationsHistoryModel;
import com.android.mevabe.common.model.VaccinationsPlanModel;
import com.android.mevabe.common.model.WebViewModel;
import com.android.mevabe.common.services.db.DBVacinations;
import com.android.mevabe.common.view.FragmentLoginRequired;
import com.android.mevabe.common.view.RecyclerViewSupportEmpty;
import com.android.mevabe.common.view.WebViewActivity;
import com.facebook.Profile;

import java.util.List;

/**
 * Created by thuyld on 12/14/16.
 */
public class VaccinationsMain extends FragmentLoginRequired implements View.OnClickListener, VaccinationsPlanAdapter.IVaccinationsPlanHandler, VaccinationsHistoryAdapter.IVaccinationsHistoryHandler {
    public static final int VACCINE_ADD_PLAN_CODE = 2017;
    public static final int VACCINE_EDIT_PLAN_CODE = 2018;

    // For empty child view
    private View emptyView;

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
        emptyView = layoutView.findViewById(R.id.empty_child_view);
        btnHeaderPlan = (TextView) layoutView.findViewById(R.id.btn_plan);
        btnHeaderHistory = (TextView) layoutView.findViewById(R.id.btn_history);
        vaccinationsView = (RecyclerViewSupportEmpty) layoutView.findViewById(R.id.vaccinations_data_view);
        TextView emptyView = (TextView) layoutView.findViewById(R.id.empty_data);
        vaccinationsView.setEmptyView(emptyView);

        // Set layout manager
        vaccinationsView.setLayoutManager(new LinearLayoutManager(getContext()));
        planAdapder = new VaccinationsPlanAdapter(getActivity(), this);
        historyAdapter = new VaccinationsHistoryAdapter(getActivity(), this);

        // Set default tab is plan tab
        btnHeaderPlan.setOnClickListener(this);
        btnHeaderHistory.setOnClickListener(this);
        btnHeaderSelected = btnHeaderHistory;
        onClick(btnHeaderPlan);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Do nothing in case not success
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        // Case go back from add vaccine plan screen success
        if (requestCode == VACCINE_ADD_PLAN_CODE) {
            // Remove child of vaccinations plan
            long planID = data.getLongExtra(Constants.INTENT_DATA, -1);
            planAdapder.removeItem(planID);
            return;
        }

        // Case go back from update vaccine plan screen success
        if (requestCode == VACCINE_EDIT_PLAN_CODE) {
            // Case user delete vaccinations plan
            if (data.hasExtra(Constants.INTENT_DATA_DELETE)) {
                long dataId = data.getLongExtra(Constants.INTENT_DATA, -1);
                historyAdapter.removeItem(dataId);
            } else {
                // Case user update vaccinations plan
                VaccinationsHistoryModel intentData = (VaccinationsHistoryModel) data.getSerializableExtra(Constants
                        .INTENT_DATA);
                historyAdapter.updateItem(intentData);
            }
            return;
        }
    }

    @Override
    public void onAccountChangeFinish(Profile profile) {
        // Refresh data only for case logged in
        if (profile != null) {
            // Case child of profile is empty
            if (AppData.getMyProfile().getChildren().isEmpty()) {
                emptyView.setVisibility(View.VISIBLE);
                contentView.setVisibility(View.GONE);
                return;
            } else {
                emptyView.setVisibility(View.GONE);
                contentView.setVisibility(View.VISIBLE);
            }

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

                // Bind vaccinations history
                MyProfile myProfile = AppData.getMyProfile();
                if (myProfile != null) {
                    List<VaccinationsHistoryModel> history = dbVacinations.getVaccinationsHistory(myProfile, null);
                    historyAdapter.refreshItems(history);
                }
            } else {
                vaccinationsView.setAdapter(planAdapder);

                // Bind vaccinations plan
                MyProfile myProfile = AppData.getMyProfile();
                if (myProfile != null) {
                    List<VaccinationsPlanModel> list = dbVacinations.getVaccinationsPlan(myProfile, null);
                    planAdapder.refreshItems(list);
                }
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
        Intent intent = new Intent(getContext(), VaccinationsAddPlan.class);
        intent.putExtra(Constants.INTENT_DATA, item);
        startActivityForResult(intent, VACCINE_ADD_PLAN_CODE);
    }

    // ******** VaccinationsPlanAdapter.IVaccinationsHistoryHandler ******** //
    @Override
    public void editVaccinePlan(VaccinationsHistoryModel item) {
        // Open edit vaccine for child view
        Intent intent = new Intent(getContext(), VaccinationsEditPlan.class);
        intent.putExtra(Constants.INTENT_DATA, item);
        startActivityForResult(intent, VACCINE_EDIT_PLAN_CODE);
    }
}
