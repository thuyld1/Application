package com.android.mevabe.common.model;

import com.android.mevabe.common.AppData;
import com.android.mevabe.common.utils.StringUtils;
import com.android.mevabe.common.utils.YearsOldUtil;

import java.io.Serializable;

/**
 * Created by leducthuy on 3/1/17.
 */
public class VaccinationsPlanModel extends BaseModel implements Serializable {
    private long planID;
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
     * @param planID       long
     * @param vaccinID     long
     * @param vaccinCode   long
     * @param vaccinName   String
     * @param vaccinPeriod String
     * @param vaccinDes    String
     * @param vaccinURL    String
     */
    public VaccinationsPlanModel(ProfileChildModel child, long planID, long vaccinID, long vaccinCode, String
            vaccinName, String vaccinPeriod, String vaccinDes, String vaccinURL) {
        this.child = child;
        this.planID = planID;
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
            if (StringUtils.isEmpty(child.getYearsOld())) {
                childInfo = String.format("%s", child.getName());
            } else {
                childInfo = String.format("%s (%s)", child.getName(), child.getYearsOld());
            }
        }

        return childInfo;
    }

    public String getChildInfo(long toDate) {
        String childInfo = "";
        if (child != null) {
            String yearOlds = YearsOldUtil.getYearsOldTo(AppData.context, child.getDateOfBirth(), toDate);
            if (yearOlds == null) {
                childInfo = String.format("%s", child.getName());
            } else {
                childInfo = String.format("%s (%s)", child.getName(), yearOlds);
            }
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

    public long getVaccinID() {
        return vaccinID;
    }

    public void setVaccinID(long vaccinID) {
        this.vaccinID = vaccinID;
    }

    public long getVaccinCode() {
        return vaccinCode;
    }

    public void setVaccinCode(long vaccinCode) {
        this.vaccinCode = vaccinCode;
    }

    public long getPlanID() {
        return planID;
    }

    public void setPlanID(long planID) {
        this.planID = planID;
    }
}
