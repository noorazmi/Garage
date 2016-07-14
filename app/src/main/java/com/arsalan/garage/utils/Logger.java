package com.arsalan.garage.utils;

import android.util.Log;

/**
 * Created by rajendra on 29/10/14.
 */
public class Logger {
    private static boolean DEBUG = true;
    private static final String TAG = "GARAGE_LOG";

    /*
     Print error log in console
     */
    public static void e(String Tag, String msg) {
        if (DEBUG) {
            if (msg != null)
                Log.e(Tag, msg);
        }
    }

    /*
    Print Debug log in console
     */
    public static void d(String Tag, String msg) {
        if (DEBUG) {
            if (msg != null)
                Log.e(Tag, msg);
        }
    }

    /*
Print Information log in console
 */
    public static void i(String Tag, String msg) {
        if (DEBUG) {
            if (msg != null)
                Log.e(Tag, msg);
        }
    }

    public static void printLog(String message){
        d(TAG, message);
    }
    public static void printError(String message){
        e(TAG, message);
    }
}
