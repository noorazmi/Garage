package com.arsalan.garage.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v13.app.ActivityCompat;
import android.view.Window;
import android.view.WindowManager;

import com.arsalan.garage.R;


public class SplashActivity extends BaseActivity {

    private static final int SPLASH_DISPLAY_LENGTH = 2000;
    private Handler mHandler;

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mHandler = new Handler();
        mHandler.postDelayed(mPostDelayedRunnable, SPLASH_DISPLAY_LENGTH);
        verifyStoragePermissions(this);
        // Get token
        //String token = FirebaseInstanceId.getInstance().getToken();

// Log and toast
        //String msg =  "" + token;
        //Log.d("Token", msg);
        //Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private Runnable mPostDelayedRunnable = new Runnable() {
        @Override
        public void run() {
            Intent intent = null;
            intent = new Intent(SplashActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    };

    @Override
    public void onBackPressed() {
        //If user pressed back button before {@link #mPostDelayedRunnable} run method return, cancel the handler
        mHandler.removeCallbacks(mPostDelayedRunnable);
        super.onBackPressed();
    }
}
