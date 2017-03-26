package com.android.mevabe.doctor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.mevabe.R;
import com.android.mevabe.common.utils.PrefUtil;
import com.android.mevabe.common.utils.StringUtils;
import com.android.mevabe.common.view.BaseActivity;
import com.android.mevabe.common.view.OnSwipeTouchListener;

/**
 * Created by thuyld on 3/14/17.
 */
public class DoctorsFilterSetting extends BaseActivity {
    public static final int DOCTORS_FILTER_LOCATION = 4017;
    public static final int DOCTORS_FILTER_SPECIALIZATION = 4018;

    public static final String FILTER_LOCATION_CITY_VALUE = "FILTER_LOCATION_CITY_VALUE";
    public static final String FILTER_LOCATION_CITY_TITLE = "FILTER_LOCATION_CITY_TITLE";
    public static final String FILTER_LOCATION_DISTRICT_VALUE = "FILTER_LOCATION_DISTRICT_VALUE";
    public static final String FILTER_LOCATION_DISTRICT_TITLE = "FILTER_LOCATION_DISTRICT_TITLE";
    public static final String FILTER_SPECIALIZATION_VALUE = "FILTER_SPECIALIZATION_VALUE";
    public static final String FILTER_SPECIALIZATION_TITLE = "FILTER_SPECIALIZATION_TITLE";

    private TextView filterLocation;
    private TextView filterSpecilization;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctors_filter_setting);

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

        filterLocation = (TextView) findViewById(R.id.filter_location);
        filterSpecilization = (TextView) findViewById(R.id.filter_specialization);
    }

    /**
     * Bind data to view
     */
    private void bindData() {
        String noSetting = getString(R.string.no_setting);

        // Get location filter info
        String location = PrefUtil.readString(FILTER_LOCATION_CITY_TITLE, null);
        if (StringUtils.isEmpty(location)) {
            location = getString(R.string.doctors_filter_location, "", noSetting);
        } else {
            String district = PrefUtil.readString(FILTER_LOCATION_DISTRICT_TITLE, null);
            if (StringUtils.isEmpty(district)) {
                location = getString(R.string.doctors_filter_location, location, "");
            } else {
                location = getString(R.string.doctors_filter_location, location, district);
            }

        }
        filterLocation.setText(location);


        // Get specialization filter info
        String specialization = PrefUtil.readString(FILTER_SPECIALIZATION_TITLE, noSetting);
        if (StringUtils.isEmpty(location)) {
            specialization = getString(R.string.doctors_filter_specialization, noSetting);
        } else {
            specialization = getString(R.string.doctors_filter_specialization, specialization);
        }
        filterSpecilization.setText(specialization);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Case go back from setting location
        if (requestCode == DOCTORS_FILTER_LOCATION) {
            // Update data
            bindData();
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
    public void settingLocation(View v) {
        // Open setting filter location view
        Intent intent = new Intent(this, DoctorsFilterLocation.class);
        startActivityForResult(intent, DOCTORS_FILTER_LOCATION);
    }

    public void settingSpecialization(View v) {
        // Open setting filter specialization view
        Intent intent = new Intent(this, DoctorsFilterSpecialization.class);
        startActivityForResult(intent, DOCTORS_FILTER_SPECIALIZATION);
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
}
