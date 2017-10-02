package com.arsalan.garage.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;

import com.arsalan.garage.R;
import com.arsalan.garage.fragments.ForSaleSubMenuFragment;
import com.arsalan.garage.utils.AppConstants;

public class ForSaleSubMenuActivity extends BaseActivity {

    private String scrapeType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_and_sale_sub_menu);
        Bundle bundle = getIntent().getExtras();
        scrapeType = bundle.getString(AppConstants.SCRAP_TYPE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolbar(toolbar, getToolbarTitle(), true, Gravity.CENTER);
        setMenuHolderFragment();
    }



    private void setMenuHolderFragment() {
        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        ForSaleSubMenuFragment fragment = new ForSaleSubMenuFragment();
        fragmentTransaction.replace(R.id.framelayout_container, fragment).commit();

    }


    private String getToolbarTitle() {
        String title = null;

        if (scrapeType.equals(AppConstants.SCRAP_AMERICA)) {
            title =getString(R.string.american);
        } else if (scrapeType.equals(AppConstants.SCRAP_EUROPEAN)) {
            title = getString(R.string.european);
        } else if (scrapeType.equals(AppConstants.SCRAP_ASIAN)) {
            title =getString(R.string.asian);
        } else if (scrapeType.equals(AppConstants.SCRAP_DELIVERY)) {
            //title = "توصيل قطع";
            title =getString(R.string.parts_and_accessories);

        }

        return title;
    }
}
