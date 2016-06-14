package com.arsalan.garage;

import android.app.Application;
import android.content.Context;

import com.arsalan.garage.utils.AppConstants;
import com.arsalan.garage.utils.FlavorConstants;
import com.arsalan.garage.utils.Utils;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

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
    public static String DEVICE_UUID_WITH_SLASH = "/5678C7C4-439B-404D-8892-4FDABB86770A%200x00007fdc995cceb0";
    public static String DEVICE_UUID = "5678C7C4-439B-404D-8892-4FDABB86770A%200x00007fdc995cceb0";

    public static final GarageApp getInstance(){
        return sGarageApp;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        sGarageApp = this;
//        if (FlavorConstants.BUILD_TYPE == AppConstants.BUILD_TYPE_DEVELOPMENT && Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
//            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
//            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
//        }
        initImageLoader(this);
        initLoaderHandler(this);
        initDeviceUUID();

    }

    private void initDeviceUUID(){
        DEVICE_UUID_WITH_SLASH = "/"+ Utils.getUDID(this);
        DEVICE_UUID = Utils.getUDID(this);
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

    private void initLoaderHandler(Context applicationContext){
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
}
