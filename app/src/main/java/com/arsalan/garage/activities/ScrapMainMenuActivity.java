package com.arsalan.garage.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.widget.FrameLayout;

import com.arsalan.garage.R;
import com.arsalan.garage.fragments.ScrapMainMenuFragment;
import com.arsalan.garage.utils.AppConstants;

public class ScrapMainMenuActivity extends BaseActivity {

    private FragmentManager mFragmentManager;
    private String scrapeType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_container);

        Bundle bundle = getIntent().getBundleExtra(AppConstants.BUNDLE_EXTRA);
        scrapeType = bundle.getString(AppConstants.SCRAP_TYPE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolbar(toolbar, getToolbarTitle(), true, Gravity.CENTER);

        setMenuHolderFragment();
    }

    private String getToolbarTitle() {
        return  "السكراب";
    }


    private void setMenuHolderFragment() {
        FrameLayout frameLayoutContainer = (FrameLayout) findViewById(R.id.framelayout_container);
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.SCREEN_TYPE, AppConstants.SCREEN_SCRAP);
        bundle.putString(AppConstants.SCRAP_TYPE, scrapeType);
        ScrapMainMenuFragment fragment = new ScrapMainMenuFragment();
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.framelayout_container, fragment).commit();

    }
}


