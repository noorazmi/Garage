package com.arsalan.garage.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.widget.TextView;

import com.arsalan.garage.R;
import com.arsalan.garage.fragments.MarineBoatFishingListFragment;
import com.arsalan.garage.utils.AppConstants;

/**
 * <p/>
 * Created by: Noor  Alam on 07/08/16.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class MarineBoatFishingActivity extends BaseActivity {

    private TextView textViewItemCount;
    private Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_and_sale_description);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolbar(mToolbar, getIntent().getStringExtra(AppConstants.EXTRA_TITLE), true, Gravity.CENTER);
        setMenuHolderFragment();
    }

    private void setMenuHolderFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.ID, getIntent().getStringExtra(AppConstants.ID));
        MarineBoatFishingListFragment fragment = new MarineBoatFishingListFragment();
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.framelayout_container, fragment).commit();
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
