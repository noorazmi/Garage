package com.arsalan.garage.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;

import com.arsalan.garage.R;
import com.arsalan.garage.fragments.AlwakalatAgencyDescriptionFragment;
import com.arsalan.garage.utils.AppConstants;

/**
 * <p/>
 * Created by: Noor  Alam on 18/10/15.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */

public class AlwakalatAgencyDescriptionActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolbar(toolbar, getIntent().getStringExtra(AppConstants.EXTRA_TITLE), true, Gravity.CENTER);
        setMenuHolderFragment();
    }

    private void setMenuHolderFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        //bundle.putString(AppConstants.EXTRA_TITLE, getIntent().getStringExtra(AppConstants.EXTRA_TITLE));
        //bundle.putInt(AppConstants.IMAGE_URL, getIntent().getIntExtra(AppConstants.IMAGE_URL, 0));
        bundle.putString(AppConstants.EXTRA_CAR_ID, getIntent().getStringExtra(AppConstants.EXTRA_CAR_ID));
        AlwakalatAgencyDescriptionFragment fragment = new AlwakalatAgencyDescriptionFragment();
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.framelayout_container, fragment).commit();
    }

}

