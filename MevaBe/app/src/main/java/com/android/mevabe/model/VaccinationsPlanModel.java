package com.android.mevabe.model;

import java.io.Serializable;

/**
 * Created by leducthuy on 3/1/17.
 */
public class VaccinationsPlanModel extends BaseModel implements Serializable {
    private long vaccinID;
    private long vaccinCode;
    private String vaccinName;
    private String vaccinPeriod;
    private String vaccinDes;
    private String vaccinURL;


    private ProfileChildModel child;

    /**
     * Constructor
     *
     * @param child        ProfileChildModel
     * @param vaccinID     long
     * @param vaccinCode   long
     * @param vaccinName   String
     * @param vaccinPeriod String
     * @param vaccinDes    String
     * @param vaccinURL    String
     */
    public VaccinationsPlanModel(ProfileChildModel child, long vaccinID, long vaccinCode, String vaccinName, String vaccinPeriod, String vaccinDes, String vaccinURL) {
        this.child = child;
        this.vaccinID = vaccinID;
        this.vaccinCode = vaccinCode;
        this.vaccinName = vaccinName;
        this.vaccinPeriod = vaccinPeriod;
        this.vaccinDes = vaccinDes;
        this.vaccinURL = vaccinURL;
    }

    public String getChildInfo() {
        String childInfo = "";
        if (child != null) {
            childInfo = String.format("%s (%s)", child.getName(), "3T");
        } else {
            childInfo = String.format("%s (%s)", "Linh Ngọc", "3T");
        }

        return childInfo;
    }

    public String getVaccinName() {
        return vaccinName;
    }

    public void setVaccinName(String vaccinName) {
        this.vaccinName = vaccinName;
    }

    public String getVaccinPeriod() {
        return vaccinPeriod;
    }

    public void setVaccinPeriod(String vaccinPeriod) {
        this.vaccinPeriod = vaccinPeriod;
    }

    public String getVaccinDes() {
        return vaccinDes;
    }

    public void setVaccinDes(String vaccinDes) {
        this.vaccinDes = vaccinDes;
    }

    public ProfileChildModel getChild() {
        return child;
    }

    public void setChild(ProfileChildModel child) {
        this.child = child;
    }

    public String getVaccinURL() {
        return vaccinURL;
    }

    public void setVaccinURL(String vaccinURL) {
        this.vaccinURL = vaccinURL;
    }
}
