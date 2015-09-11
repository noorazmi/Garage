package networking;

import android.util.Log;

import networking.loader.LoaderHandler;

/**
 * Created by Noor Alam on 13/5/15.
 */
public class Logger {

    /*
     Print error log in console
     */
    public static void e(String tag, String msg) {
        if (LoaderHandler.isLoggerEnabled()) {
            if (msg != null)
                Log.e(tag, msg);
        }
    }

    /*
    Print Debug log in console
     */
    public static void d(String tag, String msg) {
        if (LoaderHandler.isLoggerEnabled()) {
            if (msg != null)
                Log.d(tag, msg);
        }
    }

    /*
Print Information log in console
 */
    public static void i(String tag, String msg) {
        if (LoaderHandler.isLoggerEnabled()) {
            if (msg != null)
                Log.i(tag, msg);
        }
    }
}
