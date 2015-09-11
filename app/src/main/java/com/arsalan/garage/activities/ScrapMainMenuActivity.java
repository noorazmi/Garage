package com.arsalan.garage.activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.widget.FrameLayout;

import com.arsalan.garage.R;
import com.arsalan.garage.fragments.LevelOneMenuActivityFragment;
import com.arsalan.garage.fragments.ScrapMainMenuFragment;
import com.arsalan.garage.models.HomeMenuItem;
import com.arsalan.garage.utils.AppConstants;
import com.arsalan.garage.utils.Urls;

public class ScrapMainMenuActivity extends BaseActivity implements LevelOneMenuActivityFragment.OnMenuItemClickListener {

    private FragmentManager mFragmentManager;
    private String scrapeType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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

    @Override
    public void onMenuItemClick(HomeMenuItem homeMenuItem, int position) {
        Bundle bundle = new Bundle();
        Intent intent = null;

        if (position == 0) {
            bundle.putString(AppConstants.SCRAP_TYPE, AppConstants.SCRAP_AMERICA);

        } else if (position == 1) {
            bundle.putString(AppConstants.SCRAP_TYPE, AppConstants.SCRAP_EUROPEAN);

        } else if (position == 2) {
            bundle.putString(AppConstants.SCRAP_TYPE, AppConstants.SCRAP_ASIAN);

        } else if (position == 3) {
            bundle.putString(AppConstants.SCRAP_TYPE, AppConstants.SCRAP_TAUSIL_KATA);

        }

        intent = new Intent(this, ScrapSubMenuActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);

        if (scrapeType.equals(AppConstants.SCRAP_AMERICA)) {

            switch (position) {
                case 0:
                    bundle.putString(AppConstants.SCRAP_AMERICA_SUB_TYPE, AppConstants.SCRAP_AMERICA_SUB_TYPE_CADILLAC);
                    bundle.putString(AppConstants.URL, Urls.CADILLAC);
                    break;
                case 1:
                    bundle.putString(AppConstants.SCRAP_AMERICA_SUB_TYPE, AppConstants.SCRAP_AMERICA_SUB_TYPE_DODGENCHRYSLER);
                    bundle.putString(AppConstants.URL, Urls.DODGENCHRYSLER);
                    break;
                case 3:
                    bundle.putString(AppConstants.SCRAP_AMERICA_SUB_TYPE, AppConstants.SCRAP_AMERICA_SUB_TYPE_CHEVROLET);
                    bundle.putString(AppConstants.URL, Urls.CHEVROLET);
                    break;
                case 4:
                    bundle.putString(AppConstants.SCRAP_AMERICA_SUB_TYPE, AppConstants.SCRAP_AMERICA_SUB_TYPE_GMC);
                    bundle.putString(AppConstants.URL, Urls.GMC);
                    break;
                case 5:
                    bundle.putString(AppConstants.SCRAP_AMERICA_SUB_TYPE, AppConstants.SCRAP_AMERICA_SUB_TYPE_FORDNLINCOLN);
                    bundle.putString(AppConstants.URL, Urls.FORDNLINCOLN);
                    break;
                case 6:
                    bundle.putString(AppConstants.SCRAP_AMERICA_SUB_TYPE, AppConstants.SCRAP_AMERICA_SUB_TYPE_HUMMER);
                    bundle.putString(AppConstants.URL, Urls.HUMMER);
                    break;
                case 7:
                    bundle.putString(AppConstants.SCRAP_AMERICA_SUB_TYPE, AppConstants.SCRAP_AMERICA_SUB_TYPE_JEEP);
                    bundle.putString(AppConstants.URL, Urls.JEEP);
                    break;


            }
            intent = new Intent(this, CategorySaleListActivity.class);
        }
    }
}


