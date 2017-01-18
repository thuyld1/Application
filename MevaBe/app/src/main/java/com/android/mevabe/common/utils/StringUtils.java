package com.android.mevabe.common.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.util.Patterns;

import java.util.regex.Pattern;

/**
 * Created by hoangnm on 1/28/16.
 */
public class StringUtils {
    private static final String P_ALPHA_DIGIT_ONLY = "^[0-9A-Za-z]+$";
    private static final String P_USER_ID = "^[a-z][0-9A-Za-z]{3,11}$";
    private static final String P_USER_PW = "[0-9A-Za-z]{6,12}$";
    private static final String P_USER_NUMBER = "[0-9]{11}$";
    private static final String P_USER_PHONE = "[0-9]{10,11}$";
    private static final String P_VERSION = "[0-9]+.[0-9]+.[0-9]";

    private static final String P_SMS_KEY = "[0-9]{3,6}$";

    private static final String numberChars = "123456789";
    private static final String signedChars = "àảãáạăằẳẵắặâầẩẫấậđèẻẽéẹêềểễếệìỉĩíịòỏõóọôồổỗốộơờởỡớợùủũúụưừửữứựỳỷỹýỵÀẢÃÁẠĂẰẲẴẮẶÂẦẨẪẤẬĐÈẺẼÉẸÊỀỂỄẾỆÌỈĨÍỊÒỎÕÓỌÔỒỔỖỐỘƠỜỞỠỚỢÙỦŨÚỤƯỪỬỮỨỰỲỶỸÝỴ";
    private static final String unsignedChars = "aaaaaaaaaaaaaaaaadeeeeeeeeeeeiiiiiooooooooooooooooouuuuuuuuuuuyyyyyAAAAAAAAAAAAAAAAADEEEEEEEEEEEIIIIIOOOOOOOOOOOOOOOOOUUUUUUUUUUUYYYYY";

    public static boolean isAlphaDigitOnly(String str) {
        return str.matches(P_ALPHA_DIGIT_ONLY);
    }

    public static boolean validPhoneNumber(String str) {
        return str.matches(P_USER_PHONE);
    }

    public static boolean validEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    public static boolean validUserID(String userID) {

        return userID.matches(P_USER_ID);

    }

    public static boolean validUserPW(String userPW) {
        return userPW.matches(P_USER_PW);
    }

    public static boolean validUserNumber(String userNumber) {
        return userNumber.matches(P_USER_NUMBER);
    }

    public static boolean validSmskey(String smskey) {
        return smskey.matches(P_SMS_KEY);
    }

    public static boolean validVersion(String version) {
        return version.matches(P_VERSION);
    }

    /**
     * Makes a substring of a string bold.
     *
     * @param text       Full text
     * @param textToBold Text you want to make bold
     * @return String with bold substring
     */

    public static SpannableStringBuilder makeSectionOfTextBold(String text, String textToBold) {
        return null;
    }

    public static boolean isEmpty(String str) {
        return (str == null || str.trim().length() == 0);
    }

    public static boolean validTextSearch(String text) {
        for (int i = 0; i < text.length(); i++) {
            char cs = text.charAt(i);
            if (signedChars.indexOf(cs) == -1 && unsignedChars.indexOf(cs) == -1 && numberChars.indexOf(cs) == -1) {
                return false;
            }
        }
        return true;
    }

    public static String convertUnsignedString(String text) {
        String output = text;
        for (int i = 0; i < text.length(); i++) {
            char cs = text.charAt(i);
            int index = signedChars.indexOf(cs);
            if (index != -1) {
                output = output.replace(cs, unsignedChars.charAt(index));
            }
        }
        return output;
    }

    public static String getTextYoutubeView(String text) {
        if (isEmpty(text)) return "";
        String output = "";
        text = text.replace(",","").replace(".","");
        int i = 1;
        while (text.length() > 0) {
            int length = text.length();
            output = text.substring(length - 1, length) + output;
            text = text.substring(0, length - 1);
            if (i % 3 == 0 && i > 0)
                output = "," + output;
            i++;
        }
        if (output.substring(0, 1).equals(","))
            output = output.substring(1, output.length());
        return output;
    }

    public static SpannableString getTextBoldSpannable(String text) {
        SpannableString ssText = new SpannableString(text);
        ssText.setSpan(new StyleSpan(Typeface.BOLD), 0, ssText.length(), 0);
        return ssText;
    }

    public static SpannableString getTextItalicSpannable(String text) {
        SpannableString ssText = new SpannableString(text);
        ssText.setSpan(new StyleSpan(Typeface.ITALIC), 0, ssText.length(), 0);
        return ssText;
    }

    public static String getErrorMessage(Context mContext, String message) {
        //return mContext.getString(R.string.err_unknown).replace("%s", message);
        return "";
    }
}
