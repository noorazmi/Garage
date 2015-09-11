package com.arsalan.garage.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.arsalan.garage.R;
import com.arsalan.garage.fragments.CategorySaleListActivityFragment;
import com.arsalan.garage.fragments.LevelOneMenuActivityFragment;
import com.arsalan.garage.utils.AppConstants;
import com.arsalan.garage.utils.Logger;
import com.arsalan.garage.vo.AmericanCarsVO;

import networking.HttpConstants;
import networking.listeners.OnLoadCompleteListener;
import networking.loader.LoaderHandler;
import networking.models.HTTPModel;
import networking.models.HTTPRequest;
import networking.models.HTTPResponse;

public class CategorySaleListActivity extends BaseActivity {

    private CharSequence mTitle;
    private FragmentManager mFragmentManager;
    private String TAG = "CategorySaleListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_sale_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolbar(toolbar, getResources().getString(R.string.category_sale), true, Gravity.CENTER);
        setLevelOneMenuFragment();
    }




    private void setLevelOneMenuFragment(){
        //FrameLayout frameLayoutContainer = (FrameLayout) findViewById(R.id.framelayout_container);
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        Fragment categorySaleListActivityFragment = new CategorySaleListActivityFragment();
        Bundle bundle = getIntent().getExtras();
        Log.e(TAG, " bundle URL:" + bundle.getString(AppConstants.URL));
        categorySaleListActivityFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.framelayout_container, categorySaleListActivityFragment).commit();

    }




}
