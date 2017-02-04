package com.arsalan.garage.activities;

import android.content.Context;
import android.os.Bundle;
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

public class HomeActivity extends BaseActivity {

    private FragmentManager mFragmentManager;
    private int backPressedCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolbar(toolbar, getResources().getString(R.string.karaji), false, Gravity.CENTER);


        setHomeFragment();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base, "ar"));
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
