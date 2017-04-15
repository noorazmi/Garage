package com.arsalan.garage.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;

import com.arsalan.garage.R;
import com.arsalan.garage.fragments.ScrapUserDetailsFragment;
import com.arsalan.garage.utils.AppConstants;

public class ScrapUserDetailsActivity extends BaseActivity {

    //private CharSequence mTitle;
    private FragmentManager mFragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_container);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolbar(toolbar,  getIntent().getStringExtra(AppConstants.EXTRA_TITLE), true, Gravity.CENTER);

        setLevelOneMenuFragment();
    }




    private void setLevelOneMenuFragment(){
        mFragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        Fragment fragment = new ScrapUserDetailsFragment();
        fragment.setArguments(getIntent().getExtras());
        fragmentTransaction.replace(R.id.framelayout_container, fragment).commit();

    }
}
