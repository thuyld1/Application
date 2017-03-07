package com.android.mevabe.services.db;

public class DBConstants {
    public static final String ID = "_id";
    public static final String UPDATED = "updated";
    public static final String STATUS = "status";

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
    public static final String VACHIS_INJECTION_PLACE = "in_place";
    public static final String VACHIS_INJECTION_NOTE = "in_note";
}
