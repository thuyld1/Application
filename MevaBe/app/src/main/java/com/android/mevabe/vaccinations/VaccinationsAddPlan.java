package com.android.mevabe.vaccinations;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mevabe.BaseActivity;
import com.android.mevabe.R;
import com.android.mevabe.common.Constants;
import com.android.mevabe.model.VaccinationsPlanModel;

/**
 * Created by thuyld on 3/14/17.
 */
public class VaccinationsAddPlan extends BaseActivity {
    // Block vaccine information for child
    private TextView childInfo;
    private TextView vaccinName;
    private TextView moreInfo;
    private TextView vaccinPeriod;
    private TextView vaccinDes;

    // Block setting information
    private ImageView inDateThumb;
    private Button inDateBtn;
    private EditText inPlace;
    private EditText inNote;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vaccinations_add_plan);

        // Map view: vaccine information for child
        this.childInfo = (TextView) findViewById(R.id.child_info);
        this.vaccinName = (TextView) findViewById(R.id.vaccinName);
        this.moreInfo = (TextView) findViewById(R.id.moreInfo);
        this.vaccinPeriod = (TextView) findViewById(R.id.vaccinPeriod);
        this.vaccinDes = (TextView) findViewById(R.id.vaccinDes);

        // Map view: setting information
        this.inDateThumb = (ImageView) findViewById(R.id.in_date_thumb);
        this.inDateBtn = (Button) findViewById(R.id.in_date_btn);
        this.inPlace = (EditText) findViewById(R.id.in_place);
        this.inPlace = (EditText) findViewById(R.id.in_note);

        // Bind data to view
        bindData();
    }

    /**
     * Bind data to view
     */
    private void bindData() {
        // Get data
        VaccinationsPlanModel data = (VaccinationsPlanModel) getIntent().getSerializableExtra
                (Constants.INTENT_DATA);
        if (data != null) {
            // Show  text vaccine information for child
            childInfo.setText(data.getChildInfo());
            vaccinName.setText(String.format(getString(R.string.vaccinations_name), data.getVaccinName()));
            vaccinPeriod.setText(String.format(getString(R.string.vaccinations_period), data.getVaccinPeriod()));
            vaccinDes.setText(data.getVaccinDes());
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
}
