package com.arsalan.garage.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;

import com.arsalan.garage.R;
import com.arsalan.garage.fragments.AlwakalatAgencySubMenu1Fragment;
import com.arsalan.garage.utils.AppConstants;

public class AlwakalatAgencySubMenu1Activity extends BaseActivity {

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolbar(toolbar, getIntent().getStringExtra(AppConstants.EXTRA_TITLE), true, Gravity.CENTER);
        setMenuHolderFragment();
    }

    private void setMenuHolderFragment() {
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.EXTRA_TITLE, getIntent().getStringExtra(AppConstants.EXTRA_TITLE));
        bundle.putString(AppConstants.EXTRA_URL, getIntent().getStringExtra(AppConstants.EXTRA_URL));
        AlwakalatAgencySubMenu1Fragment fragment = new AlwakalatAgencySubMenu1Fragment();
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.framelayout_container, fragment).commit();
    }

}
