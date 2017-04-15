package com.arsalan.garage.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v13.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;

import com.arsalan.garage.R;
import com.arsalan.garage.adapters.SectionsFragmentPagerAdapter;
import com.arsalan.garage.utils.LocaleHelper;
import com.arsalan.garage.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity {

    private int backPressedCount;
    private static final int REQUEST_CODE_PERMISSIONS = 113;
    private static String[] PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.SEND_SMS
    };

    private ViewPager mViewPager;
    private BottomNavigationView mBottomNavigationView;
    private SectionsFragmentPagerAdapter mSectionsFragmentPagerAdapter;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mViewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_about:
                    mViewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_settings:
                    mViewPager.setCurrentItem(2);
                    return true;
            }
            return false;
        }
    };

    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            mBottomNavigationView.getMenu().getItem(position).setChecked(true);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolbar(toolbar, getResources().getString(R.string.garage), false, Gravity.CENTER);

        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        mSectionsFragmentPagerAdapter = new SectionsFragmentPagerAdapter(getFragmentManager(), this);
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsFragmentPagerAdapter);
        mViewPager.addOnPageChangeListener(mOnPageChangeListener);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
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

    @Override
    public void onBackPressed() {
        if (mViewPager.getCurrentItem() != 0) {
            mViewPager.setCurrentItem(0, true);
            mBottomNavigationView.getMenu().getItem(0).setChecked(true);
        }else {
            showHomeTabOnBackPressed();
        }
    }

    private void showHomeTabOnBackPressed() {

        if (backPressedCount == 1) {
            super.onBackPressed();
        } else {
            Utils.showToastMessage(this, getString(R.string.message_app_exit));
            backPressedCount++;
            final Runnable r = new Runnable() {
                public void run() {
                    backPressedCount = 0;
                }
            };
            android.os.Handler handler = new android.os.Handler();
            handler.postDelayed(r, 4000);
        }
    }
}
