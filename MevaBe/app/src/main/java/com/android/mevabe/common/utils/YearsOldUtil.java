package com.android.mevabe.common.utils;

import android.content.Context;

import com.android.mevabe.R;

import java.util.Calendar;

/**
 * Created by thuyld on 3/9/17.
 */
public class YearsOldUtil {

    /**
     * Get years old of child
     *
     * @param dateOfBirth long
     * @return int[] {years, months, days} and return null in case invalid
     */
    public static int[] getYearsOld(long dateOfBirth) {
        // Get current time
        Calendar current = Calendar.getInstance();

        // Case invalid
        if (dateOfBirth <= 0 || current.getTimeInMillis() < dateOfBirth) {
            return null;
        }

        // Init data
        Calendar birth = Calendar.getInstance();
        birth.setTimeInMillis(dateOfBirth);

        // Calculate years
        int years = current.get(Calendar.YEAR) - birth.get(Calendar.YEAR);

        // Calculate months
        int months = current.get(Calendar.MONTH) - birth.get(Calendar.MONTH);
        if (months < 0 && years > 0) {
            months += 12;
            years--;
        }

        // Calculate days
        int days = current.get(Calendar.DAY_OF_MONTH) - birth.get(Calendar.DAY_OF_MONTH);
        if (days < 0) {
            days += birth.getActualMaximum(Calendar.DAY_OF_MONTH);
            months--;
            if (months < 0 && years > 0) {
                months += 12;
                years--;
            }
        }

        // For special case: first day of child
        if (years <= 0 && months <= 0 && days <= 0) {
            return new int[]{0, 0, 1};
        }

        return new int[]{years, months, days};
    }

    /**
     * Get Full years old
     *
     * @param dateOfBirth long
     * @param context     Context
     * @return String
     */
    public static String getFullYearsOld(Context context, long dateOfBirth) {
        // Get year old of child
        int[] yearOlds = getYearsOld(dateOfBirth);
        if (yearOlds != null) {
            String yearStr = context.getResources().getString(R.string.child_years);
            StringBuilder sb = new StringBuilder();
            if (yearOlds[0] > 0) {
                sb.append(yearOlds[0]).append(yearStr);
            }
            if (yearOlds[1] > 0) {
                sb.append(" ").append(yearOlds[1]).append(context.getResources().getString(R.string.child_months));
            }
            if (yearOlds[2] > 0) {
                sb.append(" ").append(yearOlds[2]).append(context.getResources().getString(R.string.child_days));
            }

            if (yearOlds[0] == 0) {
                sb.append(yearStr);
            }
            return sb.toString();
        }
        return "";
    }


    /**
     * Get string of years old
     *
     * @param dateOfBirth long
     * @param context     Context
     * @return String
     */
    public static String getYearsOld(Context context, long dateOfBirth) {
        // Get year old of child
        int[] yearOlds = getYearsOld(dateOfBirth);
        if (yearOlds != null) {
            String yearStr = context.getResources().getString(R.string.child_years);
            String monthStr = context.getResources().getString(R.string.child_months);
            String dayStr = context.getResources().getString(R.string.child_days);
            StringBuilder sb = new StringBuilder();

            // Case has year => show year and month only
            if (yearOlds[0] > 0) {
                sb.append(yearOlds[0]).append(yearStr);

                if (yearOlds[1] > 0) {
                    sb.append(" ").append(yearOlds[1]).append(monthStr);
                }
            } else {
                // Case does not has year => show month or day only
                if (yearOlds[1] > 0) {
                    sb.append(yearOlds[1]).append(monthStr);
                    if (yearOlds[2] > 0) {
                        sb.append(" ").append(yearOlds[2]).append(dayStr);
                    }
                } else {
                    sb.append(yearOlds[2]).append(dayStr);
                }

                sb.append(yearStr);
            }
            return sb.toString();
        }
        return "";
    }


}
