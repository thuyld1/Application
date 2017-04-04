package com.android.mevabe.doctor;

import com.android.mevabe.common.model.DoctorInfo;

/**
 * IDoctorsHandler interface for callback
 */
public interface IDoctorsHandler {
    /**
     * Handle when uesr click to favorite button
     *
     * @param item DoctorInfo
     */
    void onClickFavorite(DoctorInfo item);

    /**
     * Handle when uesr click to call button
     *
     * @param item DoctorInfo
     */
    void onClickCall(DoctorInfo item);

    /**
     * Handle when uesr click to item
     *
     * @param item DoctorInfo
     */
    void onclickItem(DoctorInfo item);
}