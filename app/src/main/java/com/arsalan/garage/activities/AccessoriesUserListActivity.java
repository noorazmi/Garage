package com.arsalan.garage.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;

import com.arsalan.garage.R;
import com.arsalan.garage.fragments.AccessoriesUserListFragment;
import com.arsalan.garage.utils.AppConstants;

public class AccessoriesUserListActivity extends BaseActivity {

    private CharSequence mTitle;
    private FragmentManager mFragmentManager;
    private String TAG = "AccessoriesUserListActivity";
    private Toolbar mToolbar;
    private TextView textViewItemCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_sale_list);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolbar(mToolbar, getIntent().getStringExtra(AppConstants.EXTRA_TITLE), true, Gravity.CENTER);
        setListFragment();
    }


    private void setListFragment() {
        mFragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        Fragment listFragment = new AccessoriesUserListFragment();
        Bundle bundle = getIntent().getExtras();
        Log.e(TAG, " bundle URL:" + bundle.getString(AppConstants.URL));
        listFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.framelayout_container, listFragment).commit();

    }


    public void setNoOfItemsInTooBar(int itemCount) {
        if (textViewItemCount == null) {
            textViewItemCount = (TextView) getLayoutInflater().inflate(R.layout.textview_toolbar_item_count, null);
            mToolbar.addView(textViewItemCount, new ActionBar.LayoutParams(
                    ActionBar.LayoutParams.WRAP_CONTENT,
                    ActionBar.LayoutParams.MATCH_PARENT,
                    Gravity.RIGHT
            ));
        }
        textViewItemCount.setText(String.valueOf(itemCount + " Items"));
    }

}
