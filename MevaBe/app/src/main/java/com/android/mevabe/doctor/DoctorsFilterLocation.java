package com.android.mevabe.doctor;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.mevabe.R;
import com.android.mevabe.common.db.DBLocation;
import com.android.mevabe.common.model.LocationProvince;
import com.android.mevabe.common.utils.PrefUtil;
import com.android.mevabe.common.view.BaseActivity;
import com.android.mevabe.common.view.OnSwipeTouchListener;

import java.util.HashSet;
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
    private long selectedProvice;
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
        View.OnClickListener cancelListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        };
        toolbar.setNavigationOnClickListener(cancelListener);

        // Build swipe to close
        View conentView = findViewById(R.id.content_view);
        conentView.setOnTouchListener(new OnSwipeTouchListener(this));

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
        selectedProvice = PrefUtil.readLong(
                FILTER_LOCATION_CITY_VALUE, -1);
        provinceAdapter = new LocationProvinceAdapter(this, selectedProvice, this);
        listProvince.setAdapter(provinceAdapter);
        provinceAdapter.refreshItems(dbLocation.getProvinces(null));

        // Disable district tab filter if not select province yet
        if (selectedProvice < 0) {
            btnDistrict.setEnabled(false);
        }

        // Bind list districts
        selectedDistrict = PrefUtil.readList(DoctorsFilterSetting
                .FILTER_LOCATION_DISTRICT_VALUE, null);
        if (selectedDistrict == null) {
            selectedDistrict = new HashSet<String>();
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
    public void settingProvince(View v) {
        btnProvince.setTextColor(getColor(R.color.colorPrimary));
        listProvince.setVisibility(View.VISIBLE);
        btnDistrict.setTextColor(getColor(R.color.textColor));
        listDistrict.setVisibility(View.GONE);
    }

    public void settingDistrict(View v) {
        if (selectedProvice > 0) {
            btnDistrict.setTextColor(getColor(R.color.colorPrimary));
            listDistrict.setVisibility(View.VISIBLE);
            btnProvince.setTextColor(getColor(R.color.textColor));
            listProvince.setVisibility(View.GONE);

            // Refresh for district
            districtAdapter.refreshItems(dbLocation.getDistricts(selectedProvice));
        }

    }

    /**
     * Get text string from EditText
     *
     * @param editText EditText
     * @return String
     */
    private String getTextString(EditText editText) {
        String value = null;
        Editable editable = editText.getText();
        if (editable != null) {
            value = editable.toString().trim();
        }
        return value;
    }

    // ******** LocationProvinceAdapter.ILocationProvinceAdapter *********
    @Override
    public void onChangeProvince(LocationProvince item) {
        // Update province info
        selectedProvice = item.getCode();
        PrefUtil.writeLong(FILTER_LOCATION_CITY_VALUE, selectedProvice);
        PrefUtil.writeString(FILTER_LOCATION_CITY_TITLE, item.getTitle());

        // Enable select district button
        btnDistrict.setEnabled(true);

        // Show details of district

    }
}
