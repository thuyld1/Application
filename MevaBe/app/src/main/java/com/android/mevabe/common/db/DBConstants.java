package com.android.mevabe.common.db;

public class DBConstants {
    public static final String ID = "_id";
    public static final String UPDATED = "updated";
    public static final String STATUS = "status";

    public static final int STATUS_NORMAL = 0;
    public static final int STATUS_DELETE = 9;

    // Table children
    public static final String TB_CHILDREN = "children";
    public static final String CHILD_PARENT_ID = "p_id";
    public static final String CHILD_ID = "c_id";
    public static final String CHILD_NAME = "c_name";
    public static final String CHILD_BIRTH = "c_birth";
    public static final String CHILD_GENDER = "c_gender";

    // Table vaccines
    public static final String TB_VACCINES = "vaccines";
    public static final String VACCIN_CODE = "v_code";
    public static final String VACCIN_NAME = "v_name";
    public static final String VACCIN_PERIOD = "v_period";
    public static final String VACCIN_PERIOD_FROM = "v_period_f";
    public static final String VACCIN_PERIOD_TO = "v_period_t";
    public static final String VACCIN_SHORT_DES = "v_short_des";
    public static final String VACCIN_URL = "v_url";

    // Table vaccinations plan
    public static final String TB_VACCINATION_PLAN = "vaccinations_plan";
    public static final String VACPLAN_CHILD_ID = "c_id";
    public static final String VACPLAN_VACCINE_ID = "v_id";

    // Table vaccinations history
    public static final String TB_VACCINATION_HISTORY = "vaccinations_history";
    public static final String VACHIS_CHILD_ID = "c_id";
    public static final String VACHIS_VACCINE_ID = "v_id";
    public static final String VACHIS_INJECTION_DATE = "in_date";
    public static final String VACHIS_INJECTION_STATUS = "in_status";
    public static final String VACHIS_INJECTION_PLACE = "in_place";
    public static final String VACHIS_INJECTION_NOTE = "in_note";

    // Table filter location province
    public static final String TB_LOCATION_PROVINCE = "filter_location_province";
    public static final String LOC_PRO_CODE = "code";
    public static final String LOC_PRO_ORDER = "ord";
    public static final String LOC_PRO_TITLE = "title";
    public static final String LOC_PRO_SIMPLE = "simple";

    // Table filter location district
    public static final String TB_LOCATION_DISTRICT = "filter_location_district";
    public static final String LOC_DIS_CODE = "code";
    public static final String LOC_DIS_PCODE = "p_code";
    public static final String LOC_DIS_TITLE = "title";

    // Table filter specialization
    public static final String TB_SPECIALIZATION = "filter_specialization";
    public static final String SPEC_CODE = "code";
    public static final String SPEC_TITLE = "title";
    public static final String SPEC_ORDER = "ord";
}
