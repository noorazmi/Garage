package com.arsalan.garage.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.widget.FrameLayout;

import com.arsalan.garage.R;
import com.arsalan.garage.fragments.AlwakalatAgencyMenuFragment;
import com.arsalan.garage.utils.AppConstants;

public class AlwakalatAgencyMenuActivity extends BaseActivity {

    private FragmentManager mFragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home1);

        Bundle bundle = getIntent().getBundleExtra(AppConstants.BUNDLE_EXTRA);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolbar(toolbar, getToolbarTitle(), true, Gravity.CENTER);

        setMenuHolderFragment();
    }

    private String getToolbarTitle() {
        return  getString(R.string.agencies);
    }


    private void setMenuHolderFragment() {
        FrameLayout frameLayoutContainer = (FrameLayout) findViewById(R.id.framelayout_container);
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        //Bundle bundle = new Bundle();
        //bundle.putString(AppConstants.SCREEN_TYPE, AppConstants.SCREEN_SCRAP);
        AlwakalatAgencyMenuFragment fragment = new AlwakalatAgencyMenuFragment();
        //fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.framelayout_container, fragment).commit();

    }

}
