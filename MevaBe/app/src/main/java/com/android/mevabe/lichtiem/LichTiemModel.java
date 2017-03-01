package com.android.mevabe.lichtiem;

import com.android.mevabe.model.BaseModel;
import com.android.mevabe.model.ProfileChildModel;

import java.io.Serializable;

/**
 * Created by leducthuy on 3/1/17.
 */
public class LichTiemModel extends BaseModel implements Serializable {
    private String vacxinName;
    private String vacxinPeriod;
    private String vacxinDes;

    private ProfileChildModel child;

    /**
     * Constructor
     *
     * @param child        ProfileChildModel
     * @param vacxinName   String
     * @param vacxinPeriod String
     * @param vacxinDes    String
     */
    public LichTiemModel(ProfileChildModel child, String vacxinName, String vacxinPeriod, String vacxinDes) {
        this.child = child;
        this.vacxinName = vacxinName;
        this.vacxinPeriod = vacxinPeriod;
        this.vacxinDes = vacxinDes;
    }

    public String getChildInfo() {
        String childInfo = "";
        if (child != null) {
            childInfo = String.format("%s (%s)", child.getName(), "3T");
        } else {
            childInfo =  String.format("%s (%s)", "Linh Ngọc", "3T");
        }

        return childInfo;
    }

    public ProfileChildModel getChild() {
        return child;
    }

    public void setChild(ProfileChildModel child) {
        this.child = child;
    }

    public String getVacxinName() {
        return vacxinName;
    }

    public void setVacxinName(String vacxinName) {
        this.vacxinName = vacxinName;
    }

    public String getVacxinPeriod() {
        return vacxinPeriod;
    }

    public void setVacxinPeriod(String vacxinPeriod) {
        this.vacxinPeriod = vacxinPeriod;
    }

    public String getVacxinDes() {
        return vacxinDes;
    }

    public void setVacxinDes(String vacxinDes) {
        this.vacxinDes = vacxinDes;
    }
}
