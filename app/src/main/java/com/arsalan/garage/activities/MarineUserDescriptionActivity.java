package com.arsalan.garage.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.widget.FrameLayout;

import com.arsalan.garage.R;
import com.arsalan.garage.fragments.MarineUserDedailsFragment;
import com.arsalan.garage.utils.AppConstants;

public class MarineUserDescriptionActivity extends BaseActivity {

    //private CharSequence mTitle;
    private FragmentManager mFragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home1);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolbar(toolbar,  getIntent().getStringExtra(AppConstants.EXTRA_TITLE), true, Gravity.CENTER);

        setLevelOneMenuFragment();
    }




    private void setLevelOneMenuFragment(){
        FrameLayout frameLayoutContainer = (FrameLayout) findViewById(R.id.framelayout_container);
        mFragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        Fragment fragment = new MarineUserDedailsFragment();
        fragment.setArguments(getIntent().getExtras());
        fragmentTransaction.replace(R.id.framelayout_container, fragment).commit();

    }
}

