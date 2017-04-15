package com.arsalan.garage.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.widget.TextView;

import com.arsalan.garage.R;
import com.arsalan.garage.fragments.AlwakalatSparePartsFragment;
import com.arsalan.garage.utils.AppConstants;

public class AlwakalatSparePartsActivity extends BaseActivity {
    private Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home1);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolbar(mToolbar, getIntent().getStringExtra(AppConstants.EXTRA_TITLE), true, Gravity.CENTER);
        setMenuHolderFragment();
    }

    private void setMenuHolderFragment() {
        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.EXTRA_TITLE, getIntent().getStringExtra(AppConstants.EXTRA_TITLE));
        bundle.putString(AppConstants.EXTRA_URL, getIntent().getStringExtra(AppConstants.EXTRA_URL));
        AlwakalatSparePartsFragment fragment = new AlwakalatSparePartsFragment();
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.framelayout_container, fragment).commit();
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
