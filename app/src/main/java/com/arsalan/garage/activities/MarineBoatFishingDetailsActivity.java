package com.arsalan.garage.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;

import com.arsalan.garage.R;
import com.arsalan.garage.fragments.MarineBoatFishingDetailsFragment;
import com.arsalan.garage.utils.AppConstants;

/**
 * <p/>
 * Created by: Noor  Alam on 08/08/16.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class MarineBoatFishingDetailsActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_and_sale_description);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolbar(toolbar, getIntent().getStringExtra(AppConstants.EXTRA_TITLE), true, Gravity.CENTER);
        setMenuHolderFragment();
    }

    private void setMenuHolderFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.ID, getIntent().getStringExtra(AppConstants.ID));
        MarineBoatFishingDetailsFragment fragment = new MarineBoatFishingDetailsFragment();
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.framelayout_container, fragment).commit();
    }

}
