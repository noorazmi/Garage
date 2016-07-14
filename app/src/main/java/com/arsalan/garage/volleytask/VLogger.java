package com.arsalan.garage.volleytask;

import android.util.Log;

/**
 * <p/>
 * Created by: Noor  Alam on 14/12/15.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
class VLogger {

    private static boolean isDebuggable = true;

    /**
     *  Prints error level log. Prints log only if {@link #isDebuggable} true.
     *
     * @param message String
     */
    public static void printLogE(String message) {
        if(isDebuggable){
            Log.e("VLogger", message);
        }
    }

    /**
     * Prints debug level log. Prints log only if {@link #isDebuggable} true.
     * @param message
     */
    public static void printLogD(String message) {
        if(isDebuggable){
            Log.d("VLogger", message);
        }
    }

    public static void setDebaggable(boolean debuggable){
        isDebuggable = debuggable;
    }
}
