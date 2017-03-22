package com.android.mevabe.doctor;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.mevabe.R;
import com.android.mevabe.common.db.DBLocation;
import com.android.mevabe.common.view.BaseActivity;
import com.android.mevabe.common.view.OnSwipeTouchListener;
import com.android.mevabe.common.view.RecyclerViewSupportEmpty;

/**
 * Created by thuyld on 3/14/17.
 */
public class DoctorsFilterLocation extends BaseActivity {
    private TextView btnProvince;
    private TextView btnDistrict;
    private RecyclerView listProvince;
    private RecyclerView listDistrict;

    private DBLocation dbLocation;

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

        btnProvince = (TextView) findViewById(R.id.btn_province);
        btnDistrict = (TextView) findViewById(R.id.btn_district);
        listProvince = (RecyclerViewSupportEmpty) findViewById(R.id.list_province);
        listDistrict = (RecyclerViewSupportEmpty) findViewById(R.id.list_district);


    }

    /**
     * Bind data to view
     */
    private void bindData() {
        dbLocation = new DBLocation();
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

    }

    public void settingSpecialization(View v) {

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
