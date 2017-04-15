package com.arsalan.garage.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.arsalan.garage.R;


public class SplashActivity extends BaseActivity {

    private static final int SPLASH_DISPLAY_LENGTH = 1400;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mHandler = new Handler();
        mHandler.postDelayed(mPostDelayedRunnable, SPLASH_DISPLAY_LENGTH);
    }

    private Runnable mPostDelayedRunnable = new Runnable() {
        @Override
        public void run() {
            Intent intent = null;
            intent = new Intent(SplashActivity.this, LanguageChooserActivity.class);
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
