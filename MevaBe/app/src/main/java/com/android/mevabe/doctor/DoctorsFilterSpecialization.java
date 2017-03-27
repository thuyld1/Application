package com.android.mevabe.doctor;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.android.mevabe.R;
import com.android.mevabe.common.db.DBSpecialization;
import com.android.mevabe.common.utils.PrefUtil;
import com.android.mevabe.common.view.BaseActivity;
import com.android.mevabe.common.view.OnSwipeTouchListener;

import java.util.Set;

/**
 * Created by thuyld on 3/14/17.
 */
public class DoctorsFilterSpecialization extends BaseActivity {
    private RecyclerView listSpecialization;
    private SpecializationAdapter adapter;

    private DBSpecialization dbSpecialization;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctors_filter_specialization);

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
        conentView.setOnTouchListener(new OnSwipeTouchListener(this));

        // Bind content
        listSpecialization = (RecyclerView) findViewById(R.id.list_specialization);

        // Set layout manager
        listSpecialization.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * Bind data to view
     */
    private void bindData() {
        dbSpecialization = new DBSpecialization();

        // Bind list districts
        Set<String> selectedItems = PrefUtil.readList(DoctorsFilterSetting
                .FILTER_SPECIALIZATION_VALUE, null);
        adapter = new SpecializationAdapter(this);
        listSpecialization.setAdapter(adapter);
        adapter.refreshItems(dbSpecialization.getSpecializations(selectedItems));
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
     * Handle when click back button
     */
    public void onClose() {
        // Update set data
        adapter.saveSelectedItems();

        // Close current view
        finish();
    }

}
