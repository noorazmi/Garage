package com.arsalan.garage.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.widget.FrameLayout;

import com.arsalan.garage.R;
import com.arsalan.garage.fragments.BuyAndSaleMenuFragment;
import com.arsalan.garage.fragments.LevelOneMenuActivityFragment;
import com.arsalan.garage.models.HomeMenuItem;
import com.arsalan.garage.utils.AppConstants;

/**
 * <p/>
 * Project: <b>Loud Shout</b><br/>
 * Created by: Noor  Alam on 20/08/15.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class BuyAndSaleMenuActivity extends BaseActivity implements LevelOneMenuActivityFragment.OnMenuItemClickListener{

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setTitle(getResources().getString(R.string.karajat));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolbar(toolbar, getResources().getString(R.string.bai_ul_shara), true, Gravity.CENTER);

        setMenuHolderFragment();
    }


    private void setMenuHolderFragment(){
        FrameLayout frameLayoutContainer = (FrameLayout) findViewById(R.id.framelayout_container);
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.SCREEN_TYPE, AppConstants.SCREEN_FOR_RENT);
        BuyAndSaleMenuFragment fragment = new BuyAndSaleMenuFragment();
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.framelayout_container, fragment).commit();

    }

    @Override
    public void onMenuItemClick(HomeMenuItem homeMenuItem, int position) {
        //if(position == 0){
            //Intent intent = new Intent(this, HelpCarActivity.class);
            //startActivity(intent);

        //}
    }
}
