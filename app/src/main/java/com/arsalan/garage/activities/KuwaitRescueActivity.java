package com.arsalan.garage.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;

import com.arsalan.garage.R;
import com.arsalan.garage.fragments.KuwaitRescueFragment;

public class KuwaitRescueActivity extends BaseActivity {

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolbar(toolbar, getString(R.string.kuwait_rescue_title), true, Gravity.CENTER);

        setMenuHolderFragment();
    }


    private void setMenuHolderFragment(){
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        KuwaitRescueFragment fragment = new KuwaitRescueFragment();
        fragmentTransaction.replace(R.id.framelayout_container, fragment).commit();

    }

}
