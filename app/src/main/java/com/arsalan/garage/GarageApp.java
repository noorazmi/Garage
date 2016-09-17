package com.arsalan.garage;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.arsalan.garage.utils.AppConstants;
import com.arsalan.garage.utils.FlavorConstants;
import com.arsalan.garage.utils.Logger;
import com.arsalan.garage.utils.PrefUtility;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.Map;

import networking.loader.LoaderHandler;

/**
 * <p/>
 * Project: <b>Loud Shout</b><br/>
 * Created by: Noor  Alam on 03/09/15.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class GarageApp extends Application {

    private static GarageApp sGarageApp = null;

    public static final GarageApp getInstance() {
        return sGarageApp;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        //MultiDex.install(this);
        sGarageApp = this;
        initVolleyController();
        initImageLoader(this);
        initLoaderHandler(this);

    }


    private void initVolleyController(){
        //VolleyController.initVolleyRequestQueueAndImageLoader(this);
    }

    private void initImageLoader(Context applicationContext) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by ImageLoaderConfiguration.createDefault(this) method.
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(applicationContext);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        if (FlavorConstants.BUILD_TYPE == AppConstants.BUILD_TYPE_DEVELOPMENT) {
            config.writeDebugLogs();
        }
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }

    private void initLoaderHandler(Context applicationContext) {
        //Must be called to make the handler work.
        LoaderHandler.setContext(applicationContext);
        if (FlavorConstants.BUILD_TYPE == AppConstants.BUILD_TYPE_DEVELOPMENT) {
            //Turn On the framework's internal loader manager debugging logs.By default it is false
            LoaderHandler.setLoggerEnabled(true);
        }
    }

    /**If we extend {@link Application} class we need to do this to enable multidexing. In our case here we are extending {@link MultiDexApplication} class so multidexing automatically enabled*/
//    @Override
//    protected void attachBaseContext(Context base) {
//        super.attachBaseContext(base);
//        MultiDex.install(base);
//    }

    /**
     * Adds session cookie to headers if exists.
     *
     * @param headers
     */
    public final void addSessionCookie(Map<String, String> headers) {
        String sessionId = PrefUtility.getPreferences().getString(AppConstants.SESSION_COOKIE, "");
        if (sessionId.length() > 0) {
            StringBuilder builder = new StringBuilder();
            builder.append("=");
            builder.append(sessionId);
            if (headers.containsKey(AppConstants.COOKIE_KEY)) {
                builder.append("; ");
                builder.append(headers.get(AppConstants.COOKIE_KEY));
            }
            if (PrefUtility.isLoggedIn()) {
                Logger.printLog("Access Token " + PrefUtility.getAccessToken());
                headers.put("X-Auth-Token", PrefUtility.getAccessToken());
            }
            headers.put(AppConstants.COOKIE_KEY, sessionId);
        }
    }

    /**
     * Clear session cookie from headers if exists.
     */
    public final void clearSessionCookies() {
        String sessionId = PrefUtility.getPreferences().getString(AppConstants.SESSION_COOKIE, "");
        if (sessionId.length() > 0) {
            SharedPreferences.Editor prefEditor = PrefUtility.getPreferences().edit();
            prefEditor.clear();
            prefEditor.commit();
        }
    }


    /**
     * Checks the response headers for session cookie and saves it
     * if it finds it.
     *
     * @param headers Response Headers.
     */
    public final void checkSessionCookie(Map<String, String> headers) {
        if (headers.containsKey(AppConstants.SET_COOKIE_KEY)
                && headers.get(AppConstants.SET_COOKIE_KEY).startsWith(AppConstants.SESSION_COOKIE)) {
            String cookie = headers.get(AppConstants.SET_COOKIE_KEY);
            Log.e("My tag", "In Check Cookies : " + cookie);
            if (cookie.length() > 0) {
                SharedPreferences.Editor prefEditor = PrefUtility.getEditor();
                prefEditor.putString(AppConstants.SESSION_COOKIE, cookie);
                prefEditor.commit();
            }
        }
    }
}
