package com.arsalan.garage.activities;

import android.os.Bundle;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;

import com.arsalan.garage.R;
import com.arsalan.garage.fragments.AlwakalatAgencySubMenu2Fragment;
import com.arsalan.garage.utils.AppConstants;

public class AlwakalatAgencySubMenu2Activity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolbar(toolbar, getIntent().getStringExtra(AppConstants.EXTRA_TITLE), true, Gravity.CENTER);
        setMenuHolderFragment(getIntent().getStringExtra(AppConstants.EXTRA_TITLE), getIntent().getStringExtra(AppConstants.EXTRA_URL), getIntent().getStringExtra(AppConstants.EXTRA_COMPANY_NAME));
    }

    public void setMenuHolderFragment(String title, String url, String companyName) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.EXTRA_TITLE, title);
        bundle.putString(AppConstants.EXTRA_URL, url);
        bundle.putString(AppConstants.EXTRA_COMPANY_NAME, companyName);
        AlwakalatAgencySubMenu2Fragment fragment = new AlwakalatAgencySubMenu2Fragment();
        fragment.setArguments(bundle);
        fragmentTransaction.add(R.id.framelayout_container, fragment).addToBackStack("").commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getFragmentManager();
        if(fragmentManager.getBackStackEntryCount() > 1){
            fragmentManager.popBackStack();
        }else {
            super.onBackPressed();
        }
    }
}
