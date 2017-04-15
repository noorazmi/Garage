package com.arsalan.garage.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v13.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;

import com.arsalan.garage.R;
import com.arsalan.garage.fragments.HomeFragment;
import com.arsalan.garage.utils.LocaleHelper;
import com.arsalan.garage.utils.Logger;
import com.arsalan.garage.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity {

    private FragmentManager mFragmentManager;
    private int backPressedCount = 0;

    // Storage Permissions
    private static final int REQUEST_CODE_PERMISSIONS = 113;
    private static String[] PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.SEND_SMS
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolbar(toolbar, getResources().getString(R.string.garage), false, Gravity.CENTER);


        setHomeFragment();
        verifyPermissions(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base, "ar"));
    }

    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    public static void verifyPermissions(Activity activity) {
        List<String> nonGrantedPermissionsList = new ArrayList(PERMISSIONS.length);

        for (int i = 0; i < PERMISSIONS.length; i++){
        int permission = ActivityCompat.checkSelfPermission(activity, PERMISSIONS[i]);
            if(permission != PackageManager.PERMISSION_GRANTED){
                nonGrantedPermissionsList.add(PERMISSIONS[i]);
            }
        }

        if(nonGrantedPermissionsList.size() == 0){
            return;
        }
        ActivityCompat.requestPermissions(
                activity,
                nonGrantedPermissionsList.toArray(new String[nonGrantedPermissionsList.size()]),
                REQUEST_CODE_PERMISSIONS
        );
    }

    private void setHomeFragment(){
        //FrameLayout frameLayoutContainer = (FrameLayout) findViewById(R.id.framelayout_container);
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout_container, new HomeFragment()).commit();

    }

    @Override
    public void onBackPressed() {
       showHomeTabOnBackPressed();
    }

    private void showHomeTabOnBackPressed() {

            if (backPressedCount == 1) {
                super.onBackPressed();
            } else {
                Utils.showToastMessage(this, getString(R.string.message_app_exit));
                backPressedCount++;
                Logger.i("backPressedCount", backPressedCount + "");
                final Runnable r = new Runnable() {
                    public void run() {
                        backPressedCount = 0;
                    }
                };
                android.os.Handler handler = new android.os.Handler();
                handler.postDelayed(r, 4000);
            }
        }

    public void addFragment(Fragment fragment){
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.framelayout_container, fragment).addToBackStack(null).commit();
    }
}
