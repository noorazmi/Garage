package com.arsalan.garage.activities;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.widget.FrameLayout;

import com.arsalan.garage.R;
import com.arsalan.garage.fragments.HelpCarActivityFragment;

public class HelpCarActivity extends BaseActivity {

    private CharSequence mTitle;
    private FragmentManager mFragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setToolbar(toolbar, "Title", true);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolbar(toolbar, getResources().getString(R.string.title_activity_help_car), true, Gravity.CENTER);

        mTitle = getTitle();

        setLevelOneMenuFragment();
    }




    private void setLevelOneMenuFragment(){
        FrameLayout frameLayoutContainer = (FrameLayout) findViewById(R.id.framelayout_container);
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout_container, new HelpCarActivityFragment()).commit();

    }
}


