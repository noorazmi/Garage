package com.arsalan.garage.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.arsalan.garage.GarageApp;

/**
 * This class represent shared preferences details for set and get details of application
 */
public class PrefUtility {
    public static final String SHARED_PREFERENCE_NAME = "garage_shared_preferences";
    public static final int PREF_MODE = Context.MODE_PRIVATE;

    public static String getAccessToken() {
        return getPreferences().getString(AppConstants.TOKEN, "");
    }

    public static void saveUserLoginToken(String loginToken) {
        SharedPreferences.Editor editor = getEditor();
        editor.putString(AppConstants.TOKEN, loginToken);
        editor.commit();
    }
    public static void deleteUserLoginToken() {
        SharedPreferences.Editor editor = getEditor();
        editor.putString(AppConstants.TOKEN, "");
        editor.commit();
    }

    public static boolean isLoggedIn() {
        return !TextUtils.isEmpty(getPreferences().getString(AppConstants.TOKEN, ""));
    }

    public static SharedPreferences.Editor getEditor() {
        return getPreferences().edit();
    }

    public static SharedPreferences getPreferences() {
        return GarageApp.getInstance().getSharedPreferences(SHARED_PREFERENCE_NAME, PREF_MODE);
    }


    public static void clearUsrPrefs() {
        GarageApp.getInstance().clearSessionCookies();
        SharedPreferences.Editor prefEditor = PrefUtility.getEditor();
        prefEditor.clear();
        prefEditor.commit();
    }

}
