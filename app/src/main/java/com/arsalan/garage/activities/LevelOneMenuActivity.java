package com.arsalan.garage.activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.arsalan.garage.R;
import com.arsalan.garage.fragments.LevelOneMenuActivityFragment;
import com.arsalan.garage.models.HomeMenuItem;
import com.arsalan.garage.utils.AppConstants;
import com.arsalan.garage.utils.Logger;

public class LevelOneMenuActivity extends BaseActivity implements LevelOneMenuActivityFragment.OnMenuItemClickListener {

    private CharSequence mTitle;
    private FragmentManager mFragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_one_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setToolbar(toolbar, "Title", true);

        mTitle = getTitle();

        setLevelOneMenuFragment();
    }




    private void setLevelOneMenuFragment(){
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putInt(AppConstants.SCREEN_TYPE, AppConstants.SCREEN_KARAJAT);
        LevelOneMenuActivityFragment fragment = new LevelOneMenuActivityFragment();
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.framelayout_container, fragment).commit();
    }


    @Override
    public void onMenuItemClick(HomeMenuItem homeMenuItem, int position) {
        Intent intent = new Intent(this, LevelTwoMenuActivity.class);
        Logger.i("UserId", homeMenuItem.getMenuTitle());
        startActivity(intent);
    }
}
