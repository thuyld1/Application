package com.android.mevabe.doctor;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.android.mevabe.R;
import com.android.mevabe.common.db.DBLocation;
import com.android.mevabe.common.model.LocationProvince;
import com.android.mevabe.common.utils.PrefUtil;
import com.android.mevabe.common.view.BaseActivity;
import com.android.mevabe.common.view.OnSwipeTouchListener;

import java.util.Set;

import static com.android.mevabe.doctor.DoctorsFilterSetting.FILTER_LOCATION_CITY_TITLE;
import static com.android.mevabe.doctor.DoctorsFilterSetting.FILTER_LOCATION_CITY_VALUE;

/**
 * Created by thuyld on 3/14/17.
 */
public class DoctorsFilterLocation extends BaseActivity implements LocationProvinceAdapter.ILocationProvinceAdapter {
    private TextView btnProvince;
    private TextView btnDistrict;
    private RecyclerView listProvince;
    private RecyclerView listDistrict;
    private LocationProvinceAdapter provinceAdapter;
    private LocationDistrictAdapter districtAdapter;

    private DBLocation dbLocation;
    private long selectedProvince;
    private String selectedProvinceTitle;
    private Set<String> selectedDistrict;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctors_filter_location);

        // Build GUI for view
        buildGUI();

        // Bind data to view
        bindData();
    }

    /**
     * Build GUI for view
     */
    private void buildGUI() {
        // Set up toolbar
        setTitle(getString(R.string.doctors_filter_title));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Implement back button
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        View.OnClickListener closeListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClose();
            }
        };
        toolbar.setNavigationOnClickListener(closeListener);

        // Build swipe to close
        View conentView = findViewById(R.id.content_view);
        conentView.setOnTouchListener(new OnSwipeTouchListener(this) {
            public void onSwipeRight() {
                onClose();
            }
        });

        // Bind content
        btnProvince = (TextView) findViewById(R.id.btn_province);
        btnDistrict = (TextView) findViewById(R.id.btn_district);
        listProvince = (RecyclerView) findViewById(R.id.list_province);
        listDistrict = (RecyclerView) findViewById(R.id.list_district);

        // Set layout manager
        listProvince.setLayoutManager(new LinearLayoutManager(this));
        listDistrict.setLayoutManager(new LinearLayoutManager(this));

    }

    /**
     * Bind data to view
     */
    private void bindData() {
        dbLocation = new DBLocation();

        // Bind list provinces
        selectedProvince = PrefUtil.readLong(FILTER_LOCATION_CITY_VALUE, -1);
        selectedProvinceTitle = PrefUtil.readString(FILTER_LOCATION_CITY_TITLE, null);
        provinceAdapter = new LocationProvinceAdapter(this, selectedProvince, this);
        listProvince.setAdapter(provinceAdapter);
        provinceAdapter.refreshItems(dbLocation.getProvinces(null));

        // Disable district tab filter if not select province yet
        if (selectedProvince < 0) {
            btnDistrict.setEnabled(false);
        }

        // Bind list districts
        selectedDistrict = PrefUtil.readList(DoctorsFilterSetting
                .FILTER_LOCATION_DISTRICT_VALUE, null);
        districtAdapter = new LocationDistrictAdapter(this);
        listDistrict.setAdapter(districtAdapter);
        if (selectedProvince > 0) {
            districtAdapter.refreshItems(dbLocation.getDistricts(selectedProvince, selectedDistrict));
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    // ************ ACTION CONTROL ***************

    /**
     * Handle when click province tab
     *
     * @param v View
     */
    public void settingProvince(View v) {
        btnProvince.setTextColor(getColor(R.color.colorPrimary));
        listProvince.setVisibility(View.VISIBLE);
        btnDistrict.setTextColor(getColor(R.color.textColor));
        listDistrict.setVisibility(View.GONE);
    }

    /**
     * Handle when click district tab
     *
     * @param v View
     */
    public void settingDistrict(View v) {
        if (selectedProvince > 0) {
            btnDistrict.setTextColor(getColor(R.color.colorPrimary));
            listDistrict.setVisibility(View.VISIBLE);
            btnProvince.setTextColor(getColor(R.color.textColor));
            listProvince.setVisibility(View.GONE);
        }
    }

    /**
     * Handle when click back button
     */
    public void onClose() {
        // Update set data
        if (selectedProvince > 0) {
            PrefUtil.writeLong(FILTER_LOCATION_CITY_VALUE, selectedProvince);
            PrefUtil.writeString(FILTER_LOCATION_CITY_TITLE, selectedProvinceTitle);
            districtAdapter.saveSelectedItems();
        }

        // Close current view
        finish();
    }

    // ******** LocationProvinceAdapter.ILocationProvinceAdapter *********
    @Override
    public void onChangeProvince(LocationProvince item) {
        // Update province info
        selectedProvince = item.getCode();
        selectedProvinceTitle = item.getTitle();

        // Enable select district button
        btnDistrict.setEnabled(true);

        // Refresh for district
        selectedDistrict = null;
        districtAdapter.refreshItems(dbLocation.getDistricts(selectedProvince, selectedDistrict));

        // Show tab of district
        settingDistrict(null);

    }
}
