package com.arsalan.garage.adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v13.app.FragmentPagerAdapter;

import com.arsalan.garage.R;
import com.arsalan.garage.fragments.HomeTabFragment;
import com.arsalan.garage.fragments.PostAdTabFragment;
import com.arsalan.garage.fragments.SettingsFragment;


/**
 * <p/>
 * Created by: Noor  Alam on 14/03/17.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */

public class SectionsFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public SectionsFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = HomeTabFragment.newInstance(mContext.getString(R.string.home));
                break;
            case 1:
                fragment = PostAdTabFragment.newInstance(mContext.getString(R.string.post_add));
                break;
            case 2:
                fragment = SettingsFragment.newInstance(mContext.getString(R.string.contact_us));
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}

