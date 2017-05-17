package com.arsalan.garage.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;

import com.arsalan.garage.R;
import com.arsalan.garage.fragments.ScrapSubMenuFragment;
import com.arsalan.garage.utils.AppConstants;

public class ScrapSubMenuActivity extends BaseActivity {

    private String scrapeType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrap_sub_menu);
        Bundle bundle = getIntent().getExtras();
        scrapeType = bundle.getString(AppConstants.SCRAP_TYPE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolbar(toolbar, getToolbarTitle(), true, Gravity.CENTER);

        setMenuHolderFragment();
    }



    private void setMenuHolderFragment() {
        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        ScrapSubMenuFragment fragment = new ScrapSubMenuFragment();
        fragmentTransaction.replace(R.id.framelayout_container, fragment).commit();

    }


    private String getToolbarTitle() {
        String title = null;

        if (scrapeType.equals(AppConstants.SCRAP_AMERICA)) {
            title = getString(R.string.american);
        } else if (scrapeType.equals(AppConstants.SCRAP_EUROPEAN)) {
            title = getString(R.string.asian);
        } else if (scrapeType.equals(AppConstants.SCRAP_ASIAN)) {
            title = getString(R.string.asian);
        } else if (scrapeType.equals(AppConstants.SCRAP_DELIVERY)) {
            title = getString(R.string.connecting_pieces);
        }

        return title;
    }

}
