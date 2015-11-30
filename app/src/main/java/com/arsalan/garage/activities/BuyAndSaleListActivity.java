package com.arsalan.garage.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;

import com.arsalan.garage.R;
import com.arsalan.garage.fragments.BuyAndSaleListFragment;
import com.arsalan.garage.utils.AppConstants;

public class BuyAndSaleListActivity extends BaseActivity {

    private CharSequence mTitle;
    private FragmentManager mFragmentManager;
    private String TAG = "CategorySaleListActivity";
    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_and_sale_list);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        //setToolbar(toolbar, getResources().getString(R.string.category_sale), true, Gravity.CENTER);
        setToolbar(mToolbar, getIntent().getStringExtra(AppConstants.EXTRA_TITLE), true, Gravity.CENTER);
        setCategorySaleListFragment();
    }


    private void setCategorySaleListFragment() {
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        Fragment buyAndSaleListFragment = new BuyAndSaleListFragment();
        Bundle bundle = getIntent().getExtras();
        Log.e(TAG, " bundle URL:" + bundle.getString(AppConstants.URL));
        buyAndSaleListFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.framelayout_container, buyAndSaleListFragment).commit();

    }


    public void setNoOfItemsInTooBar(int itemCount) {
        TextView textViewTitle = (TextView) getLayoutInflater().inflate(R.layout.textview_toolbar_item_count, null);
        textViewTitle.setText(String.valueOf(itemCount + " Items"));
        mToolbar.addView(textViewTitle, new ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.RIGHT
        ));
    }


}
