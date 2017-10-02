package com.arsalan.garage.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;

import com.arsalan.garage.R;
import com.arsalan.garage.fragments.ScrapUserMenuFragment;

public class ScrapUserMenuActivity extends BaseActivity {

    private FragmentManager mFragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_container);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolbar(toolbar, getToolbarTitle(), true, Gravity.CENTER);

        setMenuHolderFragment();
    }

    private String getToolbarTitle() {

        //return  getString(R.string.car_brand_other);
        return  getString(R.string.buy_and_sale);
    }


    private void setMenuHolderFragment() {
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        ScrapUserMenuFragment fragment = new ScrapUserMenuFragment();
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.framelayout_container, fragment).commit();

    }
}