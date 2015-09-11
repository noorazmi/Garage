package com.arsalan.garage.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.arsalan.garage.R;
import com.arsalan.garage.fragments.CategoryDescriptionActivityFragment;
import com.arsalan.garage.fragments.CategorySaleListActivityFragment;

public class CategoryDescriptionActivity extends BaseActivity {

    //private CharSequence mTitle;
    private FragmentManager mFragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_description);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolbar(toolbar, getResources().getString(R.string.description), true, Gravity.CENTER);

        setLevelOneMenuFragment();
    }




    private void setLevelOneMenuFragment(){
        FrameLayout frameLayoutContainer = (FrameLayout) findViewById(R.id.framelayout_container);
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        Fragment fragment = new CategoryDescriptionActivityFragment();
        fragment.setArguments(getIntent().getExtras());
        fragmentTransaction.replace(R.id.framelayout_container, fragment).commit();

    }
}

